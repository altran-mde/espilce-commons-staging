package org.espilce.commons.emf.testsupport.resource;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.espilce.commons.emf.testsupport.ModelEqualityUtil;

/**
 * Convenience base class for test classes that need to load models.
 */
@SuppressWarnings("all")
public class AModelLoader {
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
	public EObject loadModel(final String modelRelativePath) {
		return LoadTestModelUtil.getInstance().loadModel(this.getClass(), modelRelativePath);
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
	public Resource loadModelResource(final String modelRelativePath) {
		return LoadTestModelUtil.getInstance().loadModelResource(this.getClass(), modelRelativePath);
	}

	/**
	 * Asserts that two Ecore Resources are equal. That is, they have {@link EcoreUtil#equals(List, List) equal}
	 * {@link Resource#getContents() contents}. If they are not equal, an {@link AssertionError} is thrown with a
	 * human-readable rendering of the differences.
	 *
	 * @param expected
	 *            expected Ecore Resource
	 * @param actual
	 *            actual Ecore Resource
	 */
	public void assertModelEquals(final Resource expected, final Resource actual) {
		ModelEqualityUtil.getInstance().assertModelEquals(expected, actual);
	}

	/**
	 * Asserts that two EObject lists are {@link EcoreUtil#equals(List, List) equal}. If they are not, an
	 * {@link AssertionError} is thrown with a human-readable rendering of the differences.
	 *
	 * @param expected
	 *            expected EObject lists
	 * @param actual
	 *            actual EObject lists
	 */
	public void assertModelEquals(final List<EObject> expected, final List<EObject> actual) {
		ModelEqualityUtil.getInstance().assertModelEquals(expected, actual);
	}

	/**
	 * Asserts that two EObjects are {@link EcoreUtil#equals(EObject, EObject) equal}. If they are not, an
	 * {@link AssertionError} is thrown with a human-readable rendering of the differences.
	 *
	 * @param expected
	 *            expected EObject
	 * @param actual
	 *            actual EObject
	 */
	public void assertModelEquals(final EObject expected, final EObject actual) {
		ModelEqualityUtil.getInstance().assertModelEquals(expected, actual);
	}
}
