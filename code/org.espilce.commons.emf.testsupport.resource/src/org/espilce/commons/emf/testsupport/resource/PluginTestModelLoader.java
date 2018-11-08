package org.espilce.commons.emf.testsupport.resource;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.internal.utils.FileUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.testsupport.ATestModelLoadHelper;
import org.espilce.commons.resource.ContentTypeUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

@SuppressWarnings("restriction")
public class PluginTestModelLoader extends ATestModelLoadHelper {
	/**
	 * Finds {@code pathName} in the workspace or plug-ins.
	 *
	 * <p>
	 * First tries to find {@code pathName} in the workspace. If no corresponding
	 * file is found in the workspace, tries to find {@code pathName} in plug-ins
	 * that are reachable (i. e. there is a (in-)direct dependency of the caller to
	 * the plug-in). If neither is found, raises an {@link AssertionError}.
	 * </p>
	 *
	 * @param pathName
	 *            {@code /project-name/path} as described in
	 *            {@link org.eclipse.emf.common.util.URI#createPlatformResourceURI(String, boolean)
	 *            URI.createPlatformResourceURI()}.
	 * @return The Ecore URI of {@code pathName} in either the workspace or
	 *         plug-ins.
	 * @throws AssertionError
	 *             If {@code pathName} can be found in neither the workspace or the
	 *             reachable plug-ins.
	 */
	@Override
	public URI toLocalmostUri(Class<?> classInContext, String resourceRelativePath) {
		final IPath path = findPathInContext(classInContext, resourceRelativePath);
		if (ResourcesPlugin.getWorkspace().getRoot().exists(path)) {
			return URI.createPlatformResourceURI(resourceRelativePath, false);
		}

		URL url = findFileInPlugin(classInContext, resourceRelativePath);
		assertNotNull("Cannot find " + resourceRelativePath, url);
		final URI pluginUri = URI.createPlatformPluginURI(resourceRelativePath, false);
		return pluginUri;
	}

	protected URL findFileInPlugin(final Class<?> classInPlugin, final String fileRelativePath) {
		final Bundle bundle = FrameworkUtil.getBundle(classInPlugin);
		return bundle.getEntry(fileRelativePath);
	}

	protected IPath findPathInContext(final Class<?> classInPlugin, final String fileRelativePath) {
		final Bundle bundle = FrameworkUtil.getBundle(classInPlugin);
		final URL entry = bundle.getEntry(fileRelativePath);
		try {
			return FileUtil.toPath(entry.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	protected IFile findIFileInContext(final Class<?> classInPlugin, final String fileRelativePath) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(findPathInContext(classInPlugin, fileRelativePath));
	}

	@Override
	protected String getContentTypeId(Class<?> classInContext, String resourceRelativePath) {
		IFile file = findIFileInContext(classInContext, resourceRelativePath);

		return ContentTypeUtils.findContentType(file).getId();
	}

	@Override
	public List<URL> findMatchingResources(Class<?> classInContext, String parentRelativePath) {
		final Bundle bundle = FrameworkUtil.getBundle(classInContext);
		Enumeration<URL> entries = bundle.findEntries(parentRelativePath, "", true);
		return Collections.list(entries);
	}

	@Override
	public InputStream getContents(Class<?> classInContext, String resourceRelativePath) throws IOException {
		final Bundle bundle = FrameworkUtil.getBundle(classInContext);
		return FileLocator.openStream(bundle, findPathInContext(classInContext, resourceRelativePath), true);
	}
}
