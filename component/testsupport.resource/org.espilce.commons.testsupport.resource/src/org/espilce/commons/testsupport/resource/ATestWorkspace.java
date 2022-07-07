/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.testsupport.resource;

import static org.espilce.commons.resource.WorkspaceUtils.waitForWorkspaceChanges;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for JUnit tests requiring an IProject.
 * 
 * @since 0.1
 */
public abstract class ATestWorkspace {
	
	protected IProject project;
	
	@Before
	@BeforeEach
	public void createProject() throws Exception {
		waitForWorkspaceChanges(() -> {
			this.project = ResourcesPlugin.getWorkspace().getRoot().getProject("myProject");
			this.project.create(null);
			this.project.open(null);
		});
	}
	
	@After
	@AfterEach
	public void destroyProject() throws Exception {
		if (this.project != null) {
			waitForWorkspaceChanges(
					() -> {
						this.project.delete(true, true, null);
					}
			);
		}
	}
	
}
