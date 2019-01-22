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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.annotation.NonNull;

public abstract class AResourceBuilder<R extends IResource> {
	protected final AContainerBuilder<?> parentBuilder;
	protected final String resourceName;

	protected IProgressMonitor monitor = new NullProgressMonitor();
	protected int updateFlags = IResource.FORCE;

	protected AResourceBuilder(final AContainerBuilder<?> parentBuilder, final @NonNull String resourceName) {
		this.parentBuilder = parentBuilder;
		this.resourceName = resourceName;
	}

	public AResourceBuilder<R> monitor(IProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public AResourceBuilder<R> updateFlags(final int updateFlags) {
		this.updateFlags = updateFlags;
		return this;
	}

	public IProject build() throws CoreException {
		return parentBuilder.build();
	}

	public AContainerBuilder<?> end() {
		return parentBuilder;
	}

	protected abstract R build(final IContainer parent) throws CoreException;

	protected IPath getNameAsPath() {
		return Path.fromOSString(this.resourceName);
	}
}
