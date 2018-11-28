/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
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
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Creates a human-readable tree-like rendering of EMF contents.
 * 
 * @since 0.1
 */
public class EmfStringRenderer {
	private final StringBuilder builder = new StringBuilder();
	private final Resource resource;
	private final Collection<EObject> eObjects;
	private final EObject eObject;

	public EmfStringRenderer(final @NonNull Resource resource) {
		this.resource = resource;
		this.eObjects = Collections.emptySet();
		this.eObject = null;
		validateInput(false);
	}

	public EmfStringRenderer(final @NonNull Collection<@NonNull EObject> eObjects) {
		this.resource = null;
		this.eObjects = eObjects;
		this.eObject = null;
		validateInput(true);
	}

	public EmfStringRenderer(final @NonNull EObject eObject) {
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
	public @NonNull String render() {
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
	protected void renderFiltered(final @NonNull Object object, final int indent, final @Nullable EObject context) {
		if (getFilter().shouldRender(object, indent, context)) {
			render(object, indent, context);
		}
	}

	protected void renderResource(final @NonNull Resource resource, final int indent, final @Nullable EObject context) {
		append("Resource: ", indent);
		this.builder.append(resource.getURI());
		renderFiltered(resource.getContents(), (indent + 1), null);
	}

	protected void renderEObjectCollection(final @NonNull Collection<@NonNull EObject> eObjects, final int indent,
			final @NonNull EObject context) {
		renderCollection(eObjects, indent, context, eObject -> renderFiltered(eObject, (indent + 1), null));
	}

	protected <T extends Object> void renderCollection(final @NonNull Collection<@NonNull T> collection,
			final int indent, final @NonNull EObject context, final @NonNull Consumer<@NonNull T> lineRenderer) {
		this.builder.append("[");
		for (final T element : collection) {
			{
				append("", (indent + 1));
				lineRenderer.accept(element);
			}
		}
		append("]", indent);
	}

	protected void renderEObject(final @NonNull EObject eObject, final int indent, final @Nullable EObject context) {
		this.builder.append(eObject.eClass().getName());
		this.builder.append(" {");
		eObject.eClass().getEAllStructuralFeatures().stream().sorted((a, b) -> a.getName().compareTo(b.getName()))
				.forEachOrdered(feature -> renderFiltered(feature, (indent + 1), eObject));
		append("}", indent);
	}

	protected void renderEAttribute(final @NonNull EAttribute attribute, final int indent,
			final @NonNull EObject context) {
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

	protected void renderAttribute(final @NonNull EAttribute attribute, final int indent,
			final @Nullable EObject context, final @Nullable Object value) {
		if (value instanceof CharSequence) {
			this.builder.append("\"");
			this.builder.append((CharSequence) value);
			this.builder.append("\"");
		} else {
			this.builder.append(value);
		}
	}

	protected void renderEReference(final @NonNull EReference reference, final int indent,
			final @NonNull EObject context) {
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

	protected void renderReference(final @NonNull EReference reference, final int indent,
			final @NonNull EObject context, final @Nullable EObject target) {
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
	 * Appends an newline and <code>text</code> to the builder, indented by
	 * <code>indent</code> levels.
	 */
	protected void append(final @Nullable Object text, final int indent) {
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
	 */
	protected @NonNull IRenderFilter getFilter() {
		return new OmitUnsetFilter();
	}

	protected @NonNull String getNewline() {
		return "\n";
	}

	protected @NonNull String getIndentation() {
		return "  ";
	}

	protected void render(final @NonNull Object attribute, final int indent, final @Nullable EObject context) {
		if (attribute instanceof EAttribute && context != null) {
			renderEAttribute((EAttribute) attribute, indent, context);
			return;
		} else if (attribute instanceof EReference && context != null) {
			renderEReference((EReference) attribute, indent, context);
			return;
		} else if (attribute instanceof Collection && context != null) {
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
