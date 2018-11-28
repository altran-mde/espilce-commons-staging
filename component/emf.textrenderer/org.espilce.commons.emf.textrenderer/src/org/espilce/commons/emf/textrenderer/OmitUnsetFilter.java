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
