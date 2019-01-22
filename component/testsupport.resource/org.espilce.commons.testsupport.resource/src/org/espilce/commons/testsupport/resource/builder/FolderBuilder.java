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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.annotation.NonNull;

public class FolderBuilder extends AContainerBuilder<IFolder> {
	private boolean local = true;

	protected FolderBuilder(final @NonNull AContainerBuilder<?> parentBuilder, final @NonNull String containerName) {
		super(parentBuilder, containerName);
	}

	@Override
	protected IFolder build(IContainer parent) throws CoreException {
		final IFolder folder = parent.getFolder(getNameAsPath());
		folder.create(updateFlags, local, monitor);
		buildChildren(folder);
		return folder;
	}
}
