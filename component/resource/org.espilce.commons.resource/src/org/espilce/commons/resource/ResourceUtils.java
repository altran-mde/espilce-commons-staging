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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.exception.UnconvertibleException;

/**
 * Utilities for {@linkplain IResource Eclipse IResource} and {@linkplain IPath
 * Eclipse IPath}.
 * 
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
 * @since 0.2
 */
public class ResourceUtils {
	public static @Nullable URL toJavaUrl(final @NonNull IPath iPath) {
		try {
			return new URL(iPath.toPortableString());
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public static @NonNull URL asJavaUrl(final @NonNull IPath iPath) throws UnconvertibleException {
		try {
			return new URL(iPath.toPortableString());
		} catch (MalformedURLException e) {
			throw new UnconvertibleException(iPath, IPath.class, URL.class, e);
		}
	}

	public static @Nullable URL toJavaUrl(final @NonNull IResource iResource) {
		try {
			return new URL(iResource.getFullPath().toPortableString());
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public static @NonNull URL asJavaUrl(final @NonNull IResource iResource) throws UnconvertibleException {
		try {
			return new URL(iResource.getFullPath().toPortableString());
		} catch (MalformedURLException e) {
			throw new UnconvertibleException(iResource, IResource.class, URL.class, e);
		}
	}

	public static @Nullable URI toJavaUri(final @NonNull IPath iPath) {
		try {
			return new URI(iPath.toPortableString());
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public static @NonNull URI asJavaUri(final @NonNull IPath iPath) throws UnconvertibleException {
		try {
			return new URI(iPath.toPortableString());
		} catch (URISyntaxException e) {
			throw new UnconvertibleException(iPath, IPath.class, URI.class, e);
		}
	}

	public static @Nullable URI toJavaUri(final @NonNull IResource iResource) {
		try {
			return new URI(iResource.getFullPath().toPortableString());
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public static @NonNull URI asJavaUri(final @NonNull IResource iResource) throws UnconvertibleException {
		try {
			return new URI(iResource.getFullPath().toPortableString());
		} catch (URISyntaxException e) {
			throw new UnconvertibleException(iResource, IResource.class, URI.class, e);
		}
	}
}
