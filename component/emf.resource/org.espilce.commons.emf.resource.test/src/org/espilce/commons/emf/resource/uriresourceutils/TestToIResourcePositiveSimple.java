package org.espilce.commons.emf.resource.uriresourceutils;

import static org.espilce.commons.resource.WorkspaceUtils.waitForWorkspaceChanges;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.resource.UriResourceUtils;
import org.espilce.commons.emf.testsupport.resource.ATestWorkspace;
import org.espilce.commons.testsupport.resource.builder.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestToIResourcePositiveSimple extends ATestWorkspace {
	@BeforeEach
	public void createProject() throws Exception {
		waitForWorkspaceChanges(
				() -> {
					this.project = new ProjectBuilder("myProject").build();
				}
		);
	}
	
	@Test
	public void fileMissing() throws Exception {
		final URI uri = URI.createPlatformResourceURI("/myProject/myFile.ext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertFalse(iResource.exists());
		assertEquals("/myProject/myFile.ext", iResource.getFullPath().toString());
	}
	
	@Test
	public void fileWorkspaceLinkMissing() throws Exception {
		waitForWorkspaceChanges(() -> {
			final java.net.URI uri = this.project.getPathVariableManager()
					.resolveURI(new java.net.URI("WORKSPACE_LOC/myProject/myFile.ext"));
			this.project.getFile("myLink.otherext").createLink(uri, IResource.ALLOW_MISSING_LOCAL, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myLink.otherext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myLink.otherext", iResource.getFullPath().toString());
	}
	
	@Test
	public void fileExternalLinkExists() throws Exception {
		waitForWorkspaceChanges(() -> {
			final File tempFile = File.createTempFile(this.getClass().getSimpleName(), "");
			tempFile.deleteOnExit();
			final java.net.URI uri = tempFile.toURI();
			this.project.getFile("myLink.otherext").createLink(uri, IResource.NONE, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myLink.otherext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myLink.otherext", iResource.getFullPath().toString());
	}
	
	@Test
	public void fileExternalLinkMissing() throws Exception {
		waitForWorkspaceChanges(() -> {
			final File tempFile = File.createTempFile(this.getClass().getSimpleName(), "");
			tempFile.delete();
			final java.net.URI uri = tempFile.toURI();
			this.project.getFile("myLink.otherext").createLink(uri, IResource.ALLOW_MISSING_LOCAL, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myLink.otherext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myLink.otherext", iResource.getFullPath().toString());
	}
	
	@Test
	public void folderWorkspaceLinkMissing() throws Exception {
		waitForWorkspaceChanges(() -> {
			final java.net.URI uri = this.project.getPathVariableManager()
					.resolveURI(new java.net.URI("WORKSPACE_LOC/myProject/myFolder"));
			this.project.getFolder("myLink.otherext").createLink(uri, IResource.ALLOW_MISSING_LOCAL, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myLink.otherext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myLink.otherext", iResource.getFullPath().toString());
	}
	
	@Test
	public void folderExternalLinkExists() throws Exception {
		waitForWorkspaceChanges(() -> {
			final File tempDir = Files.createTempDirectory(this.getClass().getSimpleName()).toFile();
			tempDir.deleteOnExit();
			final java.net.URI uri = tempDir.toURI();
			this.project.getFolder("myLink.otherext").createLink(uri, IResource.NONE, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myLink.otherext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myLink.otherext", iResource.getFullPath().toString());
	}
	
	@Test
	public void folderExternalLinkMissing() throws Exception {
		waitForWorkspaceChanges(() -> {
			final File tempDir = Files.createTempDirectory(this.getClass().getSimpleName()).toFile();
			tempDir.delete();
			final java.net.URI uri = tempDir.toURI();
			this.project.getFolder("myLink.otherext").createLink(uri, IResource.ALLOW_MISSING_LOCAL, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myLink.otherext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myLink.otherext", iResource.getFullPath().toString());
	}
	
	@Test
	public void project() throws Exception {
		final URI uri = URI.createPlatformResourceURI("/myProject", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IProject);
		assertTrue(iResource.exists());
		assertEquals("/myProject", iResource.getFullPath().toString());
	}
	
	@Test
	public void projectSlash() throws Exception {
		final URI uri = URI.createPlatformResourceURI("/myProject/", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IProject);
		assertTrue(iResource.exists());
		assertEquals("/myProject", iResource.getFullPath().toString());
	}
	
	@Test
	public void projectSlashes() throws Exception {
		final URI uri = URI.createPlatformResourceURI("/myProject///", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IProject);
		assertTrue(iResource.exists());
		assertEquals("/myProject", iResource.getFullPath().toString());
	}
	
	@Test
	public void workspaceRoot() throws Exception {
		final URI uri = URI.createPlatformResourceURI("", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IWorkspaceRoot);
		assertTrue(iResource.exists());
		assertEquals("/", iResource.getFullPath().toString());
	}
	
	@Test
	public void workspaceRootSlash() throws Exception {
		final URI uri = URI.createPlatformResourceURI("/", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IWorkspaceRoot);
		assertTrue(iResource.exists());
		assertEquals("/", iResource.getFullPath().toString());
	}
	
	@Test
	public void workspaceRootSlashes() throws Exception {
		final URI uri = URI.createPlatformResourceURI("///", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IWorkspaceRoot);
		assertTrue(iResource.exists());
		assertEquals("/", iResource.getFullPath().toString());
	}
	
}
