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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.resource.UriResourceUtils;
import org.espilce.commons.emf.testsupport.resource.ATestWorkspace;
import org.espilce.commons.testsupport.resource.builder.ProjectBuilder;
import org.junit.jupiter.api.Test;

/**
 * Test cases for valid uses of {@link UriResourceUtils#toIResource(URI)}.
 * 
 */
public class TestToIResourcePositive extends ATestWorkspace {
	@Test
	public void file() throws Exception {
		waitForWorkspaceChanges(
				() -> {
					this.project = new ProjectBuilder("myProject")
							.createFile("myFile.ext")
							.build();
				}
		);
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFile.ext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFile.ext", iResource.getFullPath().toString());
	}
	
	@Test
	public void fileNested() throws Exception {
		waitForWorkspaceChanges(() -> {
			// @formatter:off
			this.project = new ProjectBuilder("myProject")
					.newFolder("folder")
						.newFolder("deep")
							.createFile("myFile.ext")
						.end()
					.end()
					.build();
			// @formatter:on
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
			// @formatter:off
			this.project = new ProjectBuilder("myProject")
					.newFolder("folder")
						.newFolder("deep")
							.createFile("myFile.ext")
						.end()
					.end()
					.build();
			// @formatter:on
		});
			
		final URI uri = URI.createPlatformResourceURI("////myProject///folder///deep/myFile.ext//", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/folder/deep/myFile.ext", iResource.getFullPath().toString());
	}
	
	@Test
	public void fileSlash() throws Exception {
		waitForWorkspaceChanges(
				() -> {
					this.project = new ProjectBuilder("myProject")
							.createFile("myFile.ext")
							.build();
				}
		);
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFile.ext/", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFile.ext", iResource.getFullPath().toString());
	}
	
	@Test
	public void fileDifferentCase() throws Exception {
		waitForWorkspaceChanges(
				() -> {
					this.project = new ProjectBuilder("myProject")
							.createFile("myFile.ext")
							.build();
				}
		);
		
		final URI uri = URI.createPlatformResourceURI("/myProject/MYfILE.ext", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertFalse(iResource.exists());
		assertEquals("/myProject/MYfILE.ext", iResource.getFullPath().toString());
	}
	
	@Test
	public void fileWorkspaceLinkExists() throws Exception {
		waitForWorkspaceChanges(() -> {
			this.project = new ProjectBuilder("myProject")
					.createFile("myFile.ext")
					.build();
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
	public void folderWorkspaceLinkExists() throws Exception {
		waitForWorkspaceChanges(() -> {
			this.project = new ProjectBuilder("myProject")
					.createFolder("myFolder")
					.build();
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
	public void folder() throws Exception {
		waitForWorkspaceChanges(
				() -> {
					this.project = new ProjectBuilder("myProject")
							.createFolder("myFolder")
							.build();
				}
		);
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFolder", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFolder", iResource.getFullPath().toString());
	}
	
	@Test
	public void folderSlash() throws Exception {
		waitForWorkspaceChanges(
				() -> {
					this.project = new ProjectBuilder("myProject")
							.createFolder("myFolder")
							.build();
				}
		);
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFolder/", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFolder", iResource.getFullPath().toString());
	}
	
	@Test
	public void folderSlashes() throws Exception {
		waitForWorkspaceChanges(
				() -> {
					this.project = new ProjectBuilder("myProject")
							.createFolder("myFolder")
							.build();
				}
		);
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFolder///", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFolder", iResource.getFullPath().toString());
	}
	
	@Test
	public void folderSlashesInbetween() throws Exception {
		waitForWorkspaceChanges(
				() -> {
					this.project = new ProjectBuilder("myProject")
							.createFolder("myFolder")
							.build();
				}
		);
		
		final URI uri = URI.createPlatformResourceURI("/myProject///myFolder", true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFolder);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFolder", iResource.getFullPath().toString());
	}
	
	@Test
	public void fragment() throws Exception {
		waitForWorkspaceChanges(
				() -> {
					this.project = new ProjectBuilder("myProject")
							.createFile("myFile.ext")
							.build();
				}
		);
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFile.ext", true).appendFragment("fragment");
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFile.ext", iResource.getFullPath().toString());
	}
	
	@Test
	public void query() throws Exception {
		waitForWorkspaceChanges(
				() -> {
					this.project = new ProjectBuilder("myProject")
							.createFile("myFile.ext")
							.build();
				}
		);
		
		final URI uri = URI.createPlatformResourceURI("/myProject/myFile.ext", true).appendQuery("query");
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertTrue(iResource instanceof IFile);
		assertTrue(iResource.exists());
		assertEquals("/myProject/myFile.ext", iResource.getFullPath().toString());
	}
	
}
