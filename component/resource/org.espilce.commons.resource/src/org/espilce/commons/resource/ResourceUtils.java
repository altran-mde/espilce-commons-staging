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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;

/**
 * Utilities for converting {@linkplain org.eclipse.core.resources.IResource
 * Eclipse IResources}, {@linkplain org.eclipse.core.runtime.IPath Eclipse
 * IPaths}, {@linkplain java.net.URI Java URIs}, {@linkplain java.net.URL Java
 * URLs}, {@linkplain java.io.File Java Files}, and
 * {@linkplain java.nio.file.Path Java Paths} in an Eclipse
 * {@linkplain org.eclipse.core.resources.ResourcesPlugin Resources}
 * environment.
 * 
 * @see org.espilce.commons.lang.ConversionUtils
 * @see org.espilce.commons.emf.UriUtils
 * @see org.espilce.commons.emf.resource.UriResourceUtils
 * 
 * @since 0.2
 */
public class ResourceUtils extends ConversionUtils {
	/**
	 * 
	 * @param iPath
	 * @return
	 * 
	 * @since 0.2
	 */
	public static @Nullable URL toJavaUrl(final @Nullable IPath iPath) {
		if (iPath == null) {
			return null;
		}
		try {
			return new URL(iPath.toPortableString());
		} catch (final MalformedURLException e) {
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
		} catch (final MalformedURLException e) {
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
	public static @Nullable URL toJavaUrl(final @Nullable IResource iResource) {
		if (iResource == null) {
			return null;
		}
		try {
			return iResource.getLocationURI().toURL();
		} catch (final MalformedURLException e) {
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
		} catch (final MalformedURLException e) {
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
	public static @Nullable URI toJavaUri(final @Nullable IPath iPath) {
		if (iPath == null) {
			return null;
		}
		try {
			return new URI(iPath.toPortableString());
		} catch (final URISyntaxException e) {
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
		} catch (final URISyntaxException e) {
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
	public static @Nullable URI toJavaUri(final @Nullable IResource iResource) {
		if (iResource == null) {
			return null;
		}
		try {
			return new URI(iResource.getFullPath().toPortableString());
		} catch (final URISyntaxException e) {
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
		} catch (final URISyntaxException e) {
			throw new UnconvertibleException(iResource, IResource.class, URI.class, e);
		}
	}
	
	/**
	 * 
	 * @param iPath
	 * @return
	 * @since 0.6
	 */
	public static java.nio.file.@Nullable Path toJavaPath(final @Nullable IPath iPath) {
		if (iPath == null) {
			return null;
		}
		return Paths.get(iPath.segment(0), iPath.removeFirstSegments(1).segments());
	}
	
	/**
	 * 
	 * @param iPath
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.6
	 */
	public static java.nio.file.@NonNull Path asJavaPath(final @NonNull IPath iPath) throws UnconvertibleException {
		try {
			return Paths.get(iPath.segment(0), iPath.removeFirstSegments(1).segments());
		} catch (final InvalidPathException e) {
			throw new UnconvertibleException(iPath, IPath.class, java.nio.file.Path.class, e);
		}
	}
	
	/**
	 * 
	 * @param iResource
	 * @return
	 * @since 0.6
	 */
	public static java.nio.file.@Nullable Path toJavaPath(final @Nullable IResource iResource) {
		if (iResource == null) {
			return null;
		}
		return toJavaPath(iResource.getLocation());
	}
	
	/**
	 * 
	 * @param iResource
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.6
	 */
	public static java.nio.file.@NonNull Path asJavaPath(final @NonNull IResource iResource)
			throws UnconvertibleException {
		try {
			final IPath iPath = iResource.getLocation();
			if (iPath != null) {
				return Paths.get(iPath.segment(0), iPath.removeFirstSegments(1).segments());
			}
		} catch (final InvalidPathException e) {
			throw new UnconvertibleException(iResource, IResource.class, java.nio.file.Path.class, e);
		}
		
		throw new UnconvertibleException(iResource, IResource.class, java.nio.file.Path.class);
	}
	
	/**
	 * 
	 * @param iPath
	 * @return
	 * @since 0.6
	 */
	public static File toJavaFile(final @Nullable IPath iPath) {
		if (iPath == null) {
			return null;
		}
		return iPath.toFile();
	}
	
	/**
	 * 
	 * @param iPath
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.6
	 */
	public static @NonNull File asJavaFile(final @NonNull IPath iPath) throws UnconvertibleException {
		return iPath.toFile();
	}
	
	/**
	 * 
	 * @param iResource
	 * @return
	 * @since 0.6
	 */
	public static File toJavaFile(final @Nullable IResource iResource) {
		if (iResource == null) {
			return null;
		}
		return toJavaFile(iResource.getLocation());
	}
	
	/**
	 * 
	 * @param iResource
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.6
	 */
	public static @NonNull File asJavaFile(final @NonNull IResource iResource) throws UnconvertibleException {
		try {
			final IPath iPath = iResource.getLocation();
			if (iPath != null) {
				return iPath.toFile();
			}
		} catch (final InvalidPathException e) {
			throw new UnconvertibleException(iResource, IResource.class, File.class, e);
		}
		
		throw new UnconvertibleException(iResource, IResource.class, File.class);
	}
	
	/**
	 * 
	 * @param javaUrl
	 * @return
	 * 
	 * @since 0.5
	 */
	public static @Nullable IPath toIPath(final @Nullable URL javaUrl) {
		if (javaUrl == null) {
			return null;
		}
		try {
			return Path.fromPortableString(javaUrl.toURI().toString());
		} catch (final URISyntaxException e) {
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
	public static @NonNull IPath asIPath(final @NonNull URL javaUrl) throws UnconvertibleException {
		try {
			return Path.fromPortableString(javaUrl.toURI().toString());
		} catch (final URISyntaxException e) {
			throw new UnconvertibleException(javaUrl, URL.class, IPath.class, e);
		}
	}
	
	/**
	 * 
	 * @param javaUri
	 * @return
	 * 
	 * @since 0.5
	 */
	public static @Nullable IPath toIPath(final @Nullable URI javaUri) {
		if (javaUri == null) {
			return null;
		}
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
	public static @NonNull IPath asIPath(final @NonNull URI javaUri) throws UnconvertibleException {
		return Path.fromPortableString(javaUri.toString());
	}
	
	/**
	 * 
	 * @param javaPath
	 * @return
	 * @since 0.6
	 */
	public static @Nullable IPath toIPath(final java.nio.file.@Nullable Path javaPath) {
		if (javaPath == null) {
			return null;
		}
		return Path.fromOSString(javaPath.toString());
	}
	
	/**
	 * 
	 * @param javaPath
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.6
	 */
	public static @NonNull IPath asIPath(final java.nio.file.@NonNull Path javaPath) throws UnconvertibleException {
		return Path.fromOSString(javaPath.toString());
	}
	
	/**
	 * 
	 * @param javaFile
	 * @return
	 * @since 0.6
	 */
	public static @Nullable IPath toIPath(final @Nullable File javaFile) {
		if (javaFile == null) {
			return null;
		}
		return Path.fromOSString(javaFile.getPath());
	}
	
	/**
	 * 
	 * @param javaFile
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.6
	 */
	public static @NonNull IPath asIPath(final @NonNull File javaFile) throws UnconvertibleException {
		return Path.fromOSString(javaFile.getPath());
	}
	
	/**
	 * 
	 * @param javaUrl
	 * @return
	 * 
	 * @since 0.5
	 */
	public static @Nullable IResource toIResource(final @Nullable URL javaUrl) {
		if (javaUrl == null) {
			return null;
		}
		try {
			return ResourcesPlugin.getWorkspace().getRoot().findMember(javaUrl.toURI().toString());
		} catch (final URISyntaxException e) {
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
		} catch (final URISyntaxException e) {
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
	public static @Nullable IResource toIResource(final @Nullable URI javaUri) {
		if (javaUri == null) {
			return null;
		}
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
	
	/**
	 * 
	 * @param javaPath
	 * @return
	 * @since 0.6
	 */
	public static @Nullable IResource toIResource(final java.nio.file.@Nullable Path javaPath) {
		if (javaPath == null) {
			return null;
		}
		final IResource result = ResourcesPlugin.getWorkspace().getRoot().findMember(javaPath.toString());
		if (result != null) {
			return result;
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param javaPath
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.6
	 */
	public static @NonNull IResource asIResource(final java.nio.file.@NonNull Path javaPath)
			throws UnconvertibleException {
		final IResource result = ResourcesPlugin.getWorkspace().getRoot().findMember(javaPath.toString());
		if (result != null) {
			return result;
		}
		
		throw new UnconvertibleException(javaPath, java.nio.file.Path.class, IResource.class);
	}
	
	/**
	 * 
	 * @param javaFile
	 * @return
	 * @since 0.6
	 */
	public static @Nullable IResource toIResource(final @Nullable File javaFile) {
		if (javaFile == null) {
			return null;
		}
		final IResource result = ResourcesPlugin.getWorkspace().getRoot().findMember(javaFile.toString());
		if (result != null) {
			return result;
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param javaFile
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.6
	 */
	public static @NonNull IResource asIResource(final @NonNull File javaFile) throws UnconvertibleException {
		final IResource result = ResourcesPlugin.getWorkspace().getRoot().findMember(javaFile.toString());
		if (result != null) {
			return result;
		}
		
		throw new UnconvertibleException(javaFile, File.class, IResource.class);
	}
	
}
