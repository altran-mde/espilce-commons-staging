package org.espilce.commons.emf.resource.uriresourceutils;

import static org.espilce.commons.emf.resource.WorkspaceUtils.waitForWorkspaceChanges;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Before;

/**
 * Base class for JUnit tests requiring an IProject.
 * 
 * @since 0.1
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
