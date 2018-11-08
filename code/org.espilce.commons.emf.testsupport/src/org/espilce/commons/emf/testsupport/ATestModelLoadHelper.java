package org.espilce.commons.emf.testsupport;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public abstract class ATestModelLoadHelper implements ITestModelLoadHelper {
	private ResourceSet resourceSet;

	@Override
	public EObject loadModel(final Class<?> classInPlugin, final String modelRelativePath) {
		return loadModelResource(classInPlugin, modelRelativePath).getContents().iterator().next();
	}

	@Override
	public Resource loadModelResource(final Class<?> classInPlugin, final String modelRelativePath) {
		final ResourceSet resourceSet = provideResourceSet();
		return this.loadModelResource(classInPlugin, modelRelativePath, resourceSet);
	}

	protected Resource loadModelResource(final Class<?> classInContext, final String modelRelativePath,
			final ResourceSet resourceSet) {
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

			// EcoreUtil.resolveAll(resourceSet);
			return modelResource;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract String getContentTypeId(final Class<?> classInContext, final String resourceRelativePath);

	@Override
	public ResourceSet provideResourceSet() {
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
		}

		return resourceSet;
	}
}