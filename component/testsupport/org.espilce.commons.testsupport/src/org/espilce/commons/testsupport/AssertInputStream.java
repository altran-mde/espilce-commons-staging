package org.espilce.commons.testsupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.annotation.Nullable;

public class AssertInputStream {

	public void assertContentEquals(final @Nullable String message, final @Nullable InputStream expected,
			final @Nullable InputStream actual) {
		assertNotNull(message, expected);
		assertNotNull(message, actual);

		try {
			final String expectedContent = IOUtils.toString(expected);
			final String actualContent = IOUtils.toString(actual);

			assertEquals(message, expectedContent, actualContent);
		} catch (final IOException e) {
			throw new AssertionError(message, e);
		}
	}

	public void assertContentEquals(final @Nullable InputStream expected, final @Nullable InputStream actual) {
		assertContentEquals("difference in " + actual, expected, actual);
	}

	public void assertContentEqualsNormalizedNewline(final @Nullable String message,
			final @Nullable InputStream expected,
			final @Nullable InputStream actual) {
		assertNotNull(message, expected);
		assertNotNull(message, actual);

		try {
			final String expectedContent = IOUtils.toString(expected);
			final String actualContent = IOUtils.toString(actual);

			Assert2.assertEqualsNormalizedNewline(message, expectedContent, actualContent);
		} catch (final IOException e) {
			throw new AssertionError(message, e);
		}
	}

	public void assertContentEqualsNormalizedNewline(final @Nullable InputStream expected,
			final @Nullable InputStream actual) {
		assertContentEqualsNormalizedNewline("difference in " + actual, expected, actual);
	}
}
