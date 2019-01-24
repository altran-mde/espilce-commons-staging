/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.resource.uriresourceutils;

import static org.espilce.commons.resource.WorkspaceUtils.waitForWorkspaceChanges;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;

import org.apache.commons.io.input.NullInputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.resource.UriResourceUtils;
import org.espilce.commons.emf.testsupport.resource.ATestWorkspace;
import org.junit.Test;

/**
 * Test cases for valid uses of {@link UriResourceUtils#toIResource(URI)}.
 * 
 */
public class TestToIResourcePositive extends ATestWorkspace {
	@Test
	public void file() throws Exception {
		waitForWorkspaceChanges(() -> {
			final IFile file = this.project.getFile("myFile.ext");
			file.create(new NullInputStream(0), true, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFile.ext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFile.ext", iResource.getFullPath().toString());
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
	public void fileNested() throws Exception {
		waitForWorkspaceChanges(() -> {
			this.project.getFolder("/folder").create(true, true, null);
			this.project.getFolder("/folder/deep/").create(true, true, null);
			final IFile file = this.project.getFile("/folder/deep/myFile.ext");
			file.create(new NullInputStream(0), true, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/folder/deep/myFile.ext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/folder/deep/myFile.ext", iResource.getFullPath().toString());
	}
	
	@Test
	public void fileSlashesExcess() throws Exception {
		waitForWorkspaceChanges(() -> {
			this.project.getFolder("/folder").create(true, true, null);
			this.project.getFolder("/folder/deep/").create(true, true, null);
			final IFile file = this.project.getFile("/folder/deep/myFile.ext");
			file.create(new NullInputStream(0), true, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("////myProject///folder///deep/myFile.ext//", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/folder/deep/myFile.ext", iResource.getFullPath().toString());
	}
	
	@Test
	public void fileSlash() throws Exception {
		waitForWorkspaceChanges(() -> {
			final IFile file = this.project.getFile("myFile.ext");
			file.create(new NullInputStream(0), true, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFile.ext/", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFile.ext", iResource.getFullPath().toString());
	}
	
	@Test
	public void fileDifferentCase() throws Exception {
		waitForWorkspaceChanges(() -> {
			final IFile file = this.project.getFile("myFile.ext");
			file.create(new NullInputStream(0), true, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/MYfILE.ext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertFalse(iResource.exists());
		assertEquals("/myProject/MYfILE.ext", iResource.getFullPath().toString());
	}
	
	@Test
	public void fileWorkspaceLinkExists() throws Exception {
		waitForWorkspaceChanges(() -> {
			final IFile file = this.project.getFile("myFile.ext");
			file.create(new NullInputStream(0), true, null);
			final java.net.URI uri = this.project.getPathVariableManager()
					.resolveURI(new java.net.URI("WORKSPACE_LOC/myProject/myFile.ext"));
			this.project.getFile("myLink.otherext").createLink(uri, IResource.NONE, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myLink.otherext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myLink.otherext", iResource.getFullPath().toString());
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
	public void folderWorkspaceLinkExists() throws Exception {
		waitForWorkspaceChanges(() -> {
			final IFolder folder = this.project.getFolder("myFolder");
			folder.create(true, true, null);
			final java.net.URI uri = this.project.getPathVariableManager()
					.resolveURI(new java.net.URI("WORKSPACE_LOC/myProject/myFolder"));
			this.project.getFolder("myLink.otherext").createLink(uri, IResource.NONE, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myLink.otherext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
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
	public void folder() throws Exception {
		waitForWorkspaceChanges(() -> {
			final IFolder folder = this.project.getFolder("myFolder");
			folder.create(true, true, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFolder", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFolder", iResource.getFullPath().toString());
	}
	
	@Test
	public void folderSlash() throws Exception {
		waitForWorkspaceChanges(() -> {
			final IFolder folder = this.project.getFolder("myFolder");
			folder.create(true, true, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFolder/", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFolder", iResource.getFullPath().toString());
	}
	
	@Test
	public void folderSlashes() throws Exception {
		waitForWorkspaceChanges(() -> {
			final IFolder folder = this.project.getFolder("myFolder");
			folder.create(true, true, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFolder///", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFolder", iResource.getFullPath().toString());
	}
	
	@Test
	public void folderSlashesInbetween() throws Exception {
		waitForWorkspaceChanges(() -> {
			final IFolder folder = this.project.getFolder("myFolder");
			folder.create(true, true, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject///myFolder", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFolder", iResource.getFullPath().toString());
	}
	
	@Test
	public void fragment() throws Exception {
		waitForWorkspaceChanges(() -> {
			final IFile file = this.project.getFile("myFile.ext");
			file.create(new NullInputStream(0), true, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFile.ext", true).appendFragment("fragment");
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFile.ext", iResource.getFullPath().toString());
	}
	
	@Test
	public void query() throws Exception {
		waitForWorkspaceChanges(() -> {
			final IFile file = this.project.getFile("myFile.ext");
			file.create(new NullInputStream(0), true, null);
		});
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFile.ext", true).appendQuery("query");
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFile.ext", iResource.getFullPath().toString());
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
