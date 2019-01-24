/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.loadhelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
public class FilesystemClassloaderLoadHelper implements ILoadHelper {
	
	@Override
	public String getContentTypeId(final Class<?> classInContext, final String resourceRelativePath) {
		return null;
	}
	
	@Override
	public @Nullable String getContentTypeId(@NonNull final Class<?> classInContext, @NonNull final URL url) {
		return null;
	}
	
	@Override
	public InputStream getContents(final Class<?> classInContext, final String resourceRelativePath)
			throws IOException {
		final File file = toFile(classInContext, resourceRelativePath);
		if (file != null) {
			return new BufferedInputStream(new FileInputStream(file));
		}
		
		final InputStream result = classInContext.getClassLoader().getResourceAsStream(resourceRelativePath);
		if (result != null) {
			return result;
		}
		
		throw new IllegalArgumentException(
				"Cannot find " + resourceRelativePath + " in context of class " + classInContext
		);
	}
	
	@Override
	public @NonNull InputStream getContents(@NonNull final Class<?> classInContext, @NonNull final URL url)
			throws IllegalArgumentException, IOException {
		if ("file".equals(url.getProtocol())) {
			File file;
			try {
				file = new File(url.toURI());
				return new BufferedInputStream(new FileInputStream(file));
			} catch (final URISyntaxException e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			return url.openStream();
		}
	}
	
	@Override
	public List<URL> findMatchingResources(final Class<?> classInContext, final String parentRelativePath)
			throws IllegalArgumentException {
		final File file = toFile(classInContext, parentRelativePath);
		
		if (file != null) {
			if (file.isFile()) {
				return Collections.emptyList();
			}
			final Path basePath = Paths.get(file.getAbsolutePath());
			try (Stream<Path> fileWalker = Files.walk(basePath)) {
				final List<URL> result = fileWalker
						.filter(p -> !basePath.equals(p))
						.map(this::convertPathToUrl)
						.filter(Objects::nonNull).collect(Collectors.toList());
				return result;
			} catch (final IOException e) {
				// ignore
			}
		}
		
		
		final URL url = classInContext.getClassLoader().getResource(parentRelativePath);
		if (url != null) {
			Enumeration<URL> resources;
			try {
				resources = classInContext.getClassLoader().getResources(parentRelativePath);
				return Collections.list(resources);
			} catch (final IOException e) {
				return Collections.emptyList();
			}
		}
		
		throw new IllegalArgumentException(
				"Cannot find " + parentRelativePath + " in context of class " + classInContext
		);
	}
	
	@Override
	public URL toLocalmostUrl(final Class<?> classInContext, final String resourceRelativePath) {
		final File file = toFile(classInContext, resourceRelativePath);
		
		try {
			if (file != null) {
				return file.toURI().toURL();
			}
			final URL url = classInContext.getClassLoader().getResource(resourceRelativePath);
			if (url != null) {
				return url;
			}
			throw new IllegalArgumentException(
					"Cannot find " + resourceRelativePath + " in context of class " + classInContext
			);
		} catch (final MalformedURLException e) {
			throw new IllegalArgumentException(
					"Cannot find " + resourceRelativePath + " in context of class " + classInContext, e
			);
		}
	}
	
	private @Nullable URL convertPathToUrl(final @NonNull Path path) {
		try {
			return path.toUri().toURL();
		} catch (final MalformedURLException e) {
			return null;
		}
	}
	
	private @Nullable File toFile(final @NonNull Class<?> classInContext, final @NonNull String resourceRelativePath) {
		final File file = new File(resourceRelativePath);
		
		if (file.canRead()) {
			return file.getAbsoluteFile();
		}
		
		return null;
	}
}
