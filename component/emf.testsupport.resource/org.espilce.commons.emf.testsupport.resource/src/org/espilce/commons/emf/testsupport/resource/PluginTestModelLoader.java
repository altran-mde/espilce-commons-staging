package org.espilce.commons.emf.testsupport.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.internal.utils.FileUtil;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.emf.testsupport.ATestModelLoadHelper;
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
@SuppressWarnings("restriction")
public class PluginTestModelLoader extends ATestModelLoadHelper {
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
		final IPath path = findPathInContext(classInContext, resourceRelativePath);
		if (path != null && ResourcesPlugin.getWorkspace().getRoot().exists(path)) {
			return ResourceUtils.asJavaUrl(path);
		}

		URL url = findFileInPlugin(classInContext, resourceRelativePath);
		if (url != null) {
			return url;
		}

		throw new AssertionError("cannot find " + resourceRelativePath + " within context class " + classInContext);
	}

	@Override
	public String getContentTypeId(Class<?> classInContext, String resourceRelativePath) {
		IFile file = findIFileInContext(classInContext, resourceRelativePath);
		if (file != null) {
			final IContentType contentType = ContentTypeUtils.searchContentType(file);
			if (contentType != null) {
				return contentType.getId();
			}
		}

		return null;
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
	public List<URL> findMatchingResources(Class<?> classInContext, String parentRelativePath) {
		final IPath path = findPathInContext(classInContext, parentRelativePath);
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (path != null) {
			final IResource member = root.findMember(path);
			if (member instanceof IContainer) {
				final List<URL> result = new ArrayList<>();
				try {
					member.accept(r -> {
						final URL url = ResourceUtils.toJavaUrl(r);
						if (url != null) {
							result.add(url);
						}
						return true;
					});
				} catch (CoreException e) {
					// ignore
				}
				return result;
			}
		}

		final Bundle bundle = FrameworkUtil.getBundle(classInContext);
		if (bundle != null) {
			Enumeration<URL> entries = bundle.findEntries(parentRelativePath, "", true);
			if (entries != null) {
				return Collections.list(entries);
			}
		}

		return Collections.emptyList();
	}

	@Override
	public InputStream getContents(Class<?> classInContext, String resourceRelativePath) throws IOException {
		final IPath path = findPathInContext(classInContext, resourceRelativePath);
		if (path != null) {
			final Bundle bundle = FrameworkUtil.getBundle(classInContext);
			return FileLocator.openStream(bundle, path, true);
		}

		throw new IOException("Cannot find " + resourceRelativePath + " in context of class " + classInContext);
	}

	private @Nullable URL findFileInPlugin(final @NonNull Class<?> classInPlugin,
			final @NonNull String fileRelativePath) {
		final Bundle bundle = FrameworkUtil.getBundle(classInPlugin);
		if (bundle != null) {
			return bundle.getEntry(fileRelativePath);
		}

		return null;
	}

	private @Nullable IPath findPathInContext(final @NonNull Class<?> classInPlugin,
			final @NonNull String fileRelativePath) {
		final URL entry = findFileInPlugin(classInPlugin, fileRelativePath);
		try {
			if (entry != null) {
				return FileUtil.toPath(entry.toURI());
			}
		} catch (URISyntaxException e) {
			// do nothing
		}
		return null;
	}

	private @Nullable IFile findIFileInContext(final @NonNull Class<?> classInPlugin,
			final @NonNull String fileRelativePath) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(findPathInContext(classInPlugin, fileRelativePath));
	}
}
