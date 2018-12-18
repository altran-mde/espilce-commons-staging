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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Supports finding and reading resources (a.k.a. files) in different contexts,
 * e.g. file system, classloader, Eclipse workspace, OSGi bundles, &hellip;.
 *
 * @since 0.1
 */
public interface ILoadHelper {
	
	/**
	 * Finds all files directly and indirectly below the folder pointed to by
	 * <code>parentRelativePath</code>.
	 *
	 * <p>
	 * Returns the <i>closest</i> <code>parentRelativePath</code> contents,
	 * according to {@linkplain #toLocalmostUrl(Class, String)}.
	 * </p>
	 *
	 * @param classInContext
	 *            Class that is located in the same classloader as the resource
	 *            to load.
	 * @param parentRelativePath
	 *            Path of the folder to list, relative to the classpath of the
	 *            classloader of <code>classInContext</code>.
	 *
	 * @return List of Java URLs below <code>parentRelativePath</code>.
	 * @throws IllegalArgumentException
	 *             If <code>resourceRelativePath</code> cannot be found.
	 * @since 0.1
	 */
	public @NonNull List<@NonNull URL> findMatchingResources(final @NonNull Class<?> classInContext,
			final @NonNull String parentRelativePath) throws IllegalArgumentException;
	
	/**
	 * Finds <code>resourceRelativePath</code> from the <i>closest</i> available
	 * source.
	 *
	 * <p>
	 * <i>Close</i> may carry different meaning for each implementer.
	 * </p>
	 *
	 * @param classInContext
	 *            Class that is located in the same classloader as the resource
	 *            to load.
	 * @param resourceRelativePath
	 *            Path of the resource, relative to the classpath of the
	 *            classloader of <code>classInContext</code>.
	 * @return <code>resourceRelativePath</code> from the <i>closest</i>
	 *         available source.
	 * @throws IllegalArgumentException
	 *             If <code>resourceRelativePath</code> cannot be found.
	 * @since 0.1
	 */
	public @NonNull URL toLocalmostUrl(final @NonNull Class<?> classInContext,
			final @NonNull String resourceRelativePath) throws IllegalArgumentException;
	
	/**
	 * Returns the contents of <code>resourceRelativePath</code> according to
	 * {@linkplain #toLocalmostUrl(Class, String)}.
	 *
	 * @param classInContext
	 *            Class that is located in the same classloader as the resource
	 *            to load.
	 * @param resourceRelativePath
	 *            Path of the resource, relative to the classpath of the
	 *            classloader of <code>classInContext</code>.
	 * @return Contents of <code>resourceRelativePath</code> from the
	 *         <i>closest</i> available source.
	 * @throws IllegalArgumentException
	 *             If <code>resourceRelativePath</code> cannot be found.
	 * @throws IOException
	 *             Forwarded from accessing contents of
	 *             <code>resourceRelativePath</code>.
	 * @since 0.1
	 */
	public @NonNull InputStream getContents(final @NonNull Class<?> classInContext,
			final @NonNull String resourceRelativePath) throws IllegalArgumentException, IOException;
	
	/**
	 * Looks up the Content Type of <code>resourceRelativePath</code>, if
	 * possible.
	 *
	 * @param classInContext
	 *            Class that is located in the same classloader as the resource
	 *            to load.
	 * @param resourceRelativePath
	 *            Path of the resource, relative to the classpath of the
	 *            classloader of <code>classInContext</code>.
	 * @return Content Type of <code>resourceRelativePath</code>, or
	 *         {@code null} if no Content Type can be found.
	 * @throws IllegalArgumentException
	 *             If <code>resourceRelativePath</code> cannot be found.
	 * @since 0.1
	 */
	public @Nullable String getContentTypeId(final @NonNull Class<?> classInContext,
			final @NonNull String resourceRelativePath);
}
