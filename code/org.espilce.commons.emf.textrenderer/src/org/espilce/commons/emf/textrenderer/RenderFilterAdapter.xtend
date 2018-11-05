package org.espilce.commons.emf.textrenderer

import java.util.Collection
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet

/** 
 * Default implementation of {@link IRenderFilter}.
 * 
 * @author nstotz
 */
abstract class RenderFilterAdapter implements IRenderFilter {
	def dispatch boolean shouldRender(ResourceSet resourceSet, int indent, EObject context) {
		true
	}

	def dispatch boolean shouldRender(Resource resource, int indent, EObject context) {
		true
	}

	def dispatch boolean shouldRender(Collection<EObject> eObjects, int indent, EObject context) {
		true
	}

	def dispatch boolean shouldRender(EObject eObject, int indent, EObject context) {
		true
	}

	def dispatch boolean shouldRender(EAttribute attribute, int indent, EObject context) {
		true
	}

	def dispatch boolean shouldRender(EReference reference, int indent, EObject context) {
		true
	}
}
