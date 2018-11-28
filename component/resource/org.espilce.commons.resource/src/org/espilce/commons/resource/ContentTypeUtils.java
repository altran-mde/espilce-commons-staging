/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.resource;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.exception.UnmappableException;

/**
 * Utilities for handling {@linkplain IContentType Content Types}.
 * <p>
 * Each conversion method exists in two variants:
 * </p>
 * 
 * <ul>
 * <li><code><b>to</b>TargetType(SourceType)</code> returns {@code null} if the
 * conversion is unsuccessful.</li>
 * <li><code><b>as</b>TargetType(SourceType)</code> throws a
 * {@link UnconvertibleException} if the conversion is unsuccessful.</li>
 * </ul>
 *
 * <p>
 * Each lookup method exists in two variants:
 * </p>
 * 
 * <ul>
 * <li><code><b>search</b>Target(Source)</code> returns {@code null} if the
 * lookup is unsuccessful.</li>
 * <li><code><b>find</b>Target(Source)</code> throws a
 * {@link UnmappableException} if the lookup is unsuccessful.</li>
 * </ul>
 * 
 * @since 0.1
 */
public class ContentTypeUtils {
	/**
	 * Looks up the Content Type of an Eclipse IFile, if possible.
	 * 
	 * @param iFile
	 *            Eclipse IFile to look up.
	 * @return Content Type of <code>iFile</code>, or {@code null} if no Content
	 *         Type can be found.
	 * @since 0.2
	 */
	public static @Nullable IContentType searchContentType(final @NonNull IFile iFile) {
		try {
			final IContentDescription contentDescription = iFile.getContentDescription();
			if (contentDescription != null) {
				return contentDescription.getContentType();
			} else {
				return null;
			}
		} catch (final CoreException e) {
			return null;
		}
	}

	/**
	 * Looks up the Content Type of an Eclipse IFile.
	 * 
	 * @param iFile
	 *            Eclipse IFile to look up.
	 * @return Content Type of <code>iFile</code>.
	 * @throws UnmappableException
	 *             If no Content Type can be found.
	 * @since 0.2
	 */
	public static @NonNull IContentType findContentType(final @NonNull IFile iFile) throws UnmappableException {
		try {
			final IContentDescription contentDescription = iFile.getContentDescription();
			if (contentDescription != null) {
				return contentDescription.getContentType();
			} else {
				throw new UnmappableException(iFile, IFile.class, IContentType.class);
			}
		} catch (final CoreException e) {
			throw new UnmappableException(iFile, IFile.class, IContentType.class, e);
		}
	}

	/**
	 * Looks up the Content Type of a Java File, if possible.
	 * 
	 * @param file
	 *            Java File to look up.
	 * @return Content Type of <code>file</code>, or {@code null} if no Content Type
	 *         can be found.
	 * @since 0.2
	 */
	public static @Nullable IContentType searchContentType(final @NonNull File file) {
		return searchContentType(file.getName());
	}

	/**
	 * Looks up the Content Type of a Java File.
	 * 
	 * @param file
	 *            Java File to look up.
	 * @return Content Type of <code>file</code>.
	 * @throws UnmappableException
	 *             If no Content Type can be found.
	 * @since 0.2
	 */
	public static @NonNull IContentType findContentType(final @NonNull File file) throws UnmappableException {
		return findContentType(file.getName());
	}

	/**
	 * Looks up the Content Type for a file name.
	 * 
	 * @param fileName
	 *            File name to look up.
	 * @return Content Type of <code>fileName</code>, or {@code null} if no Content
	 *         Type can be found.
	 * @since 0.2
	 */
	public static @Nullable IContentType searchContentType(final @NonNull String fileName) {
		return lookupContentType(fileName).orElse(null);
	}

	/**
	 * Looks up the Content Type for a file name.
	 * 
	 * @param fileName
	 *            File name to look up.
	 * @return Content Type of <code>fileName</code>.
	 * @throws UnmappableException
	 *             If no Content Type can be found.
	 * @since 0.2
	 */
	public static @NonNull IContentType findContentType(final @NonNull String fileName) throws UnmappableException {
		return lookupContentType(fileName)
				.orElseThrow(() -> new UnmappableException(fileName, String.class, IContentType.class));
	}

	private static Optional<IContentType> lookupContentType(final String fileName) {
		return Arrays.asList(Platform.getContentTypeManager().getAllContentTypes()).stream()
				.filter(ct -> ct.isAssociatedWith(fileName)).findAny();
	}

	/**
	 * Looks up all file extensions registered for a Content Type Id.
	 * 
	 * @param contentTypeIdentifier
	 *            Content Type Id to look up.
	 * @return All file extensions registered for
	 *         <code>contentTypeIdentifier</code>. If
	 *         <code>contentTypeIdentifier</code> cannot be resolved, returns an
	 *         empty collection.
	 * @since 0.1
	 */
	public static @NonNull Collection<@NonNull String> searchFileExtensions(
			final @NonNull String contentTypeIdentifier) {
		final IContentType contentType = toContentType(contentTypeIdentifier);
		return contentType == null ? Collections.emptyList() : lookupFileExtensions(contentType);
	}

	/**
	 * Looks up all file extensions registered for a Content Type Id.
	 * 
	 * @param contentTypeIdentifier
	 *            Content Type Id to look up.
	 * @return All file extensions registered for
	 *         <code>contentTypeIdentifier</code>.
	 * @throws UnmappableException
	 *             If <code>contentTypeIdentifier</code> cannot be resolved.
	 * @since 0.2
	 */
	public static @NonNull Collection<@NonNull String> findFileExtensions(final @NonNull String contentTypeIdentifier)
			throws UnmappableException {
		return lookupFileExtensions(asContentType(contentTypeIdentifier));
	}

	/**
	 * Looks up all file extensions registered for a Content Type.
	 * 
	 * @param contentType
	 *            Content Type to look up.
	 * @return All file extensions registered for <code>contentType</code>.
	 * @since 0.2
	 */
	public static @NonNull Collection<@NonNull String> lookupFileExtensions(final @NonNull IContentType contentType) {
		return Arrays.asList(contentType.getFileSpecs(IContentType.FILE_EXTENSION_SPEC));
	}

	/**
	 * Looks up the Content Type of Content Type Id, if possible.
	 * 
	 * @param contentTypeIdentifier
	 *            Content Type Id to look up.
	 * @return Content Type of <code>contentTypeIdentifier</code>, or {@code null}
	 *         if no Content Type can be found.
	 * @since 0.1
	 */
	public static @Nullable IContentType toContentType(final @NonNull String contentTypeIdentifier) {
		return Platform.getContentTypeManager().getContentType(contentTypeIdentifier);
	}

	/**
	 * Looks up the Content Type of Content Type Id.
	 * 
	 * @param contentTypeIdentifier
	 *            Content Type Id to look up.
	 * @return Content Type of <code>contentTypeIdentifier</code>.
	 * @throws UnmappableException
	 *             If no Content Type can be found.
	 * @since 0.2
	 */
	public static @NonNull IContentType asContentType(final @NonNull String contentTypeIdentifier)
			throws UnmappableException {
		final IContentType result = Platform.getContentTypeManager().getContentType(contentTypeIdentifier);

		if (result != null) {
			return result;
		}

		throw new UnmappableException(contentTypeIdentifier, String.class, IContentType.class);
	}
}
