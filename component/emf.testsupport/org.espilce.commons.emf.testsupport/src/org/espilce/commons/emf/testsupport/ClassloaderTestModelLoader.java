/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.testsupport;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Loads resources either from file system or classloader.
 * 
 * @since 0.1
 *
 */
public class ClassloaderTestModelLoader extends ATestModelLoadHelper {

	@Override
	public String getContentTypeId(Class<?> classInContext, String resourceRelativePath) {
		return null;
	}

	@Override
	public InputStream getContents(Class<?> classInContext, String resourceRelativePath) throws IOException {
		File file = toFile(classInContext, resourceRelativePath);
		if (file != null) {
			return new BufferedInputStream(new FileInputStream(file));
		}

		return classInContext.getClassLoader().getResourceAsStream(resourceRelativePath);
	}

	@Override
	public List<URL> findMatchingResources(Class<?> classInContext, String parentRelativePath) {
		File file = toFile(classInContext, parentRelativePath);

		if (file != null) {
			Path basePath = Paths.get(file.getAbsolutePath());
			try (Stream<Path> fileWalker = Files.walk(basePath)) {
				final List<URL> result = fileWalker.filter(Files::isRegularFile).map(this::convertPathToUrl)
						.filter(Objects::nonNull).collect(Collectors.toList());
				return result;
			} catch (IOException e) {
				// ignore
			}
		}

		Enumeration<URL> resources;
		try {
			resources = classInContext.getClassLoader().getResources(parentRelativePath);
			return Collections.list(resources);
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public URL toLocalmostUrl(Class<?> classInContext, String resourceRelativePath) {
		File file = toFile(classInContext, resourceRelativePath);

		try {
			if (file != null) {
				return file.toURI().toURL();
			}
			return new URL(resourceRelativePath);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private @Nullable URL convertPathToUrl(final @NonNull Path path) {
		try {
			return path.toUri().toURL();
		} catch (MalformedURLException e) {
			return null;
		}
	}

	private @Nullable File toFile(final @NonNull Class<?> classInContext, final @NonNull String resourceRelativePath) {
		File file = new File(resourceRelativePath);

		if (file.canRead()) {
			return file.getAbsoluteFile();
		}

		return null;
	}
}
