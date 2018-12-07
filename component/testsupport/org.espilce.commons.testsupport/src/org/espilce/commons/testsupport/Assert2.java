package org.espilce.commons.testsupport;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.lang.StringUtils2;

public class Assert2 {
	public static void assertEqualsNormalizedNewline(
			final @Nullable String message,
			final @Nullable Object expected,
			final @Nullable Object actual) {
		if (expected == null || actual == null) {
			assertEquals(message, expected, actual);
		}
		
		assertEquals(
				message,
				StringUtils2.normalizeNewline(expected.toString()),
				StringUtils2.normalizeNewline(actual.toString()));
	}
	
	public static void assertEqualsNormalizedNewline(final @Nullable Object expected, final @Nullable Object actual) {
		assertEqualsNormalizedNewline(null, expected, actual);
	}
}
