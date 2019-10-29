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

import static org.espilce.commons.resource.WorkspaceUtils.waitForWorkspaceChanges;

import org.eclipse.core.resources.IProject;
import org.junit.jupiter.api.AfterEach;

/**
 * Base class for JUnit tests requiring an IProject.
 * 
 * @since 0.2
 */
public abstract class ATestWorkspace {
	
	protected IProject project;
	
	// FIXME: How to make compatible with both JUnit4 and JUnit5?
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
