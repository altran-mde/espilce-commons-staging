package org.espilce.commons.emf.textrenderer

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference

/**
 * {@link IRenderFilter} that omits rendering {@link EObject#eIsSet(org.eclipse.emf.ecore.EStructuralFeature) unset} features.
 * 
 * @author nstotz
 */
class OmitUnsetFilter extends RenderFilterAdapter {
	override dispatch boolean shouldRender(EAttribute attribute, int indent, EObject context) {
		context.eIsSet(attribute)
	}

	override dispatch boolean shouldRender(EReference reference, int indent, EObject context) {
		context.eIsSet(reference)
	}
}
