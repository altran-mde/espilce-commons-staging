package org.espilce.commons.resource.third.test.loadhelper.mixed;

import static org.espilce.commons.resource.WorkspaceUtils.waitForWorkspaceChanges;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestGetContents;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestGetContents_workspace extends ATestGetContents {
	protected IProject project;
	
	@BeforeEach
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
	
	@AfterEach
	public void destroyProject() throws Exception {
		if (this.project != null) {
			waitForWorkspaceChanges(() -> this.project.delete(true, true, null));
		}
	}
	
	@Override
	@Test
	public void rootFile() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> super.rootFile()
		);
	}
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	@Disabled
	public void existingFileStartSlash() throws Exception {
		super.existingFileStartSlash();
	}
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new WorkspacePluginLoadHelper();
	}
	
	@Override
	protected void assertContents(final String relativePath, final String contents) {
		assertEquals(contents, "fileWorkspace.txt in workspace", relativePath);
	}
	
	@Override
	protected String file() {
		return "fileWorkspace.txt";
	}
	
	@Override
	protected String dir() {
		return "dirWorkspace";
	}
}
