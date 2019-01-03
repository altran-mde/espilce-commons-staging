package org.espilce.commons.resource.test.loadhelper.workspace;

import static org.espilce.commons.resource.WorkspaceUtils.waitForWorkspaceChanges;
import static org.junit.Assert.assertEquals;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestGetContents;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGetContents extends ATestGetContents {
	protected IProject project;

	@Before
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

	@Override
	protected ILoadHelper createLoadHelper() {
		return new WorkspacePluginLoadHelper();
	}

	@Override
	protected void assertContents(final String relativePath, final String contents) {
		assertEquals(relativePath, "file.txt in workspace", contents);
	}
	
}
