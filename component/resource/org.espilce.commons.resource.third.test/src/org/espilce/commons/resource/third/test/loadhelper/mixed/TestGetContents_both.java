package org.espilce.commons.resource.third.test.loadhelper.mixed;

import static org.espilce.commons.resource.WorkspaceUtils.waitForWorkspaceChanges;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

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
import org.junit.jupiter.api.Test;

public class TestGetContents_both extends ATestGetContents {
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
		checkGetContentsInverse(file());
	}
	
	@Override
	@Test
	public void rootFileStartSlash() throws Exception {
		checkGetContentsInverse(separator() + file());
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
		if (relativePath.contains(dir())) {
			assertEquals(contents, "file.txt in workspace", relativePath);
		} else {
			assertEquals(contents, "file.txt in workspace root", relativePath);
		}
	}
	
	protected void checkGetContentsInverse(final String relativePath) throws Exception {
		final InputStream contents = createLoadHelper().getContents(getClass(), relativePath);
		try {
			assertContentsInverse(relativePath, IOUtils.toString(contents));
		} finally {
			IOUtils.closeQuietly(contents);
		}
	}
	
	protected void assertContentsInverse(final String relativePath, final String contents) {
		if (relativePath.contains(dir())) {
			assertEquals(contents, "file.txt in plugin", relativePath);
		} else {
			assertEquals(contents, "file.txt in plugin root", relativePath);
		}
	}
	
}
