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
 * Filter to decide whether EMF elements should be rendered.
 *
 * @see EmfStringRenderer
 * @since 0.1
 */
@FunctionalInterface
public interface IRenderFilter {
	/**
	 * Decides whether <code>object</code> should be rendered.
	 *
	 * <p>
	 * <code>object</code> might be one of:
	 * </p>
	 *
	 * <ul>
	 * <li>{@link org.eclipse.emf.ecore.resource.ResourceSet ResourceSet}</li>
	 * <li>{@link org.eclipse.emf.ecore.resource.Resource Resource}</li>
	 * <li>{@link java.util.Collection Collection&lt;Object&gt;}</li>
	 * <li>{@link org.eclipse.emf.ecore.EObject EObject}</li>
	 * <li>{@link org.eclipse.emf.ecore.EAttribute EAttribute}</li>
	 * <li>{@link org.eclipse.emf.ecore.EReference EReference}</li>
	 * </ul>
	 *
	 * @param object
	 *            EMF element to decide whether to render.
	 * @param indent
	 *            Current indentation.
	 * @param context
	 *            Context of <code>object</code>. Only set if <code>object</code> is
	 *            either an {@link org.eclipse.emf.ecore.EAttribute EAttribute} or
	 *            an {@link org.eclipse.emf.ecore.EReference EReference}.
	 * @return Whether to render <code>object</code>.
	 */
	boolean shouldRender(Object object, int indent, EObject context);
}
