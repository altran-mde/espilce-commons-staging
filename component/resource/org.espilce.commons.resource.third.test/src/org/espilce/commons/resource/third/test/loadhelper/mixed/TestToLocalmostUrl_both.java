package org.espilce.commons.resource.third.test.loadhelper.mixed;

import static org.espilce.commons.resource.WorkspaceUtils.waitForWorkspaceChanges;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestToLocalmostUrl;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestToLocalmostUrl_both extends ATestToLocalmostUrl {
	protected IProject project;
	
	@BeforeEach
	public void createProject() throws Exception {
		waitForWorkspaceChanges(() -> {
			this.project = ResourcesPlugin.getWorkspace().getRoot().getProject("some");
			this.project.create(null);
			this.project.open(null);
			
			final IFolder folder = this.project.getFolder("dir");
			folder.create(true, true, null);
			
			final IFile file = folder.getFile("file.txt");
			file.create(IOUtils.toInputStream("file.txt in workspace"), true, null);
		});
	}
	
	@AfterEach
	public void destroyProject() throws Exception {
		if (this.project != null) {
			waitForWorkspaceChanges(() -> this.project.delete(true, true, null));
		}
	}
	
	@Override
	@Test
	public void rootFile() throws Exception {
		checkToLocalmostUrlInverse(file());
	}
	
	@Override
	@Test
	public void rootFileStartSlash() throws Exception {
		checkToLocalmostUrlInverse(separator() + file());
	}
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	public void existingFileStartSlash() throws Exception {
		super.existingFileStartSlash();
	}
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	public void existingDirStartSlash() throws Exception {
		super.existingDirStartSlash();
	}
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	public void existingDirStartEndSlash() throws Exception {
		super.existingDirStartEndSlash();
	}
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new WorkspacePluginLoadHelper();
	}
	
	@Override
	protected void assertUrl(final String relativePath, final URL localmostUrl) {
		final String str = localmostUrl.toString();
		assertTrue(str.contains("/testWorkspace/"), str);
		assertNotEquals("bundleentry", localmostUrl.getProtocol());
	}
	
	protected void checkToLocalmostUrlInverse(final String relativePath) throws Exception {
		assertUrlInverse(relativePath, createLoadHelper().toLocalmostUrl(getClass(), relativePath));
	}
	
	protected void assertUrlInverse(final String relativePath, final URL localmostUrl) {
		assertEquals("bundleentry", localmostUrl.getProtocol());
		final String str = localmostUrl.toString();
		assertFalse(str.contains("/testWorkspace/"), str);
	}
	
}
