package org.espilce.commons.resource.dependency.bundle.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.resources.ResourcesPlugin;
import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.exception.UnmappableException;
import org.espilce.commons.resource.ContentTypeUtils;
import org.espilce.commons.resource.ResourceUtils;
import org.espilce.commons.resource.WorkspaceUtils;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.junit.Test;

public class TestResourceDependency {
	@Test
	public void contentTypeUtils() throws Exception {
		try {
			assertNotNull(ContentTypeUtils.findContentType("org.eclipse.core.runtime.text"));
		} catch (final UnmappableException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void resourceUtils() throws Exception {
		try {
			assertNotNull(ResourceUtils.asIPath(URI.create("/")));
			assertNotNull(ResourceUtils.asIResource(URI.create("/")));
		} catch (final UnconvertibleException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void workspaceUtils() throws Exception {
		final AtomicBoolean b = new AtomicBoolean(false);
		WorkspaceUtils.waitForWorkspaceChanges(() -> {
			ResourcesPlugin.getWorkspace().getRoot().getProject("x").create(null);
			b.set(true);
		});
		assertTrue(b.get());
	}
	
	@Test
	public void workspacePluginLoadHelper() throws Exception {
		assertNotNull(new WorkspacePluginLoadHelper().findMatchingResources(this.getClass(), "."));
	}
}
