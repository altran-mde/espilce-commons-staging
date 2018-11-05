package org.espilce.commons.emf.textrenderer;

import java.util.Arrays;
import java.util.Collection;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.espilce.commons.emf.textrenderer.RenderFilterAdapter;

/**
 * {@link IRenderFilter} that omits rendering
 * {@link EObject#eIsSet(org.eclipse.emf.ecore.EStructuralFeature) unset}
 * features.
 * 
 * @author nstotz
 */
@SuppressWarnings("all")
public class OmitUnsetFilter extends RenderFilterAdapter {
	@Override
	protected boolean _shouldRender(final EAttribute attribute, final int indent, final EObject context) {
		return context.eIsSet(attribute);
	}

	@Override
	protected boolean _shouldRender(final EReference reference, final int indent, final EObject context) {
		return context.eIsSet(reference);
	}

	@Override
	public boolean shouldRender(final Object attribute, final int indent, final EObject context) {
		if (attribute instanceof EAttribute) {
			return _shouldRender((EAttribute) attribute, indent, context);
		} else if (attribute instanceof EReference) {
			return _shouldRender((EReference) attribute, indent, context);
		} else if (attribute instanceof Collection) {
			return _shouldRender((Collection<EObject>) attribute, indent, context);
		} else if (attribute instanceof EObject) {
			return _shouldRender((EObject) attribute, indent, context);
		} else if (attribute instanceof Resource) {
			return _shouldRender((Resource) attribute, indent, context);
		} else if (attribute instanceof ResourceSet) {
			return _shouldRender((ResourceSet) attribute, indent, context);
		} else {
			throw new IllegalArgumentException(
					"Unhandled parameter types: " + Arrays.<Object>asList(attribute, indent, context).toString());
		}
	}
}
