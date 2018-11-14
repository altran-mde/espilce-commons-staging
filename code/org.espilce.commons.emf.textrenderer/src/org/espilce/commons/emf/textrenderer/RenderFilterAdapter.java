package org.espilce.commons.emf.textrenderer;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Default implementation of {@link IRenderFilter}.
 * 
 * @author nstotz
 */
public abstract class RenderFilterAdapter implements IRenderFilter {
	protected boolean shouldRenderResourceSet(final ResourceSet resourceSet, final int indent, final EObject context) {
		return true;
	}

	protected boolean shouldRenderResource(final Resource resource, final int indent, final EObject context) {
		return true;
	}

	protected boolean shouldRenderCollection(final Collection<?> collection, final int indent, final EObject context) {
		return true;
	}

	protected boolean shouldRenderEObject(final EObject eObject, final int indent, final EObject context) {
		return true;
	}

	protected boolean shouldRenderEAttribute(final EAttribute attribute, final int indent, final EObject context) {
		return true;
	}

	protected boolean shuldRenderEReference(final EReference reference, final int indent, final EObject context) {
		return true;
	}

	@Override
	public boolean shouldRender(final Object attribute, final int indent, final EObject context) {
		if (attribute instanceof EAttribute) {
			return shouldRenderEAttribute((EAttribute) attribute, indent, context);
		} else if (attribute instanceof EReference) {
			return shuldRenderEReference((EReference) attribute, indent, context);
		} else if (attribute instanceof Collection) {
			return shouldRenderCollection((Collection<?>) attribute, indent, context);
		} else if (attribute instanceof EObject) {
			return shouldRenderEObject((EObject) attribute, indent, context);
		} else if (attribute instanceof Resource) {
			return shouldRenderResource((Resource) attribute, indent, context);
		} else if (attribute instanceof ResourceSet) {
			return shouldRenderResourceSet((ResourceSet) attribute, indent, context);
		} else {
			throw new IllegalArgumentException(
					"Unhandled parameter types: " + Arrays.<Object>asList(attribute, indent, context).toString());
		}
	}
}
