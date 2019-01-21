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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
	/**
	 * 
	 * @param iPath
	 * @return
	 * 
	 * @since 0.2
	 */
	public static @Nullable URL toJavaUrl(final @NonNull IPath iPath) {
		try {
			return new URL(iPath.toPortableString());
		} catch (MalformedURLException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param iPath
	 * @return
	 * @throws UnconvertibleException
	 * 
	 * @since 0.2
	 */
	public static @NonNull URL asJavaUrl(final @NonNull IPath iPath) throws UnconvertibleException {
		try {
			return new URL(iPath.toPortableString());
		} catch (MalformedURLException e) {
			throw new UnconvertibleException(iPath, IPath.class, URL.class, e);
		}
	}

	/**
	 * 
	 * @param iResource
	 * @return
	 * 
	 * @since 0.2
	 */
	public static @Nullable URL toJavaUrl(final @NonNull IResource iResource) {
		try {
			return iResource.getLocationURI().toURL();
		} catch (MalformedURLException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param iResource
	 * @return
	 * @throws UnconvertibleException
	 * 
	 * @since 0.2
	 */
	public static @NonNull URL asJavaUrl(final @NonNull IResource iResource) throws UnconvertibleException {
		try {
			return iResource.getLocationURI().toURL();
		} catch (MalformedURLException e) {
			throw new UnconvertibleException(iResource, IResource.class, URL.class, e);
		}
	}

	/**
	 * 
	 * @param iPath
	 * @return
	 * 
	 * @since 0.2
	 */
	public static @Nullable URI toJavaUri(final @NonNull IPath iPath) {
		try {
			return new URI(iPath.toPortableString());
		} catch (URISyntaxException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param iPath
	 * @return
	 * @throws UnconvertibleException
	 * 
	 * @since 0.2
	 */
	public static @NonNull URI asJavaUri(final @NonNull IPath iPath) throws UnconvertibleException {
		try {
			return new URI(iPath.toPortableString());
		} catch (URISyntaxException e) {
			throw new UnconvertibleException(iPath, IPath.class, URI.class, e);
		}
	}

	/**
	 * 
	 * @param iResource
	 * @return
	 * 
	 * @since 0.2
	 */
	public static @Nullable URI toJavaUri(final @NonNull IResource iResource) {
		try {
			return new URI(iResource.getFullPath().toPortableString());
		} catch (URISyntaxException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param iResource
	 * @return
	 * @throws UnconvertibleException
	 * 
	 * @since 0.2
	 */
	public static @NonNull URI asJavaUri(final @NonNull IResource iResource) throws UnconvertibleException {
		try {
			return new URI(iResource.getFullPath().toPortableString());
		} catch (URISyntaxException e) {
			throw new UnconvertibleException(iResource, IResource.class, URI.class, e);
		}
	}

	/**
	 * 
	 * @param javaUrl
	 * @return
	 * 
	 * @since 0.5
	 */
	public static @Nullable IPath toIPath(URL javaUrl) {
		try {
			return Path.fromPortableString(javaUrl.toURI().toString());
		} catch (URISyntaxException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param javaUrl
	 * @return
	 * @throws UnconvertibleException
	 * 
	 * @since 0.5
	 */
	public static @NonNull IPath asIPath(URL javaUrl) throws UnconvertibleException {
		try {
			return Path.fromPortableString(javaUrl.toURI().toString());
		} catch (URISyntaxException e) {
			throw new UnconvertibleException(javaUrl, URL.class, IPath.class, e);
		}
	}

	/**
	 * 
	 * @param javaUrl
	 * @return
	 * 
	 * @since 0.5
	 */
	public static @Nullable IResource toIResource(final @NonNull URL javaUrl) {
		try {
			return ResourcesPlugin.getWorkspace().getRoot().findMember(javaUrl.toURI().toString());
		} catch (URISyntaxException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param javaUrl
	 * @return
	 * @throws UnconvertibleException
	 * 
	 * @since 0.5
	 */
	public static @NonNull IResource asIResource(final @NonNull URL javaUrl) throws UnconvertibleException {
		try {
			final IResource result = ResourcesPlugin.getWorkspace().getRoot().findMember(javaUrl.toURI().toString());
			if (result != null) {
				return result;
			}
			throw new UnconvertibleException(javaUrl, URL.class, IResource.class);
		} catch (URISyntaxException e) {
			throw new UnconvertibleException(javaUrl, URL.class, IResource.class, e);
		}
	}

	/**
	 * 
	 * @param javaUri
	 * @return
	 * 
	 * @since 0.5
	 */
	public static @Nullable IPath toIPath(URI javaUri) {
		return Path.fromPortableString(javaUri.toString());
	}

	/**
	 * 
	 * @param javaUri
	 * @return
	 * @throws UnconvertibleException
	 * 
	 * @since 0.5
	 */
	public static @NonNull IPath asIPath(URI javaUri) throws UnconvertibleException {
		return Path.fromPortableString(javaUri.toString());
	}

	/**
	 * 
	 * @param javaUri
	 * @return
	 * 
	 * @since 0.5
	 */
	public static @Nullable IResource toIResource(final @NonNull URI javaUri) {
		return ResourcesPlugin.getWorkspace().getRoot().findMember(javaUri.toString());
	}

	/**
	 * 
	 * @param javaUri
	 * @return
	 * @throws UnconvertibleException
	 * 
	 * @since 0.5
	 */
	public static @NonNull IResource asIResource(final @NonNull URI javaUri) throws UnconvertibleException {
		final IResource result = ResourcesPlugin.getWorkspace().getRoot().findMember(javaUri.toString());
		if (result != null) {
			return result;
		}
		throw new UnconvertibleException(javaUri, URL.class, IResource.class);
	}
}
