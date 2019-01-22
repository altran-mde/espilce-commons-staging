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

import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.input.CharSequenceInputStream;
import org.apache.commons.io.input.NullInputStream;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class FileBuilder extends AResourceBuilder<IFile> {
	private InputStream source = new NullInputStream(0);
	private Charset charset = Charset.defaultCharset();

	public FileBuilder(final @Nullable AContainerBuilder<?> parentBuilder, final @NonNull String containerName) {
		super(parentBuilder, containerName);
	}

	public FileBuilder source(final @NonNull InputStream source) {
		this.source = source;
		return this;
	}

	public FileBuilder source(final @NonNull CharSequence source) {
		return source(new CharSequenceInputStream(source, charset));
	}

	public FileBuilder charset(final @NonNull Charset charset) {
		this.charset = charset;
		return this;
	}

	public FileBuilder charset(final @NonNull String charsetName) {
		return charset(Charset.forName(charsetName));
	}

	@Override
	protected IFile build(IContainer parent) throws CoreException {
		final IFile result = parent.getFile(getNameAsPath());
		result.create(source, updateFlags, monitor);
		return result;
	}
}
