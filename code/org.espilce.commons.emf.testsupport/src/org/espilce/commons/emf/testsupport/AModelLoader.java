package org.espilce.commons.emf.testsupport;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Convenience base class for test classes that need to load models.
 */
@SuppressWarnings("all")
public class AModelLoader {
	private ITestModelLoadHelper testModelLoadHelper;

	/**
	 * Loads the file pointed to by {@code modelRelativePath}.
	 *
	 * <p>
	 * This method tries to resolve all proxies in the contained model.
	 * </p>
	 *
	 * <p>
	 * <b>Caution!</b> This method assumes the plug-in containing the model file to
	 * be unzipped!
	 * </p>
	 *
	 * @param classInPlugin
	 *            A class that is located in the same plug-in as the model file to
	 *            load.
	 * @param modelRelativePath
	 *            Path of the model file, relative to the plug-in containing the
	 *            given class.
	 *
	 * @return The the root element of the loaded model.
	 */
	public EObject loadModel(final String modelRelativePath) {
		return getTestModelLoadHelper().loadModel(this.getClass(), modelRelativePath);
	}

	/**
	 * Loads the file pointed to by {@code modelRelativePath}.
	 *
	 * <p>
	 * This method tries to resolve all proxies in the contained model.
	 * </p>
	 *
	 * <p>
	 * <b>Caution!</b> This method assumes the plug-in containing the model file to
	 * be unzipped!
	 * </p>
	 *
	 * @param classInPlugin
	 *            A class that is located in the same plug-in as the model file to
	 *            load.
	 * @param modelRelativePath
	 *            Path of the model file, relative to the plug-in containing the
	 *            given class.
	 *
	 * @return The Ecore resource loaded from {@code modelRelativePath}.
	 */
	public Resource loadModelResource(final String modelRelativePath) {
		return getTestModelLoadHelper().loadModelResource(this.getClass(), modelRelativePath);
	}

	/**
	 * Asserts that two Ecore Resources are equal. That is, they have
	 * {@link EcoreUtil#equals(List, List) equal} {@link Resource#getContents()
	 * contents}. If they are not equal, an {@link AssertionError} is thrown with a
	 * human-readable rendering of the differences.
	 *
	 * @param expected
	 *            expected Ecore Resource
	 * @param actual
	 *            actual Ecore Resource
	 */
	public void assertModelEquals(final Resource expected, final Resource actual) {
		getModelEqualityUtil().assertModelEquals(expected, actual);
	}

	/**
	 * Asserts that two EObject lists are {@link EcoreUtil#equals(List, List)
	 * equal}. If they are not, an {@link AssertionError} is thrown with a
	 * human-readable rendering of the differences.
	 *
	 * @param expected
	 *            expected EObject lists
	 * @param actual
	 *            actual EObject lists
	 */
	public void assertModelEquals(final List<EObject> expected, final List<EObject> actual) {
		getModelEqualityUtil().assertModelEquals(expected, actual);
	}

	/**
	 * Asserts that two EObjects are {@link EcoreUtil#equals(EObject, EObject)
	 * equal}. If they are not, an {@link AssertionError} is thrown with a
	 * human-readable rendering of the differences.
	 *
	 * @param expected
	 *            expected EObject
	 * @param actual
	 *            actual EObject
	 */
	public void assertModelEquals(final EObject expected, final EObject actual) {
		getModelEqualityUtil().assertModelEquals(expected, actual);
	}

	public void assertOutputEquals(final String expectedOutputParent, final Map<String, CharSequence> actuals)
			throws IOException {
		List<URL> expectedUrls = getTestModelLoadHelper().findMatchingResources(getClass(), expectedOutputParent);
		final List<String> expectedUrlNames = expectedUrls.stream().map(URL::getPath).collect(Collectors.toList());

		final String commonPrefix = StringUtils
				.getCommonPrefix(expectedUrlNames.toArray(new String[expectedUrlNames.size()]));
		int commonPrefixLength = commonPrefix.length();

		final String[] expectedNames = expectedUrlNames.stream().map(p -> p.substring(commonPrefixLength))
				.toArray(String[]::new);

		final String[] actualNames = actuals.keySet().toArray(new String[actuals.size()]);

		assertArrayEquals(expectedNames, actualNames);

		for (Entry<String, CharSequence> file : actuals.entrySet()) {
			String name = file.getKey();
			CharSequence actualContent = file.getValue();

			final InputStream expectedStream = getTestModelLoadHelper().getContents(getClass(),
					expectedOutputParent + name);
			final String expectedContent = IOUtils.toString(expectedStream);

			assertEquals("difference in " + name, expectedContent, actualContent);
		}
	}

	protected ITestModelLoadHelper getTestModelLoadHelper() {
		if (testModelLoadHelper == null) {
			testModelLoadHelper = createTestModelLoadHelper();
		}

		return testModelLoadHelper;
	}

	protected ITestModelLoadHelper createTestModelLoadHelper() {
		return new ClassloaderTestModelLoader();
	}

	protected ModelEqualityUtil getModelEqualityUtil() {
		return ModelEqualityUtil.getInstance();
	}
}
