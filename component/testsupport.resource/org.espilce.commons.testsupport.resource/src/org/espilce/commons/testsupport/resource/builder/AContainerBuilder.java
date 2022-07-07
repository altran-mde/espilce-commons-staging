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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public abstract class AContainerBuilder<C extends IContainer> extends AResourceBuilder<C> {
	protected final List<AResourceBuilder<?>> childBuilders = new ArrayList<>();
	
	protected AContainerBuilder(
			final @Nullable AContainerBuilder<?> parentBuilder,
			final @NonNull String containerName
	) {
		super(parentBuilder, containerName);
	}
	
	public AContainerBuilder<C> addChildBuilder(final @NonNull AResourceBuilder<?> childBuilder) {
		this.childBuilders.add(childBuilder);
		return this;
	}
	
	public FolderBuilder newFolder(final @NonNull String folderName) {
		final FolderBuilder result = new FolderBuilder(this, folderName);
		addChildBuilder(result);
		return result;
	}
	
	public AContainerBuilder<C> createFolder(final @NonNull String folderName) {
		newFolder(folderName);
		return this;
	}
	
	public FileBuilder newFile(final @NonNull String fileName) {
		final FileBuilder result = new FileBuilder(this, fileName);
		addChildBuilder(result);
		return result;
	}
	
	public AContainerBuilder<C> createFile(final @NonNull String fileName) {
		newFile(fileName);
		return this;
	}
	
	protected void buildChildren(@NonNull final IContainer self) throws CoreException {
		for (final AResourceBuilder<?> childBuilder : this.childBuilders) {
			childBuilder.build(self);
		}
	}
}
