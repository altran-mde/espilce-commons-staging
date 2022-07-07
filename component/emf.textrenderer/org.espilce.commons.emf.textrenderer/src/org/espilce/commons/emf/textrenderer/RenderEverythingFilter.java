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

import org.eclipse.emf.ecore.EObject;

/**
 * Dummy {@link IRenderFilter} that renders everything, i.e. does not omit
 * anything.
 * 
 * @since 0.1
 */
public class RenderEverythingFilter implements IRenderFilter {
	public final static RenderEverythingFilter INSTANCE = new RenderEverythingFilter();
	
	private RenderEverythingFilter() {
		
	}
	
	@Override
	public boolean shouldRender(final Object object, final int indent, final EObject context) {
		return true;
	}
	
}
