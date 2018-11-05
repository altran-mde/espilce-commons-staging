package org.espilce.commons.emf.textrenderer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.espilce.commons.emf.textrenderer.IRenderFilter;
import org.espilce.commons.emf.textrenderer.OmitUnsetFilter;

/**
 * Creates a human-readable tree-like rendering of EMF contents.
 * 
 * @author nstotz
 */
@SuppressWarnings("all")
public class EmfStringRenderer {
	private final StringBuilder builder = new StringBuilder();

	private final Resource resource;

	private final Collection<EObject> eObjects;

	private final EObject eObject;

	public EmfStringRenderer(final Resource resource) {
		this.resource = resource;
		this.eObjects = CollectionLiterals.<EObject>emptySet();
		this.eObject = null;
		this.validateInput(false);
	}

	public EmfStringRenderer(final Collection<EObject> eObjects) {
		this.resource = null;
		this.eObjects = eObjects;
		this.eObject = null;
		this.validateInput(true);
	}

	public EmfStringRenderer(final EObject eObject) {
		this.resource = null;
		this.eObjects = CollectionLiterals.<EObject>emptySet();
		this.eObject = eObject;
		this.validateInput(false);
	}

	protected void validateInput(final boolean allowEmptySet) {
		if (((((this.resource == null) && (this.eObject == null)) && (!allowEmptySet)) && this.eObjects.isEmpty())) {
			throw new IllegalArgumentException("Invalid constructor parameter.");
		}
	}

	/**
	 * Public API to render.
	 */
	public String render() {
		int _length = this.builder.length();
		boolean _equals = (_length == 0);
		if (_equals) {
			this.doRender();
		}
		return this.builder.toString();
	}

	/**
	 * Switch to render the actually passed constructor parameter.
	 */
	protected void doRender() {
		if ((this.resource != null)) {
			this.renderFiltered(this.resource, 0, null);
		} else {
			if ((this.eObject != null)) {
				this.renderFiltered(this.eObject, 0, null);
			} else {
				this.renderFiltered(this.eObjects, 0, null);
			}
		}
	}

	/**
	 * {@link #render()} call, guarded by {@link #getFilter()}.
	 */
	protected void renderFiltered(final Object object, final int indent, final EObject context) {
		boolean _shouldRender = this.getFilter().shouldRender(object, indent, context);
		if (_shouldRender) {
			this.render(object, indent, context);
		}
	}

	protected void _render(final Resource resource, final int indent, final EObject context) {
		this.append("Resource: ", indent);
		this.builder.append(resource.getURI());
		this.renderFiltered(resource.getContents(), (indent + 1), null);
	}

	protected void _render(final Collection<EObject> eObjects, final int indent, final EObject context) {
		final Consumer<EObject> _function = (EObject it) -> {
			this.renderFiltered(it, (indent + 1), null);
		};
		this.<EObject>renderCollection(eObjects, indent, context, _function);
	}

	protected <T extends Object> void renderCollection(final Collection<T> collection, final int indent,
			final EObject context, final Consumer<T> lineRenderer) {
		this.builder.append("[");
		for (final T element : collection) {
			{
				this.append("", (indent + 1));
				lineRenderer.accept(element);
			}
		}
		this.append("]", indent);
	}

	protected void _render(final EObject eObject, final int indent, final EObject context) {
		this.builder.append(eObject.eClass().getName());
		this.builder.append(" {");
		final Function1<EStructuralFeature, String> _function = (EStructuralFeature it) -> {
			return it.getName();
		};
		List<EStructuralFeature> _sortBy = IterableExtensions
				.<EStructuralFeature, String>sortBy(eObject.eClass().getEAllStructuralFeatures(), _function);
		for (final EStructuralFeature feature : _sortBy) {
			this.renderFiltered(feature, (indent + 1), eObject);
		}
		this.append("}", indent);
	}

	protected void _render(final EAttribute attribute, final int indent, final EObject context) {
		this.append(attribute.getName(), indent);
		this.builder.append("=");
		final Object value = context.eGet(attribute);
		boolean _isMany = attribute.isMany();
		if (_isMany) {
			final Consumer<Object> _function = (Object it) -> {
				this.renderAttribute(attribute, (indent + 1), context, it);
			};
			this.<Object>renderCollection(((Collection<Object>) value), indent, context, _function);
		} else {
			this.renderAttribute(attribute, indent, context, value);
		}
	}

	protected void renderAttribute(final EAttribute attribute, final int indent, final EObject context,
			final Object value) {
		boolean _matched = false;
		if (value instanceof CharSequence) {
			_matched = true;
			this.builder.append("\"");
			this.builder.append(((CharSequence) value));
			this.builder.append("\"");
		}
		if (!_matched) {
			this.builder.append(value);
		}
	}

	protected void _render(final EReference reference, final int indent, final EObject context) {
		this.append(reference.getName(), indent);
		boolean _isContainment = reference.isContainment();
		if (_isContainment) {
			this.builder.append(": ");
			this.renderFiltered(context.eGet(reference), indent, null);
		} else {
			this.builder.append(" -> ");
			final Object value = context.eGet(reference);
			boolean _isMany = reference.isMany();
			if (_isMany) {
				final Consumer<EObject> _function = (EObject it) -> {
					this.renderReference(reference, (indent + 1), context, it);
				};
				this.<EObject>renderCollection(((Collection<EObject>) value), indent, context, _function);
			} else {
				this.renderReference(reference, indent, context, ((EObject) value));
			}
		}
	}

	protected void renderReference(final EReference reference, final int indent, final EObject context,
			final EObject target) {
		if ((target != null)) {
			final String id = EcoreUtil.getID(target);
			boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(id);
			boolean _not = (!_isNullOrEmpty);
			if (_not) {
				this.builder.append(id);
			} else {
				boolean _isAncestor = EcoreUtil.isAncestor(context, target);
				if (_isAncestor) {
					this.builder.append(EcoreUtil.getRelativeURIFragmentPath(context, target));
				} else {
					this.builder.append(EcoreUtil.getURI(target));
				}
			}
		} else {
			this.builder.append("null");
		}
	}

	/**
	 * Appends an newline and {@code text} to the builder, indented by
	 * {@code indent} levels.
	 */
	protected void append(final Object text, final int indent) {
		this.appendIndex(indent);
		this.builder.append(text);
	}

	protected void appendIndex(final int indent) {
		this.builder.append(this.getNewline());
		for (int i = 0; (i < indent); i++) {
			this.builder.append(this.getIndentation());
		}
	}

	/**
	 * Filter to prevent selected Ecore elements from being rendered.
	 * 
	 * Must not be {@code null}.
	 */
	protected IRenderFilter getFilter() {
		return new OmitUnsetFilter();
	}

	protected String getNewline() {
		return "\n";
	}

	protected String getIndentation() {
		return "  ";
	}

	protected void render(final Object attribute, final int indent, final EObject context) {
		if (attribute instanceof EAttribute) {
			_render((EAttribute) attribute, indent, context);
			return;
		} else if (attribute instanceof EReference) {
			_render((EReference) attribute, indent, context);
			return;
		} else if (attribute instanceof Collection) {
			_render((Collection<EObject>) attribute, indent, context);
			return;
		} else if (attribute instanceof EObject) {
			_render((EObject) attribute, indent, context);
			return;
		} else if (attribute instanceof Resource) {
			_render((Resource) attribute, indent, context);
			return;
		} else {
			throw new IllegalArgumentException(
					"Unhandled parameter types: " + Arrays.<Object>asList(attribute, indent, context).toString());
		}
	}
}
