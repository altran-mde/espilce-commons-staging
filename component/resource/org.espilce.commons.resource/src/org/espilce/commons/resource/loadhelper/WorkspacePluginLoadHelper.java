/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.resource.loadhelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.resource.ContentTypeUtils;
import org.espilce.commons.resource.ResourceUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Finds resources in the workspace or plug-ins.
 * 
 * <p>
 * First tries to find <code>resourceRelativePath</code> in the workspace. If no
 * corresponding file is found in the workspace, tries to find
 * <code>resourceRelativePath</code> in plug-ins that are reachable (i. e. there
 * is a (in-)direct dependency of the caller to the plug-in). If neither is
 * found, raises an {@link AssertionError}.
 * </p>
 *
 * @since 0.1
 */
public class WorkspacePluginLoadHelper implements ILoadHelper {
	/**
	 * Finds <code>resourceRelativePath</code> in the workspace or plug-ins.
	 *
	 * @param classInContext
	 *            Class that is located in the same classloader as the resource to
	 *            load.
	 * @param resourceRelativePath
	 *            Path of the resource, relative to the classpath of the classloader
	 *            of <code>classInContext</code>.
	 * @return <code>resourceRelativePath</code> from either the workspace or
	 *         plug-ins.
	 * @throws AssertionError
	 *             If <code>resourceRelativePath</code> can be found in neither the
	 *             workspace or the reachable plug-ins.
	 */
	@Override
	public URL toLocalmostUrl(Class<?> classInContext, String resourceRelativePath) {
		final IPath path = findPathInWorkspace(classInContext, resourceRelativePath);
		if (path != null) {
			return ResourceUtils.asJavaUrl(ResourcesPlugin.getWorkspace().getRoot().findMember(path));
		}

		URL url = findEntryInPlugin(classInContext, resourceRelativePath);
		if (url != null) {
			return url;
		}

		throw new IllegalArgumentException(
				"Cannot find " + resourceRelativePath + " in context of class " + classInContext);
	}

	@Override
	public String getContentTypeId(Class<?> classInContext, String resourceRelativePath) {
		IContentType contentType = null;
		IPath path = findPathInWorkspace(classInContext, resourceRelativePath);
		if (path != null) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			if (file != null) {
				contentType = ContentTypeUtils.searchContentType(file);
			}
		}

		URL url = findEntryInPlugin(classInContext, resourceRelativePath);
		if (url != null) {
			contentType = ContentTypeUtils.searchContentType(url);
		}

		if (contentType != null) {
			return contentType.getId();
		}

		throw new IllegalArgumentException(
				"Cannot find " + resourceRelativePath + " in context of class " + classInContext);
	}

	/**
	 * Finds <code>resourceRelativePath</code> from the <i>closest</i> available
	 * source.
	 * 
	 * First tries to find <code>parentRelativePath</code> in the workspace. If no
	 * corresponding file is found in the workspace, tries to find
	 * <code>parentRelativePath</code> in plug-ins that are reachable (i. e. there
	 * is a (in-)direct dependency of the caller to the plug-in). If neither is
	 * found, returns an empty list.
	 * </p>
	 */
	@Override
	public List<URL> findMatchingResources(Class<?> classInContext, String parentRelativePath)
			throws IllegalArgumentException {
		final IPath path = findPathInWorkspace(classInContext, parentRelativePath);
		if (path != null) {
			final IResource member = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
			if (member instanceof IContainer) {
				final List<URL> result = new ArrayList<>();
				try {
					member.accept((IResourceVisitor) r -> {
						if (!member.equals(r)) {
							final URL url = ResourceUtils.toJavaUrl(r);
							if (url != null) {
								if (r instanceof IContainer) {
									try {
										result.add(new URL(url, url.toString() + "/"));
									} catch (MalformedURLException e) {
										// ignore
									}
								} else {
									result.add(url);
								}
							}
						}
						return true;
					});
				} catch (CoreException e) {
					// ignore
				}
				return result;
			} else {
				return Collections.emptyList();
			}
		}

		final Bundle bundle = FrameworkUtil.getBundle(classInContext);
		if (bundle != null) {
			Enumeration<URL> entries = bundle.findEntries(parentRelativePath, "*", true);
			if (entries != null) {
				return Collections.list(entries);
			} else if (bundle.getEntry(parentRelativePath) != null) {
				return Collections.emptyList();
			}
		}

		throw new IllegalArgumentException(
				"Cannot find " + parentRelativePath + " in context of class " + classInContext);
	}

	@Override
	public InputStream getContents(Class<?> classInContext, String resourceRelativePath) throws IOException {
		IPath workspacePath = findPathInWorkspace(classInContext, resourceRelativePath);
		if (workspacePath != null) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(workspacePath);
			if (file != null && file.isAccessible()) {
				try {
					return file.getContents();
				} catch (CoreException e) {
					// ignore
				}
			}
		}

		final URL entry = findEntryInPlugin(classInContext, resourceRelativePath);
		if (entry != null) {
			return entry.openStream();
		}

		throw new IllegalArgumentException(
				"Cannot find " + resourceRelativePath + " in context of class " + classInContext);
	}

	private @Nullable URL findEntryInPlugin(final @NonNull Class<?> classInPlugin,
			final @NonNull String fileRelativePath) {
		final Bundle bundle = FrameworkUtil.getBundle(classInPlugin);
		if (bundle != null) {
			return bundle.getEntry(fileRelativePath);
		}

		return null;
	}

	private @Nullable IPath findPathInWorkspace(final @NonNull Class<?> classInPlugin,
			final @NonNull String fileRelativePath) {
		IPath path = Path.fromPortableString(fileRelativePath);
		if (ResourcesPlugin.getWorkspace().getRoot().exists(path)) {
			return path;
		}

		return null;
	}
}
