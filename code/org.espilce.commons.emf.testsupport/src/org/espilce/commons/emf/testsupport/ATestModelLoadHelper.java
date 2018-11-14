package org.espilce.commons.emf.testsupport;

import java.io.IOException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.annotation.NonNull;
import org.espilce.commons.emf.UriUtils;

/**
 * Base class for tests that load models from outside.
 * 
 * @since 0.1
 *
 */
public abstract class ATestModelLoadHelper implements IResourceLoadHelper {
	private ResourceSet resourceSet;

	/**
	 * Loads the file pointed to by <code>modelRelativePath</code>.
	 *
	 * @param classInContext
	 *            Class that is located in the same classloader as the model file to
	 *            load.
	 * @param modelRelativePath
	 *            Path of the model file, relative to the classpath of the
	 *            classloader of <code>classInContext</code>.
	 * 
	 * @return Root element of the loaded model.
	 * @since 0.1
	 */
	public @NonNull EObject loadModel(final @NonNull Class<?> classInContext, final @NonNull String modelRelativePath) {
		return loadModelResource(classInContext, modelRelativePath).getContents().iterator().next();
	}

	/**
	 * Loads the file pointed to by <code>modelRelativePath</code>.
	 *
	 * @param classInContext
	 *            Class that is located in the same classloader as the model file to
	 *            load.
	 * @param modelRelativePath
	 *            Path of the model file, relative to the classpath of the
	 *            classloader of <code>classInContext</code>.
	 * 
	 * @return EMF resource loaded from <code>modelRelativePath</code>.
	 * @since 0.1
	 */
	public @NonNull Resource loadModelResource(final @NonNull Class<?> classInContext,
			final @NonNull String modelRelativePath) {
		final ResourceSet resourceSet = provideResourceSet();
		return this.loadModelResource(classInContext, modelRelativePath, resourceSet);
	}

	/**
	 * Provides the EMF ResourceSet to load models into.
	 *
	 * <p>
	 * Override to provide specialized ResourceSets.
	 * </p>
	 * 
	 * @return ResourceSet to load models into.
	 * @since 0.1
	 */
	public @NonNull ResourceSet provideResourceSet() {
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
		}

		return resourceSet;
	}

	protected @NonNull Resource loadModelResource(final @NonNull Class<?> classInContext,
			final @NonNull String modelRelativePath, final @NonNull ResourceSet resourceSet) {
		try {
			final String contentTypeId = getContentTypeId(classInContext, modelRelativePath);
			URI uri = toLocalmostUri(classInContext, modelRelativePath);

			final Resource modelResource;
			if (contentTypeId != null) {
				modelResource = resourceSet.createResource(uri, contentTypeId);
			} else {
				modelResource = resourceSet.createResource(uri);
			}

			modelResource.load(resourceSet.getLoadOptions());

			return modelResource;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected @NonNull URI toLocalmostUri(final @NonNull Class<?> classInContext,
			final @NonNull String resourceRelativePath) {
		final URL url = toLocalmostUrl(classInContext, resourceRelativePath);
		return UriUtils.asEmfUri(url);
	}
}