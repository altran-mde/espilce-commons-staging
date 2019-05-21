package org.espilce.commons.emf.testsupport.test.amodelloader;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.input.CharSequenceInputStream;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.emf.testsupport.AModelLoader;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.junit.Test;

public class TestAssertOutputEquals {
	@Test
	public void xx() throws Exception {
		final AModelLoader modelLoader = new AModelLoader() {
			@Override
			protected @NonNull ILoadHelper createLoadHelper() {
				return new ILoadHelper() {
					
					@Override
					public @NonNull URL toLocalmostUrl(
							@NonNull final Class<?> classInContext, @NonNull final String resourceRelativePath
					)
							throws IllegalArgumentException {
						throw new UnsupportedOperationException();
					}
					
					@Override
					public @NonNull InputStream getContents(
							@NonNull final Class<?> classInContext, @NonNull final URL url
					)
							throws IllegalArgumentException, IOException {
						throw new UnsupportedOperationException();
					}
					
					@Override
					public @NonNull InputStream getContents(
							@NonNull final Class<?> classInContext, @NonNull final String resourceRelativePath
					)
							throws IllegalArgumentException, IOException {
						return new CharSequenceInputStream("", "utf-8");
					}
					
					@Override
					public @Nullable String getContentTypeId(
							@NonNull final Class<?> classInContext, @NonNull final URL url
					) {
						return null;
					}
					
					@Override
					public @Nullable String getContentTypeId(
							@NonNull final Class<?> classInContext, @NonNull final String resourceRelativePath
					) {
						return null;
					}
					
					@Override
					public @NonNull List<@NonNull URL> findMatchingResources(
							@NonNull final Class<?> classInContext, @NonNull final String parentRelativePath
					) throws IllegalArgumentException {
						final ArrayList<@NonNull URL> result = new ArrayList<>();
						try {
							result.add(new URL("file://c:/some/dir/aber/file.h"));
							result.add(new URL("file://c:/some/dir/awer/file.cpp"));
						} catch (final MalformedURLException e) {
							fail(e.getMessage());
						}
						return result;
					}
				};
			}
		};
		
		final Map<@NonNull String, @NonNull CharSequence> actuals = new LinkedHashMap<>();
		
		actuals.put("aber/file.h", "");
		actuals.put("awer/file.cpp", "");
		
		modelLoader.assertOutputEquals("some/dir", actuals);
	}
}
