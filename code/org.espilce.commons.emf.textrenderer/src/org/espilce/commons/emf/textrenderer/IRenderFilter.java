package org.espilce.commons.emf.textrenderer;

import org.eclipse.emf.ecore.EObject;

/**
 * Filter to decide whether Ecore elements should be rendered.
 *
 * @author nstotz
 *
 * @see EmfStringRenderer
 */
@FunctionalInterface
public interface IRenderFilter {
	/**
	 * Decides whether {@code object} should be rendered.
	 *
	 * <p>
	 * {@code object} might be one of:
	 * </p>
	 *
	 * <ul>
	 * <li>{@link org.eclipse.emf.ecore.resource.ResourceSet ResourceSet}</li>
	 * <li>{@link org.eclipse.emf.ecore.resource.Resource Resource}</li>
	 * <li>{@link java.util.Collection Collection&lt;EObject&gt;}</li>
	 * <li>{@link org.eclipse.emf.ecore.EObject EObject}</li>
	 * <li>{@link org.eclipse.emf.ecore.EAttribute EAttribute}</li>
	 * <li>{@link org.eclipse.emf.ecore.EReference EReference}</li>
	 * </ul>
	 *
	 * @param object
	 *            Ecore element to decide whether to render.
	 * @param indent
	 *            Current indentation.
	 * @param context
	 *            Context of {@code object}. Only set if {@code object} is either an
	 *            {@link org.eclipse.emf.ecore.EAttribute EAttribute} or an {@link org.eclipse.emf.ecore.EReference
	 *            EReference}.
	 * @return Whether to render {@code object}.
	 */
	boolean shouldRender(Object object, int indent, EObject context);
}
