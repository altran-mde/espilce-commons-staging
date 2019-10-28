/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.resource.test.loadhelper.workspace;

import static org.espilce.commons.resource.WorkspaceUtils.waitForWorkspaceChanges;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestToLocalmostUrl;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.espilce.commons.testsupport.resource.builder.ProjectBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestToLocalmostUrl extends ATestToLocalmostUrl {
	protected IProject project;
	
	@BeforeEach
	public void createProject() throws Exception {
		this.project = ResourcesPlugin.getWorkspace().getRoot().getProject("some");
		try {
			this.project.delete(true, true, null);
		} catch (final CoreException e) {
			
		}
		
		waitForWorkspaceChanges(() -> {
			// @formatter:off
			this.project = new ProjectBuilder("some")
					.newFolder("dir")
						.newFile("file.txt")
							.source("file.txt in workspace")
						.end()
					.end()
					.build();
			// @formatter:on
		});
	}
	
	@SuppressWarnings("null")
	@AfterEach
	public void destroyProject() throws Exception {
		if (this.project != null) {
			waitForWorkspaceChanges(() -> {
						// if (this.project != null && this.project.exists()) {
					this.project.delete(true, true, null);
						// } ;
			});
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
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	@Disabled
	public void existingDirStartSlash() throws Exception {
		super.existingDirStartSlash();
	}
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	@Disabled
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
	}
	
}
