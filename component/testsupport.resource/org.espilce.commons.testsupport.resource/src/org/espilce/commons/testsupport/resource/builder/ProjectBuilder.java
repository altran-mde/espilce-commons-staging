/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.testsupport.resource.builder;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.annotation.NonNull;

public class ProjectBuilder extends AContainerBuilder<IProject> {
	private IProjectDescription description = null;
	
	public ProjectBuilder(final @NonNull String projectName) {
		super(null, projectName);
	}
	
	public ProjectBuilder description(final @NonNull IProjectDescription description) {
		this.description = description;
		return this;
	}
	
	@Override
	public AContainerBuilder<?> end() {
		throw new UnsupportedOperationException("Cannot end a ProjectBuilder");
	}
	
	@Override
	public IProject build() throws CoreException {
		return build(null);
	}
	
	@Override
	protected IProject build(final IContainer parent) throws CoreException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(this.resourceName);
		project.create(this.description, this.updateFlags, this.monitor);
		project.open(this.monitor);
		buildChildren(project);
		return project;
	}
}
