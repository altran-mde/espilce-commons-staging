package org.espilce.commons.emf.textrenderer

import java.util.Collection
import java.util.function.Consumer
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * Creates a human-readable tree-like rendering of EMF contents.
 * 
 * @author nstotz
 */
class EmfStringRenderer {
	val builder = new StringBuilder()

	val Resource resource
	val Collection<EObject> eObjects
	val EObject eObject

	new(Resource resource) {
		this.resource = resource
		this.eObjects = emptySet
		this.eObject = null
		validateInput(false);
	}

	new(Collection<EObject> eObjects) {
		this.resource = null
		this.eObjects = eObjects
		this.eObject = null
		validateInput(true);
	}

	new(EObject eObject) {
		this.resource = null
		this.eObjects = emptySet
		this.eObject = eObject
		validateInput(false);
	}

	def protected validateInput(boolean allowEmptySet) {
		if (resource === null && eObject === null && !allowEmptySet && eObjects.empty)
		{
			throw new IllegalArgumentException("Invalid constructor parameter.");
		}
	}

	/** Public API to render. */
	def render() {
		if (builder.length == 0) {
			doRender()
		}

		return builder.toString
	}

	/** Switch to render the actually passed constructor parameter. */
	def protected doRender() {
		if (this.resource !== null) {
			renderFiltered(this.resource, 0, null)
		}
		// check this.eObject first, as this.eObjects can legally be empty
		else if (this.eObject !== null) {
			renderFiltered(this.eObject, 0, null)
		} else {
			renderFiltered(this.eObjects, 0, null)
		}
	}

	/** {@link #render()} call, guarded by {@link #getFilter()}. */
	def protected void renderFiltered(Object object, int indent, EObject context) {
		if (getFilter().shouldRender(object, indent, context)) {
			render(object, indent, context)
		}
	}

	def protected dispatch void render(Resource resource, int indent, EObject context) {
		append("Resource: ", indent)
		builder.append(resource.getURI)
		renderFiltered(resource.contents, indent + 1, null)
	}

	def protected dispatch void render(Collection<EObject> eObjects, int indent, EObject context) {
		renderCollection(eObjects, indent, context) [
			renderFiltered(it, indent + 1, null)
		]
	}

	def protected <T> void renderCollection(Collection<T> collection, int indent, EObject context,
		Consumer<T> lineRenderer) {
		builder.append("[")
		for (element : collection) {
			append("", indent + 1)
			lineRenderer.accept(element)
		}
		append("]", indent)
	}

	def protected dispatch void render(EObject eObject, int indent, EObject context) {
		builder.append(eObject.eClass.name)
		builder.append(" {")
		for (feature : eObject.eClass.getEAllStructuralFeatures.sortBy[it.name]) {
			renderFiltered(feature, indent + 1, eObject)
		}
		append("}", indent)
	}

	def protected dispatch void render(EAttribute attribute, int indent, EObject context) {
		append(attribute.name, indent)
		builder.append("=")
		val value = context.eGet(attribute)
		if (attribute.isMany) {
			renderCollection(value as Collection<Object>, indent, context) [
				renderAttribute(attribute, indent + 1, context, it)
			]
		} else {
			renderAttribute(attribute, indent, context, value)
		}
	}

	def protected void renderAttribute(EAttribute attribute, int indent, EObject context, Object value) {
		switch (value) {
			CharSequence: {
				builder.append('"')
				builder.append(value)
				builder.append('"')
			}
			
			default:
				builder.append(value)
		}
	}

	def protected dispatch void render(EReference reference, int indent, EObject context) {
		append(reference.name, indent)
		if (reference.isContainment) {
			builder.append(": ")
			renderFiltered(context.eGet(reference), indent, null)
		} else {
			builder.append(" -> ")
			val value = context.eGet(reference)
			if (reference.isMany) {
				renderCollection(value as Collection<EObject>, indent, context) [
					renderReference(reference, indent + 1, context, it)
				]
			} else {
				renderReference(reference, indent, context, value as EObject)
			}
		}
	}

	def protected void renderReference(EReference reference, int indent, EObject context, EObject target) {
		if (target !== null) {
			val id = EcoreUtil.getID(target)
			if (!id.isNullOrEmpty) {
				builder.append(id)
			} else {
				if (EcoreUtil.isAncestor(context, target)) {
					builder.append(EcoreUtil.getRelativeURIFragmentPath(context, target))
				} else {
					builder.append(EcoreUtil.getURI(target))
				}
			}
		} else {
			builder.append("null")
		}
	}

	/** Appends an newline and {@code text} to the builder, indented by {@code indent} levels. */
	def protected void append(Object text, int indent) {
		appendIndex(indent)
		builder.append(text)
	}

	def protected void appendIndex(int indent) {
		builder.append(newline)
		for (var i = 0; i < indent; i++) {
			builder.append(indentation)
		}
	}

	/** 
	 * Filter to prevent selected Ecore elements from being rendered.
	 * 
	 * Must not be {@code null}.
	 */
	def protected IRenderFilter getFilter() {
		new OmitUnsetFilter()
	}

	def protected getNewline() {
		"\n"
	}

	def protected getIndentation() {
		"  "
	}
}
