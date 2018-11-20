package org.espilce.commons.emf.testsupport;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.NonNull;

/**
 * Convenience base class for test classes that need to load models.
 * 
 * @since 0.1
 */
public class AModelLoader {
	private ATestModelLoadHelper testModelLoadHelper;

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
	public @NonNull EObject loadModel(final @NonNull String modelRelativePath) {
		return getTestModelLoadHelper().loadModel(getClass(), modelRelativePath);
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
	public @NonNull Resource loadModelResource(final @NonNull String modelRelativePath) {
		return getTestModelLoadHelper().loadModelResource(getClass(), modelRelativePath);
	}

	public void assertOutputEquals(final @NonNull String expectedOutputParent,
			final @NonNull Map<@NonNull String, @NonNull CharSequence> actuals) throws IOException {
		final List<URL> expectedUrls = getTestModelLoadHelper().findMatchingResources(getClass(), expectedOutputParent);
		final List<String> expectedUrlNames = expectedUrls.stream().map(URL::getPath).collect(Collectors.toList());

		final String commonPrefix = StringUtils
				.getCommonPrefix(expectedUrlNames.toArray(new String[expectedUrlNames.size()]));
		final int commonPrefixLength = commonPrefix.length();

		final String[] expectedNames = expectedUrlNames.stream().map(p -> p.substring(commonPrefixLength))
				.toArray(String[]::new);

		final String[] actualNames = actuals.keySet().toArray(new String[actuals.size()]);

		assertEquals(Arrays.asList(expectedNames).toString().replace(",", "\n"),
				Arrays.asList(actualNames).toString().replace(",", "\n"));

		for (Entry<String, CharSequence> file : actuals.entrySet()) {
			final String name = file.getKey();
			final CharSequence actualContent = file.getValue();

			final InputStream expectedStream = getTestModelLoadHelper().getContents(getClass(),
					expectedOutputParent + name);
			final String expectedContent = IOUtils.toString(expectedStream);

			assertEquals("difference in " + name, expectedContent, actualContent);
		}
	}

	protected @NonNull ATestModelLoadHelper getTestModelLoadHelper() {
		if (testModelLoadHelper == null) {
			testModelLoadHelper = createTestModelLoadHelper();
		}

		return testModelLoadHelper;
	}

	protected @NonNull ATestModelLoadHelper createTestModelLoadHelper() {
		return new ClassloaderTestModelLoader();
	}
}
