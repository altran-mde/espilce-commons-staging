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

import static org.espilce.commons.assertion.Assertion.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.emf.UriUtils;
import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.testsupport.Assert2;
import org.espilce.commons.text.StringUtils2;

/**
 * Convenience base class for test classes that need to load models.
 * 
 * @since 0.1
 */
public class AModelLoader {
	private ResourceSet resourceSet;
	private ILoadHelper loadHelper;
	
	/**
	 * Loads the file pointed to by {@code modelRelativePath}.
	 *
	 * <p>
	 * This method tries to resolve all proxies in the contained model.
	 * </p>
	 *
	 * <p>
	 * <b>Caution!</b> This method assumes the plug-in containing the model file
	 * to be unzipped!
	 * </p>
	 *
	 * @param classInPlugin
	 *            A class that is located in the same plug-in as the model file
	 *            to load.
	 * @param modelRelativePath
	 *            Path of the model file, relative to the plug-in containing the
	 *            given class.
	 *
	 * @return The the root element of the loaded model.
	 */
	public @NonNull EObject loadModel(final @NonNull String modelRelativePath) {
		return loadModelResource(modelRelativePath).getContents().iterator().next();
	}
	
	/**
	 * 
	 * @param modelUrl
	 * @return
	 * 
	 * @since 0.3
	 */ 
	public @NonNull EObject loadModel(final @NonNull URL modelUrl) {
		return loadModelResource(modelUrl).getContents().iterator().next();
	}
	
	/**
	 * 
	 * @param content
	 * @param modelRelativePath
	 * @return
	 * 
	 * @since 0.3
	 */
	public @NonNull EObject parseModel(final @NonNull CharSequence content, final @NonNull String modelRelativePath) {
		return parseModelResource(content, modelRelativePath).getContents().iterator().next();
	}
	
	/**
	 * Loads the file pointed to by {@code modelRelativePath}.
	 *
	 * <p>
	 * This method tries to resolve all proxies in the contained model.
	 * </p>
	 *
	 * <p>
	 * <b>Caution!</b> This method assumes the plug-in containing the model file
	 * to be unzipped!
	 * </p>
	 *
	 * @param classInPlugin
	 *            A class that is located in the same plug-in as the model file
	 *            to load.
	 * @param modelRelativePath
	 *            Path of the model file, relative to the plug-in containing the
	 *            given class.
	 *
	 * @return The EMF resource loaded from {@code modelRelativePath}.
	 */
	public @NonNull Resource loadModelResource(final @NonNull String modelRelativePath) {
		final ResourceSet resourceSet = provideResourceSet();
		return loadModelResource(modelRelativePath, resourceSet);
	}
	
	/**
	 * 
	 * @param modelUrl
	 * @return
	 * 
	 * @since 0.3
	 */
	public @NonNull Resource loadModelResource(final @NonNull URL modelUrl) {
		final ResourceSet resourceSet = provideResourceSet();
		final String contentTypeId = getLoadHelper().getContentTypeId(getClass(), modelUrl);
		return loadModelResource(UriUtils.asEmfUri(modelUrl), resourceSet, contentTypeId, null);
	}
	
	/**
	 * 
	 * @param content
	 * @param modelRelativePath
	 * @return
	 * 
	 * @since 0.3
	 */
	public Resource parseModelResource(@NonNull final CharSequence content, @NonNull final String modelRelativePath) {
		final ResourceSet resourceSet = provideResourceSet();
		final String contentTypeId = getLoadHelper().getContentTypeId(getClass(), modelRelativePath);
		return loadModelResource(
				createSyntheticUri(modelRelativePath), resourceSet, contentTypeId,
				new CharSequenceInputStream(content, getCharset())
		);
	}
	
	protected Charset getCharset() { return Charset.defaultCharset(); }
	
	protected URI createSyntheticUri(final @NonNull String modelRelativePath) {
		return URI.createURI(modelRelativePath);
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
		if (this.resourceSet == null) {
			this.resourceSet = new ResourceSetImpl();
		}
		
		return this.resourceSet;
	}
	
	public void assertOutputEquals(
			final @NonNull String expectedOutputParent,
			final @NonNull Map<@NonNull String, @NonNull CharSequence> actuals
	) throws IOException {
		final List<URL> expectedUrls = getLoadHelper().findMatchingResources(getClass(), expectedOutputParent);
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
							StringUtils2.getCommonSuffix(firstExpectedUrlName, actualNames.iterator().next())
					);
				} else {
					expectedNames = Collections.singletonList(firstExpectedUrlName);
				}
				break;
			
			default:
				
				final List<@NonNull Path> collect = expectedUrls.stream().map(ConversionUtils::asJavaPath).collect(Collectors.toList());
				Path p = null;
				for (final Path c : collect) {
					if (p == null) {
						p = c;
					} else {
						Path tmp = p;
						for (final Path subpath : c) {
							if (!subpath.startsWith(p)) {
								break;
							}
							tmp = subpath;
						}
						p = tmp;
					}
				}
				
				final List<List<String>> expectedPathsSegments = expectedUrlNames.stream().map(s -> s.split("/"))
						.map(Arrays::asList).collect(Collectors.toList());
				
				int prefixes;
				boolean done = false;
				for (prefixes = 0; !done; prefixes++) {
					String segment = null;
					for (final List<String> pathSegments : expectedPathsSegments) {
						if (pathSegments.size() < prefixes) {
							done = true;
							break;
						}
						if (segment == null) {
							segment = pathSegments.get(prefixes);
						} else if (!segment.equals(pathSegments.get(prefixes))) {
							done = true;
							break;
						}
					}
				}
				final int finalPrefixes = Math.max(0, prefixes - 1);
				
				expectedNames = expectedPathsSegments.stream().map(px -> px.subList(finalPrefixes, px.size()))
						.map(px -> String.join("/", px)).collect(Collectors.toList());
				break;
		}
		
		assertEquals(String.join("\n", expectedNames), String.join("\n", actualNames));
		
		for (final Entry<String, CharSequence> file : actuals.entrySet()) {
			final String name = file.getKey();
			final CharSequence actualContent = file.getValue();
			
			final InputStream expectedStream = getLoadHelper().getContents(getClass(), expectedOutputParent + name);
			final String expectedContent = IOUtils.toString(expectedStream);
			
			Assert2.assertEqualsNormalizedNewline("difference in " + name, expectedContent, actualContent);
		}
	}
	
	protected @NonNull Resource loadModelResource(
			final @NonNull String modelRelativePath,
			final @NonNull ResourceSet resourceSet
	) {
		final String contentTypeId = getLoadHelper().getContentTypeId(getClass(), modelRelativePath);
		final URI uri = toLocalmostUri(getClass(), modelRelativePath);
		return loadModelResource(uri, resourceSet, contentTypeId, null);
	}
	
	protected @NonNull Resource loadModelResource(
			final @NonNull URI modelUri, final @NonNull ResourceSet resourceSet,
			final @Nullable String contentTypeId, final @Nullable InputStream content
	) {
		try {
			final Resource modelResource;
			if (contentTypeId != null) {
				modelResource = resourceSet.createResource(modelUri, contentTypeId);
			} else {
				modelResource = resourceSet.createResource(modelUri);
			}
			
			if (content != null) {
				modelResource.load(content, resourceSet.getLoadOptions());
			} else {
				modelResource.load(resourceSet.getLoadOptions());
			}
			return modelResource;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected @NonNull URI toLocalmostUri(
			final @NonNull Class<?> classInContext,
			final @NonNull String resourceRelativePath
	) {
		final URL url = getLoadHelper().toLocalmostUrl(classInContext, resourceRelativePath);
		return UriUtils.asEmfUri(url);
	}
	
	protected @NonNull ILoadHelper getLoadHelper() {
		if (this.loadHelper == null) {
			this.loadHelper = createLoadHelper();
		}
		
		return this.loadHelper;
	}
	
	protected @NonNull ILoadHelper createLoadHelper() {
		return new FilesystemClassloaderLoadHelper();
	}
}
