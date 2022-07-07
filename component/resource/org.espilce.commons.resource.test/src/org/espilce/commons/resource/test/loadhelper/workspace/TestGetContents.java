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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.eclipse.core.resources.IProject;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestGetContents;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.espilce.commons.testsupport.resource.builder.ProjectBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestGetContents extends ATestGetContents {
	protected IProject project;
	
	@BeforeEach
	public void createProject() throws Exception {
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
	@Test
	@Disabled
	public void nonExistingDir() throws Exception {
		super.nonExistingDir();
	}
	
	@Override
	@Test
	@Disabled
	public void rootFileStartSlash() throws Exception {
		super.rootFileStartSlash();
	}
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new WorkspacePluginLoadHelper();
	}
	
	@Override
	protected void assertContents(final String relativePath, final String contents) {
		assertEquals(contents, "file.txt in workspace", relativePath);
	}
	
}
