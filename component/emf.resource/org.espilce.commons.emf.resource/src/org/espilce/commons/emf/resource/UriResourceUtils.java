/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.resource;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.emf.UriUtils;
import org.espilce.commons.exception.UnconvertibleException;

/**
 * Utilities for handling {@linkplain org.eclipse.emf.common.util.URI EMF URIs}
 * in an Eclipse {@linkplain org.eclipse.core.resources.ResourcesPlugin
 * Resources} environment.
 *
 * @since 0.1
 *
 */
public class UriResourceUtils extends UriUtils {

	/**
	 * Returns the equivalent {@linkplain org.eclipse.core.resources.IResource
	 * Eclipse IResource} for an {@linkplain org.eclipse.emf.common.util.URI EMF
	 * URI}, if available.
	 *
	 * <p>
	 * {@code uri} can be represented as IResource if {@code uri} is an
	 * {@linkplain URI#isPlatformResource() platform resource} (i.e. {@code uri}
	 * starts with {@code platform:/resource/}). Otherwise, this method returns
	 * {@code null}.
	 * </p>
	 *
	 * <p>
	 * This method ignores any {@linkplain URI#fragment() fragment} or
	 * {@linkplain URI#query() query} of {@code uri}.
	 * </p>
	 *
	 * <p>
	 * If the resulting IResource exists, this method returns the existing kind of
	 * IResource ({@linkplain org.eclipse.core.resources.IWorkspaceRoot
	 * IWorkspaceRoot}, {@linkplain org.eclipse.core.resources.IProject IProject},
	 * {@linkplain org.eclipse.core.resources.IFolder IFolder}, or
	 * {@linkplain org.eclipse.core.resources.IFile IFile}).
	 * </p>
	 *
	 * <p>
	 * If the resulting IResource does not exist, this method returns an IFile
	 * pointing to the place equivalent to {@code uri}.
	 * </p>
	 *
	 * <p>
	 * This method handles excess slashes (behind the platform resource identifiers)
	 * gracefully (i.e. ignores the slashes).<br/>
	 * Example: An URI of
	 * {@code platform:/resource/////MyProject///folder///deep/myFile.ext//} leads
	 * to an IFile for path {@code /MyProject/folder/deep/myFile.ext}.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method treats {@code uri} as case-sensitive (on <i>all</i>
	 * platforms, including Windows). Therefore, if the workspace contained a file
	 * at {@code /MyProject/myFolder/myFile.ext} and we passed the URI
	 * {@code platform:/resource/MyProject/myFolder/mYfILE.ext} to this method, the
	 * result is an IFile for path {@code /MyProject/myFolder/mYfILE.ext}.
	 * {@link IResource#exists() result.exists()} will return {@code false}.
	 * </p>
	 *
	 * @param emfUri
	 *            The EMF URI to return as Eclipse IResource.
	 * @return {@code uri} as Eclipse IResource, if available; {@code null}
	 *         otherwise.
	 *
	 * @throws IllegalArgumentException
	 *             If {@code uri} is seriously ill-formatted.
	 *
	 * @since 0.1
	 */
	public static @Nullable IResource toIResource(final @NonNull URI emfUri) {
		if (emfUri.isPlatformResource()) {
			final String platformString = emfUri.toPlatformString(true);
			final IPath path = Path.fromOSString(platformString);
			final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			if (workspaceRoot.exists(path)) {
				return workspaceRoot.findMember(path);
			} else {
				return workspaceRoot.getFile(path);
			}
		}

		return null;
	}

	/**
	 * Returns the equivalent {@linkplain org.eclipse.core.resources.IResource
	 * Eclipse IResource} for an {@linkplain org.eclipse.emf.common.util.URI EMF
	 * URI}.
	 *
	 * <p>
	 * {@code uri} can be represented as IResource if {@code uri} is an
	 * {@linkplain URI#isPlatformResource() platform resource} (i.e. {@code uri}
	 * starts with {@code platform:/resource/}). Otherwise, this method throws an
	 * {@link UnconvertibleException}.
	 * </p>
	 *
	 * <p>
	 * This method ignores any {@linkplain URI#fragment() fragment} or
	 * {@linkplain URI#query() query} of {@code uri}.
	 * </p>
	 *
	 * <p>
	 * If the resulting IResource exists, this method returns the existing kind of
	 * IResource ({@linkplain org.eclipse.core.resources.IWorkspaceRoot
	 * IWorkspaceRoot}, {@linkplain org.eclipse.core.resources.IProject IProject},
	 * {@linkplain org.eclipse.core.resources.IFolder IFolder}, or
	 * {@linkplain org.eclipse.core.resources.IFile IFile}).
	 * </p>
	 *
	 * <p>
	 * If the resulting IResource does not exist, this method returns an IFile
	 * pointing to the place equivalent to {@code uri}.
	 * </p>
	 *
	 * <p>
	 * This method handles excess slashes (behind the platform resource identifiers)
	 * gracefully (i.e. ignores the slashes).<br/>
	 * Example: An URI of
	 * {@code platform:/resource/////MyProject///folder///deep/myFile.ext//} leads
	 * to an IFile for path {@code /MyProject/folder/deep/myFile.ext}.
	 * </p>
	 *
	 * <p>
	 * <b>Note:</b> This method treats {@code uri} as case-sensitive (on <i>all</i>
	 * platforms, including Windows). Therefore, if the workspace contained a file
	 * at {@code /MyProject/myFolder/myFile.ext} and we passed the URI
	 * {@code platform:/resource/MyProject/myFolder/mYfILE.ext} to this method, the
	 * result is an IFile for path {@code /MyProject/myFolder/mYfILE.ext}.
	 * {@link IResource#exists() result.exists()} will return {@code false}.
	 * </p>
	 *
	 * @param uri
	 *            The EMF URI to return as Eclipse IResource.
	 * @return {@code uri} as Eclipse IResource.
	 *
	 * @throws UnconvertibleException
	 *             If {@code uri} cannot be converted into an Eclipse IResource.
	 * @throws IllegalArgumentException
	 *             If {@code uri} is seriously ill-formatted.
	 *
	 * @since 0.1
	 */
	public static @NonNull IResource asIResource(final @NonNull URI uri) throws UnconvertibleException {
		final IResource result = toIResource(uri);

		if (result != null) {
			return result;
		}

		throw new UnconvertibleException(uri, URI.class, IResource.class);
	}

	/**
	 * Converts an Eclipse IResource to an EMF URI, if possible.
	 * 
	 * @param iResource
	 *            Eclipse IResource to convert.
	 * @return EMF URI equivalent to <code>iResource</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.2
	 */
	public static @Nullable URI toEmfUri(final @NonNull IResource iResource) {
		try {
			return URI.createPlatformResourceURI(iResource.getFullPath().toPortableString(), false);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Converts an Eclipse IResource to an EMF URI.
	 * 
	 * @param iResource
	 *            Eclipse IResource to convert.
	 * @return EMF URI equivalent to <code>iResource</code>.
	 * @throws UnconvertibleException
	 *             If conversion is unsuccessful.
	 * @since 0.2
	 */
	public static @NonNull URI asEmfUri(final @NonNull IResource iResource) throws UnconvertibleException {
		try {
			return URI.createPlatformResourceURI(iResource.getFullPath().toPortableString(), false);
		} catch (IllegalArgumentException e) {
			throw new UnconvertibleException(iResource, IResource.class, URI.class, e);
		}
	}

	/**
	 * Converts an Eclipse IPath to an EMF URI, if possible.
	 * 
	 * @param iPath
	 *            Eclipse IPath to convert.
	 * @return EMF URI equivalent to <code>iPath</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.2
	 */
	public static @Nullable URI toEmfUri(final @NonNull IPath iPath) {
		try {
			if (ResourcesPlugin.getWorkspace().getRoot().getFullPath().isPrefixOf(iPath)) {
				return URI.createPlatformResourceURI(iPath.makeAbsolute().toPortableString(), false);
			} else if (Path.fromPortableString("resources").isPrefixOf(iPath)) {
				return URI.createPlatformPluginURI(iPath.toPortableString(), false);
			} else {
				return URI.createFileURI(iPath.toPortableString());
			}
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Converts an Eclipse IPath to an EMF URI.
	 * 
	 * @param iPath
	 *            Eclipse IPath to convert.
	 * @return EMF URI equivalent to <code>iPath</code>.
	 * @throws UnconvertibleException
	 *             If conversion is unsuccessful.
	 * @since 0.2
	 */
	public static @NonNull URI asEmfUri(final @NonNull IPath iPath) throws UnconvertibleException {
		try {
			if (ResourcesPlugin.getWorkspace().getRoot().getFullPath().isPrefixOf(iPath)) {
				return URI.createPlatformResourceURI(iPath.makeAbsolute().toPortableString(), false);
			} else if (Path.fromPortableString("resources").isPrefixOf(iPath)) {
				return URI.createPlatformPluginURI(iPath.toPortableString(), false);
			} else {
				return URI.createFileURI(iPath.toPortableString());
			}
		} catch (IllegalArgumentException e) {
			throw new UnconvertibleException(iPath, IPath.class, URI.class, e);
		}
	}
}
