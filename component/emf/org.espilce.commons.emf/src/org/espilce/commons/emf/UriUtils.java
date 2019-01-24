/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf;

import java.io.File;
import java.io.IOError;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;

/**
 * Utilities for converting {@linkplain org.eclipse.emf.common.util.URI EMF
 * URIs}, {@linkplain java.net.URI Java URIs}, {@linkplain java.net.URL Java
 * URLs}, {@linkplain java.io.File Java Files}, and
 * {@linkplain java.nio.file.Path Java Paths}.
 * 
 * @see org.espilce.commons.lang.ConversionUtils
 * @see org.espilce.commons.emf.resource.UriResourceUtils
 * @see org.espilce.commons.resource.ResourceUtils
 * 
 * @since 0.1
 *
 */
public class UriUtils extends ConversionUtils {
	
	/**
	 * Converts an EMF URI to a Java URI, if possible.
	 * 
	 * @param emfUri
	 *            EMF URI to convert.
	 * @return Java URI equivalent to <code>emfUri</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.1
	 */
	public static java.net.@Nullable URI toJavaUri(final @Nullable URI emfUri) {
		if (emfUri == null) {
			return null;
		}
		try {
			return new java.net.URI(emfUri.toString());
		} catch (final URISyntaxException e) {
			return null;
		}
	}
	
	/**
	 * Converts an EMF URI to a Java URI.
	 * 
	 * @param emfUri
	 *            EMF URI to convert.
	 * @return Java URI equivalent to <code>emfUri</code>.
	 * @throws UnconvertibleException
	 *             If conversion is unsuccessful.
	 * @since 0.1
	 */
	public static java.net.@NonNull URI asJavaUri(final @NonNull URI emfUri) throws UnconvertibleException {
		try {
			return new java.net.URI(emfUri.toString());
		} catch (final URISyntaxException e) {
			throw new UnconvertibleException(emfUri, URI.class, java.net.URI.class, e);
		}
	}
	
	/**
	 * Converts an EMF URI to a Java URL, if possible.
	 * 
	 * @param emfUri
	 *            EMF URI to convert.
	 * @return Java URL equivalent to <code>emfUri</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @Nullable URL toJavaUrl(final @Nullable URI emfUri) {
		if (emfUri == null) {
			return null;
		}
		try {
			return new URL(emfUri.toString());
		} catch (final MalformedURLException e) {
			return null;
		}
	}
	
	/**
	 * Converts an EMF URI to a Java URL.
	 * 
	 * @param emfUri
	 *            EMF URI to convert.
	 * @return Java URL equivalent to <code>emfUri</code>.
	 * @throws UnconvertibleException
	 *             If conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @NonNull URL asJavaUrl(final @NonNull URI emfUri) throws UnconvertibleException {
		try {
			return new URL(emfUri.toString());
		} catch (final MalformedURLException e) {
			throw new UnconvertibleException(emfUri, URI.class, URL.class, e);
		}
	}
	
	/**
	 * 
	 * @param emfUri
	 * @return
	 * @since 0.2
	 */
	public @Nullable Path toJavaPath(final @Nullable URI emfUri) {
		final java.net.URI javaUri = toJavaUri(emfUri);
		if (javaUri != null) {
			try {
				return Paths.get(javaUri);
			} catch (IllegalArgumentException | FileSystemNotFoundException e) {
				// fall-through
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param emfUri
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.2
	 */
	public @NonNull Path asJavaPath(final @NonNull URI emfUri) throws UnconvertibleException {
		try {
			final java.net.URI javaUri = new java.net.URI(emfUri.toString());
				return Paths.get(javaUri);
		} catch (IllegalArgumentException | FileSystemNotFoundException | URISyntaxException e) {
			throw new UnconvertibleException(emfUri, URI.class, Path.class, e);
		}
	}
	
	/**
	 * 
	 * @param emfUri
	 * @return
	 * @since 0.2
	 */
	public @Nullable File toJavaFile(final @Nullable URI emfUri) {
		final Path javaPath = toJavaPath(emfUri);
		if (javaPath != null) {
			return javaPath.toFile();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param emfUri
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.2
	 */
	public @NonNull File asJavaFile(final @NonNull URI emfUri) throws UnconvertibleException {
		try {
			final java.net.URI javaUri = new java.net.URI(emfUri.toString());
			return Paths.get(javaUri).toFile();
		} catch (IllegalArgumentException | FileSystemNotFoundException | URISyntaxException e) {
			throw new UnconvertibleException(emfUri, URI.class, Path.class, e);
		}
	}
	
	/**
	 * Converts a Java URL to an EMF URI, if possible.
	 * 
	 * @param javaUrl
	 *            Java URL to convert.
	 * @return EMF URI equivalent to <code>javaUrl</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @Nullable URI toEmfUri(final @Nullable URL javaUrl) {
		if (javaUrl == null) {
			return null;
		}
		return URI.createURI(javaUrl.toString());
	}
	
	/**
	 * Converts a Java URL to an EMF URI.
	 * 
	 * @param javaUrl
	 *            Java URL to convert.
	 * @return EMF URI equivalent to <code>javaUrl</code>.
	 * @throws UnconvertibleException
	 *             If conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @NonNull URI asEmfUri(final @NonNull URL javaUrl) throws UnconvertibleException {
		return URI.createURI(javaUrl.toString());
	}
	
	/**
	 * Converts a Java URI to an EMF URI, if possible.
	 * 
	 * @param javaUri
	 *            Java URI to convert.
	 * @return EMF URI equivalent to <code>javaUri</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @Nullable URI toEmfUri(final java.net.@Nullable URI javaUri) {
		if (javaUri == null) {
			return null;
		}
		return URI.createURI(javaUri.toString());
	}
	
	/**
	 * Converts a Java URI to an EMF URI.
	 * 
	 * @param javaUri
	 *            Java URI to convert.
	 * @return EMF URI equivalent to <code>javaUri</code>.
	 * @throws UnconvertibleException
	 *             If conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @NonNull URI asEmfUri(final java.net.@NonNull URI javaUri) throws UnconvertibleException {
		return URI.createURI(javaUri.toString());
	}
	
	/**
	 * Converts a Java File to an EMF URI, if possible.
	 * 
	 * @param file
	 *            The Java File to convert.
	 * @return EMF URI equivalent to <code>file</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @Nullable URI toEmfUri(final @Nullable File file) {
		if (file == null) {
			return null;
		}
		try {
			return URI.createFileURI(file.getAbsolutePath());
		} catch (final IllegalArgumentException e) {
			return null;
		}
	}
	
	/**
	 * Converts a Java File to an EMF URI.
	 * 
	 * @param file
	 *            The Java File to convert.
	 * @return EMF URI equivalent to <code>file</code>.
	 * @throws UnconvertibleException
	 *             If conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @NonNull URI asEmfUri(final @NonNull File file) throws UnconvertibleException {
		try {
			return URI.createFileURI(file.getAbsolutePath());
		} catch (final IllegalArgumentException e) {
			throw new UnconvertibleException(file, File.class, URI.class, e);
		}
	}
	
	/**
	 * Converts a Java Path to an EMF URI, if possible.
	 * 
	 * @param path
	 *            The Java Path to convert.
	 * @return EMF URI equivalent to <code>path</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @Nullable URI toEmfUri(final @Nullable Path path) {
		if (path == null) {
			return null;
		}
		try {
			return toEmfUri(path.toAbsolutePath().toUri());
		} catch (IOError | SecurityException e) {
			return null;
		}
	}
	
	/**
	 * Converts a Java Path to an EMF URI.
	 * 
	 * @param path
	 *            The Java Path to convert.
	 * @return EMF URI equivalent to <code>path</code>.
	 * @throws UnconvertibleException
	 *             If conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @NonNull URI asEmfUri(final @NonNull Path path) throws UnconvertibleException {
		try {
			return asEmfUri(path.toAbsolutePath().toUri());
		} catch (IOError | SecurityException e) {
			throw new UnconvertibleException(path, Path.class, URI.class, e);
		} catch (final UnconvertibleException e) {
			throw new UnconvertibleException(path, Path.class, URI.class, e.getCause());
		}
	}
}
