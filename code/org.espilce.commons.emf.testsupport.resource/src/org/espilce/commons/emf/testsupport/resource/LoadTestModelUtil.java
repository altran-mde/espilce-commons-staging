package org.espilce.commons.emf.testsupport.resource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.espilce.commons.emf.resource.UriResourceUtils;
import org.espilce.commons.resource.ContentTypeUtils;
import org.junit.Assert;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class LoadTestModelUtil {
	private static LoadTestModelUtil instance;

	protected LoadTestModelUtil() {
	}

	public static LoadTestModelUtil getInstance() {
		if (instance == null) {
			instance = new LoadTestModelUtil();
		}

		return instance;
	}

	/**
	 * Loads the file pointed to by {@code modelRelativePath}.
	 *
	 * <p>
	 * This method tries to resolve all proxies in the contained model.
	 * </p>
	 *
	 * <p>
	 * <b>Caution!</b> This method assumes the plug-in containing the model file to be unzipped!
	 * </p>
	 *
	 * @param classInPlugin
	 *            A class that is located in the same plug-in as the model file to load.
	 * @param modelRelativePath
	 *            Path of the model file, relative to the plug-in containing the given class.
	 *
	 * @return The the root element of the loaded model.
	 */
	public EObject loadModel(final Class<?> classInPlugin, final String modelRelativePath) {
		return loadModelResource(classInPlugin, modelRelativePath).getContents().iterator().next();
	}

	/**
	 * Loads the file pointed to by {@code modelRelativePath}.
	 *
	 * <p>
	 * This method tries to resolve all proxies in the contained model.
	 * </p>
	 *
	 * <p>
	 * <b>Caution!</b> This method assumes the plug-in containing the model file to be unzipped!
	 * </p>
	 *
	 * @param classInPlugin
	 *            A class that is located in the same plug-in as the model file to load.
	 * @param modelRelativePath
	 *            Path of the model file, relative to the plug-in containing the given class.
	 *
	 * @return The Ecore resource loaded from {@code modelRelativePath}.
	 */
	public Resource loadModelResource(final Class<?> classInPlugin, final String modelRelativePath) {
		final ResourceSet resourceSet = createResourceSet();
		return this.loadModelResource(classInPlugin, modelRelativePath, resourceSet);
	}

	/**
	 * Creates the Ecore ResourceSet to load models into.
	 *
	 * <p>
	 * Override to provide specialized ResourceSets.
	 * </p>
	 */
	protected ResourceSet createResourceSet() {
		return new ResourceSetImpl();
	}

	protected Resource loadModelResource(final Class<?> classInPlugin, final String modelRelativePath,
			final ResourceSet resourceSet) {
		try {
			final File modelFile = findFileInPlugin(classInPlugin, modelRelativePath);
			final URI modelUri = UriResourceUtils.toEcoreUri(modelFile);

			final Resource modelResource;
			final IContentType contentType = ContentTypeUtils.findContentType(modelFile);
			if (contentType != null) {
				modelResource = resourceSet.createResource(modelUri, contentType.getId());
			} else {
				modelResource = resourceSet.createResource(modelUri);
			}

			modelResource.load(resourceSet.getLoadOptions());

			EcoreUtil.resolveAll(resourceSet);
			return modelResource;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Finds the file pointed to by {@code fileRelativePath}.
	 *
	 * <p>
	 * <b>Caution!</b> This method assumes the plug-in containing the file to be unzipped!
	 * </p>
	 *
	 * @param classInPlugin
	 *            A class that is located in the same plug-in as the model file to load.
	 * @param fileRelativePath
	 *            Path of the file, relative to the plug-in containing the given class.
	 *
	 * @return The Java File corresponding to {@code fileRelativePath}.
	 */
	public File findFileInPlugin(final Class<?> classInPlugin, final String fileRelativePath) throws IOException {
		final Bundle bundle = FrameworkUtil.getBundle(classInPlugin);

		final URL entry = FileLocator.toFileURL(bundle.getEntry(fileRelativePath));
		Assert.assertNotNull("model file does not exist: " + fileRelativePath, entry);

		final File modelFile = new File(entry.getFile());
		Assert.assertTrue(modelFile.canRead());
		return modelFile;
	}

	/**
	 * Finds the Ecore URI pointed to by {@code fileRelativePath}.
	 *
	 * <p>
	 * <b>Caution!</b> This method assumes the plug-in containing the file to be unzipped!
	 * </p>
	 *
	 * @param classInPlugin
	 *            A class that is located in the same plug-in as the model file to load.
	 * @param fileRelativePath
	 *            Path of the file, relative to the plug-in containing the given class.
	 *
	 * @return The Ecore URI corresponding to {@code fileRelativePath}.
	 */
	public URI toUri(final Class<?> classInPlugin, final String fileRelativePath) throws IOException {
		final File modelFile = findFileInPlugin(classInPlugin, fileRelativePath);
		return UriResourceUtils.toEcoreUri(modelFile);
	}

	/**
	 * Finds {@code pathName} in the workspace or plug-ins.
	 *
	 * <p>
	 * First tries to find {@code pathName} in the workspace. If no corresponding file is found in the workspace, tries
	 * to find {@code pathName} in plug-ins that are reachable (i. e. there is a (in-)direct dependency of the caller to
	 * the plug-in).
	 * If neither is found, raises an {@link AssertionError}.
	 * </p>
	 *
	 * @param pathName
	 *            {@code /project-name/path} as described in
	 *            {@link org.eclipse.emf.common.util.URI#createPlatformResourceURI(String, boolean)
	 *            URI.createPlatformResourceURI()}.
	 * @return The Ecore URI of {@code pathName} in either the workspace or plug-ins.
	 * @throws AssertionError
	 *             If {@code pathName} can be found in neither the workspace or the reachable plug-ins.
	 */
	public URI toResourceOrPluginUri(final String pathName) {
		final IPath path = Path.fromPortableString(pathName);
		if (ResourcesPlugin.getWorkspace().getRoot().exists(path)) {
			return URI.createPlatformResourceURI(pathName, false);
		}

		try {
			final URI pluginUri = URI.createPlatformPluginURI(pathName, false);
			assertNotNull("Cannot find " + pathName, FileLocator.find(new URL(pluginUri.toString())));
			return pluginUri;
		} catch (final MalformedURLException e) {
			assertTrue("Cannot find " + pathName, false);
			return null;
		}

	}
}
