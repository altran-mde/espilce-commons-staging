package org.espilce.commons.resource.third.test.loadhelper.mixed;

import static org.espilce.commons.emf.resource.WorkspaceUtils.waitForWorkspaceChanges;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestToLocalmostUrl;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestToLocalmostUrl_workspace extends ATestToLocalmostUrl {
	protected IProject project;
	
	@Before
	public void createProject() throws Exception {
		waitForWorkspaceChanges(() -> {
			this.project = ResourcesPlugin.getWorkspace().getRoot().getProject("some");
			this.project.create(null);
			this.project.open(null);

			final IFolder folder = this.project.getFolder("dirWorkspace");
			folder.create(true, true, null);

			final IFile file = folder.getFile("fileWorkspace.txt");
			file.create(IOUtils.toInputStream("fileWorkspace.txt in workspace"), true, null);
		});
	}
	
	@After
	public void destroyProject() throws Exception {
		if (this.project != null) {
			waitForWorkspaceChanges(() -> this.project.delete(true, true, null));
		}
	}
	
	@Override
	@Test(expected = IllegalArgumentException.class)
	public void rootFile() throws Exception {
		super.rootFile();
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
	protected String file() {
		return "fileWorkspace.txt";
	}

	@Override
	protected String dir() {
		return "dirWorkspace";
	}
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new WorkspacePluginLoadHelper();
	}

	@Override
	protected void assertUrl(final String relativePath, final URL localmostUrl) {
		final String str = localmostUrl.toString();
		assertTrue(str, str.contains("/testWorkspace/"));
		assertNotEquals("bundleentry", localmostUrl.getProtocol());
	}
	
}
