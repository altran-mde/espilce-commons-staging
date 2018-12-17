/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.testsupport.resource;

import static org.espilce.commons.emf.resource.WorkspaceUtils.waitForWorkspaceChanges;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Before;

/**
 * Base class for JUnit tests requiring an IProject.
 * 
 * @since 0.2
 */
public abstract class ATestWorkspace {

	protected IProject project;

	@Before
	public void createProject() throws Exception {
		waitForWorkspaceChanges(() -> {
			project = ResourcesPlugin.getWorkspace().getRoot().getProject("myProject");
			project.create(null);
			project.open(null);
		});
	}

	@After
	public void destroyProject() throws Exception {
		if (project != null) {
			waitForWorkspaceChanges(() -> {
				project.delete(true, true, null);
			});
		}
	}

}
