package org.espilce.commons.emf.testsupport;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

public interface ITestModelLoadHelper {

	/**
	 * Loads the file pointed to by {@code modelRelativePath}.
	 *
	 * <p>
	 * This method tries to resolve all proxies in the contained model.
	 * </p>
	 *
	 * @param classInContext
	 *            A class that is located in the same classloader as the model file
	 *            to load.
	 * @param modelRelativePath
	 *            Path of the model file, relative to the classpath of the
	 *            classloader containing the given class.
	 *
	 * @return The the root element of the loaded model.
	 */
	EObject loadModel(Class<?> classInContext, String modelRelativePath);

	/**
	 * Loads the file pointed to by {@code modelRelativePath}.
	 *
	 * <p>
	 * This method tries to resolve all proxies in the contained model.
	 * </p>
	 *
	 * @param classInContext
	 *            A class that is located in the same classloader as the model file
	 *            to load.
	 * @param modelRelativePath
	 *            Path of the model file, relative to the classpath of the
	 *            classloader containing the given class.
	 *
	 * @return The Ecore resource loaded from {@code modelRelativePath}.
	 */
	Resource loadModelResource(Class<?> classInContext, String modelRelativePath);

	/**
	 * Finds the file pointed to by {@code fileRelativePath}.
	 *
	 * @param classInContext
	 *            A class that is located in the same classloader as the model file
	 *            to load.
	 * @param fileRelativePath
	 *            Path of the file, relative to the classpath of the classloader
	 *            containing the given class.
	 *
	 * @return The Java File corresponding to {@code fileRelativePath}.
	 */
	List<URL> findMatchingResources(final Class<?> classInContext, final String parentRelativePath);

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
	URI toLocalmostUri(Class<?> classInContext, String resourceRelativePath);

	public InputStream getContents(final Class<?> classInContext, final String resourceRelativePath) throws IOException;

	/**
	 * Provides the Ecore ResourceSet to load models into.
	 *
	 * <p>
	 * Override to provide specialized ResourceSets.
	 * </p>
	 */
	ResourceSet provideResourceSet();
}