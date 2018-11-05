package org.espilce.commons.emf.textrenderer;

import org.eclipse.emf.ecore.EObject;

/**
 * Dummy {@link IRenderFilter} that renders everything, i.e. does not omit
 * anything.
 * 
 * @author nstotz
 *
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
