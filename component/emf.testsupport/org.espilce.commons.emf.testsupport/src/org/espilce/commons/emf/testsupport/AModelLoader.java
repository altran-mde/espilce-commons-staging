/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.testsupport;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.NonNull;
import org.espilce.commons.lang.StringUtils2;
import org.espilce.commons.testsupport.Assert2;

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
	 * @return The EMF resource loaded from {@code modelRelativePath}.
	 */
	public @NonNull Resource loadModelResource(final @NonNull String modelRelativePath) {
		return getTestModelLoadHelper().loadModelResource(getClass(), modelRelativePath);
	}

	public void assertOutputEquals(final @NonNull String expectedOutputParent,
			final @NonNull Map<@NonNull String, @NonNull CharSequence> actuals) throws IOException {
		final List<URL> expectedUrls = getTestModelLoadHelper().findMatchingResources(getClass(), expectedOutputParent);
		final List<String> expectedUrlNames = expectedUrls.stream().map(URL::getPath).collect(Collectors.toList());

		final List<String> actualNames = actuals.keySet().stream().sorted().collect(Collectors.toList());

		List<String> expectedNames;
		switch (expectedUrlNames.size()) {
		case 0:
			expectedNames = Collections.emptyList();
			break;

		case 1:
			final String firstExpectedUrlName = expectedUrlNames.iterator().next();
			if (actualNames.size() > 0) {
				expectedNames = Collections.singletonList(
						StringUtils2.getCommonSuffix(firstExpectedUrlName, actualNames.iterator().next()));
			} else {
				expectedNames = Collections.singletonList(firstExpectedUrlName);
			}
			break;

		default:
			expectedNames = Arrays.asList(
					StringUtils2.removeCommonPrefix(expectedUrlNames.toArray(new String[expectedUrlNames.size()])));
			break;
		}

		assertEquals(String.join("\n", expectedNames), String.join("\n", actualNames));

		for (Entry<String, CharSequence> file : actuals.entrySet()) {
			final String name = file.getKey();
			final CharSequence actualContent = file.getValue();

			final InputStream expectedStream = getTestModelLoadHelper().getContents(getClass(),
					expectedOutputParent + name);
			final String expectedContent = IOUtils.toString(expectedStream);

			Assert2.assertEqualsNormalizedNewline("difference in " + name, expectedContent, actualContent);
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
