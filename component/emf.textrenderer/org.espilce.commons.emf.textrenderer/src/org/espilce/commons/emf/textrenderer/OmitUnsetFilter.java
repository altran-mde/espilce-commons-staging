package org.espilce.commons.emf.textrenderer;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * {@link IRenderFilter} that omits rendering
 * {@linkplain EObject#eIsSet(org.eclipse.emf.ecore.EStructuralFeature) unset}
 * features.
 * 
 * @since 0.1
 */
public class OmitUnsetFilter extends RenderFilterAdapter {
	@Override
	protected boolean shouldRenderEAttribute(final EAttribute attribute, final int indent, final EObject context) {
		return context.eIsSet(attribute);
	}

	@Override
	protected boolean shuldRenderEReference(final EReference reference, final int indent, final EObject context) {
		return context.eIsSet(reference);
	}
}
