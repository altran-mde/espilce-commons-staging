package org.espilce.commons.emf.textrenderer.dependency.bundle.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.ecore.EObject;
import org.espilce.commons.emf.textrenderer.EmfStringRenderer;
import org.espilce.commons.emf.textrenderer.IRenderFilter;
import org.espilce.commons.emf.textrenderer.RenderFilterAdapter;
import org.junit.Test;

public class TestEmfTextrendererDependency {
	@SuppressWarnings({ "rawtypes", "unchecked", "null" })
	@Test
	public void emfStringRenderer() throws Exception {
		assertNotNull(new EmfStringRenderer((Collection) null));
	}
	
	@Test
	public void iRenderFilter() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		new IRenderFilter() {
			
			@Override
			public boolean shouldRender(final Object object, final int indent, final EObject context) {
				b.set(true);
				return false;
			}
		}.shouldRender(null, 0, null);
		
		assertTrue(b.get());
	}
	
	@Test
	public void renderFilterAdapter() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		new RenderFilterAdapter() {
			
			@Override
			public boolean shouldRender(final Object object, final int indent, final EObject context) {
				b.set(true);
				return false;
			}
		}.shouldRender(null, 0, null);
		
		assertTrue(b.get());
	}
}
