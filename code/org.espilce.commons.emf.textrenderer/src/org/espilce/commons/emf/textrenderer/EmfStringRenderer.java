package org.espilce.commons.emf.textrenderer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Creates a human-readable tree-like rendering of EMF contents.
 * 
 * @author nstotz
 */
public class EmfStringRenderer {
	private final StringBuilder builder = new StringBuilder();
	private final Resource resource;
	private final Collection<EObject> eObjects;
	private final EObject eObject;

	public EmfStringRenderer(final Resource resource) {
		this.resource = resource;
		this.eObjects = Collections.emptySet();
		this.eObject = null;
		validateInput(false);
	}

	public EmfStringRenderer(final Collection<EObject> eObjects) {
		this.resource = null;
		this.eObjects = eObjects;
		this.eObject = null;
		validateInput(true);
	}

	public EmfStringRenderer(final EObject eObject) {
		this.resource = null;
		this.eObjects = Collections.emptySet();
		this.eObject = eObject;
		validateInput(false);
	}

	protected void validateInput(final boolean allowEmptySet) {
		if (this.resource == null && this.eObject == null && !allowEmptySet && this.eObjects.isEmpty()) {
			throw new IllegalArgumentException("Invalid constructor parameter.");
		}
	}

	/**
	 * Public API to render.
	 */
	public String render() {
		if (this.builder.length() == 0) {
			doRender();
		}

		return this.builder.toString();
	}

	/**
	 * Switch to render the actually passed constructor parameter.
	 */
	protected void doRender() {
		if (this.resource != null) {
			renderFiltered(this.resource, 0, null);
		} else {
			if (this.eObject != null) {
				renderFiltered(this.eObject, 0, null);
			} else {
				renderFiltered(this.eObjects, 0, null);
			}
		}
	}

	/**
	 * {@link #render()} call, guarded by {@link #getFilter()}.
	 */
	protected void renderFiltered(final Object object, final int indent, final EObject context) {
		if (getFilter().shouldRender(object, indent, context)) {
			render(object, indent, context);
		}
	}

	protected void renderResource(final Resource resource, final int indent, final EObject context) {
		append("Resource: ", indent);
		this.builder.append(resource.getURI());
		renderFiltered(resource.getContents(), (indent + 1), null);
	}

	protected void renderEObjectCollection(final Collection<EObject> eObjects, final int indent,
			final EObject context) {
		renderCollection(eObjects, indent, context, eObject -> renderFiltered(eObject, (indent + 1), null));
	}

	protected <T extends Object> void renderCollection(final Collection<T> collection, final int indent,
			final EObject context, final Consumer<T> lineRenderer) {
		this.builder.append("[");
		for (final T element : collection) {
			{
				append("", (indent + 1));
				lineRenderer.accept(element);
			}
		}
		append("]", indent);
	}

	protected void renderEObject(final EObject eObject, final int indent, final EObject context) {
		this.builder.append(eObject.eClass().getName());
		this.builder.append(" {");
		eObject.eClass().getEAllStructuralFeatures().stream().sorted((a, b) -> a.getName().compareTo(b.getName()))
				.forEachOrdered(feature -> renderFiltered(feature, (indent + 1), eObject));
		append("}", indent);
	}

	protected void renderEAttribute(final EAttribute attribute, final int indent, final EObject context) {
		append(attribute.getName(), indent);
		this.builder.append("=");
		final Object value = context.eGet(attribute);
		if (attribute.isMany()) {
			@SuppressWarnings("unchecked")
			final Collection<Object> collection = (Collection<Object>) value;
			renderCollection(collection, indent, context,
					val -> renderAttribute(attribute, (indent + 1), context, val));
		} else {
			renderAttribute(attribute, indent, context, value);
		}
	}

	protected void renderAttribute(final EAttribute attribute, final int indent, final EObject context,
			final Object value) {
		if (value instanceof CharSequence) {
			this.builder.append("\"");
			this.builder.append((CharSequence) value);
			this.builder.append("\"");
		} else {
			this.builder.append(value);
		}
	}

	protected void renderEReference(final EReference reference, final int indent, final EObject context) {
		append(reference.getName(), indent);
		if (reference.isContainment()) {
			this.builder.append(": ");
			renderFiltered(context.eGet(reference), indent, null);
		} else {
			this.builder.append(" -> ");
			final Object value = context.eGet(reference);
			if (reference.isMany()) {
				@SuppressWarnings("unchecked")
				final Collection<EObject> collection = (Collection<EObject>) value;
				renderCollection(collection, indent, context,
						eObject -> renderReference(reference, (indent + 1), context, eObject));
			} else {
				renderReference(reference, indent, context, ((EObject) value));
			}
		}
	}

	protected void renderReference(final EReference reference, final int indent, final EObject context,
			final EObject target) {
		if (target != null) {
			final String id = EcoreUtil.getID(target);
			if (id != null && id.length() > 0) {
				this.builder.append(id);
			} else {
				if (EcoreUtil.isAncestor(context, target)) {
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
		appendIndex(indent);
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
			renderEAttribute((EAttribute) attribute, indent, context);
			return;
		} else if (attribute instanceof EReference) {
			renderEReference((EReference) attribute, indent, context);
			return;
		} else if (attribute instanceof Collection) {
			@SuppressWarnings("unchecked")
			final Collection<EObject> collection = (Collection<EObject>) attribute;
			renderEObjectCollection(collection, indent, context);
			return;
		} else if (attribute instanceof EObject) {
			renderEObject((EObject) attribute, indent, context);
			return;
		} else if (attribute instanceof Resource) {
			renderResource((Resource) attribute, indent, context);
			return;
		} else {
			throw new IllegalArgumentException(
					"Unhandled parameter types: " + Arrays.<Object>asList(attribute, indent, context).toString());
		}
	}
}
