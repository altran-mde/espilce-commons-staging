/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.resource.test.contenttypeutils;

import static org.espilce.commons.resource.WorkspaceUtils.waitForWorkspaceChanges;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.core.resources.IProject;
import org.espilce.commons.exception.UnmappableException;
import org.espilce.commons.resource.ContentTypeUtils;
import org.espilce.commons.testsupport.resource.builder.ProjectBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSearchContentType {
	IProject project;
	
	@Before
	public void createProjects() throws Exception {
		waitForWorkspaceChanges(() -> {
			// @formatter:off
			this.project = new ProjectBuilder("myProject")
					.createFile("fileWithoutExtension")
					.createFile("textFile.txt")
					.build();
			// @formatter:on
		});
	}
	
	@Test
	public void findTextFile() {
		assertEquals(
				"org.eclipse.core.runtime.text",
				ContentTypeUtils.findContentType(this.project.getFile("textFile.txt")).getId()
		);
	}
	
	@Test
	public void searchTextFile() {
		assertEquals(
				"org.eclipse.core.runtime.text",
				ContentTypeUtils.searchContentType(this.project.getFile("textFile.txt")).getId()
		);
	}
	
	@Test(expected = UnmappableException.class)
	public void findFileWithoutExtension() {
		ContentTypeUtils.findContentType(this.project.getFile("fileWithoutExtension"));
	}
	
	@Test
	public void searchFileWithoutExtension() {
		assertNull(ContentTypeUtils.searchContentType(this.project.getFile("fileWithoutExtension")));
	}
	
	@After
	public void destroyProjects() throws Exception {
		destroyProject(this.project);
	}
	
	private static void destroyProject(final IProject project) throws Exception {
		if (project != null) {
			waitForWorkspaceChanges(() -> project.delete(true, true, null));
		}
	}
}
