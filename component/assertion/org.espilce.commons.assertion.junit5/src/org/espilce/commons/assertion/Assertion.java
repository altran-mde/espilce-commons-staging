package org.espilce.commons.assertion;

import org.eclipse.jdt.annotation.Nullable;
import org.junit.jupiter.api.Assertions;

public class Assertion {
	public static void assertArrayEquals(final boolean @Nullable [] expected, final boolean @Nullable [] actual) {
		Assertions.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final boolean @Nullable [] expected, final boolean @Nullable [] actual,
			final @Nullable String message
	) {
		Assertions.assertArrayEquals(expected, actual, message);
	}
	
	public static void assertArrayEquals(final byte @Nullable [] expected, final byte @Nullable [] actual) {
		Assertions.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final byte @Nullable [] expected, final byte @Nullable [] actual,
			final @Nullable String message
	) {
		Assertions.assertArrayEquals(expected, actual, message);
	}
	
	public static void assertArrayEquals(final char @Nullable [] expected, final char @Nullable [] actual) {
		Assertions.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final char @Nullable [] expected, final char @Nullable [] actual,
			final @Nullable String message
	) {
		Assertions.assertArrayEquals(expected, actual, message);
	}
	
	public static void assertArrayEquals(
			final double @Nullable [] expected, final double @Nullable [] actual,
			final double delta
	) {
		Assertions.assertArrayEquals(expected, actual, delta);
	}
	
	public static void assertArrayEquals(
			final double @Nullable [] expected, final double @Nullable [] actual,
			final double delta,
			final @Nullable String message
	) {
		Assertions.assertArrayEquals(expected, actual, delta, message);
	}
	
	public static void assertArrayEquals(
			final float @Nullable [] expected, final float @Nullable [] actual,
			final float delta
	) {
		Assertions.assertArrayEquals(expected, actual, delta);
	}
	
	public static void assertArrayEquals(
			final float @Nullable [] expected, final float @Nullable [] actual,
			final float delta,
			final @Nullable String message
	) {
		Assertions.assertArrayEquals(expected, actual, delta, message);
	}
	
	public static void assertArrayEquals(final int @Nullable [] expected, final int @Nullable [] actual) {
		Assertions.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final int @Nullable [] expected, final int @Nullable [] actual,
			final @Nullable String message
	) {
		Assertions.assertArrayEquals(expected, actual, message);
	}
	
	public static void assertArrayEquals(final long @Nullable [] expected, final long @Nullable [] actual) {
		Assertions.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final long @Nullable [] expected, final long @Nullable [] actual,
			final @Nullable String message
	) {
		Assertions.assertArrayEquals(expected, actual, message);
	}
	
	public static void assertArrayEquals(
			final @Nullable Object @Nullable [] expected,
			final @Nullable Object @Nullable [] actual
	) {
		Assertions.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final @Nullable Object @Nullable [] expected,
			final @Nullable Object @Nullable [] actual,
			final @Nullable String message
	) {
		Assertions.assertArrayEquals(expected, actual, message);
	}
	
	public static void assertArrayEquals(final short @Nullable [] expected, final short @Nullable [] actual) {
		Assertions.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final short @Nullable [] expected, final short @Nullable [] actual,
			final @Nullable String message
	) {
		Assertions.assertArrayEquals(expected, actual, message);
	}
	
	public static void assertEquals(final byte expected, final byte actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final byte expected, final byte actual, final @Nullable String message) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final byte expected, final @Nullable Byte actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final byte expected, final @Nullable Byte actual, final @Nullable String message) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Byte expected, final byte actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final @Nullable Byte expected, final byte actual, final @Nullable String message) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Byte expected, final @Nullable Byte actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Byte expected, final @Nullable Byte actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final char expected, final char actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final char expected, final char actual, final @Nullable String message) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final char expected, final @Nullable Character actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final char expected, final @Nullable Character actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Character expected, final char actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Character expected, final char actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Character expected, final @Nullable Character actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Character expected, final @Nullable Character actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final double expected, final double actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final double expected, final double actual, final double delta) {
		Assertions.assertEquals(expected, actual, delta);
	}
	
	public static void assertEquals(
			final double expected, final double actual, final double delta,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, delta, message);
	}
	
	public static void assertEquals(final double expected, final double actual, final @Nullable String message) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final double expected, final @Nullable Double actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final double expected, final @Nullable Double actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Double expected, final double actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Double expected, final double actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Double expected, final @Nullable Double actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Double expected, final @Nullable Double actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final float expected, final float actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final float expected, final float actual, final float delta) {
		Assertions.assertEquals(expected, actual, delta);
	}
	
	public static void assertEquals(
			final float expected, final float actual, final float delta,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, delta, message);
	}
	
	public static void assertEquals(final float expected, final float actual, final @Nullable String message) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final float expected, final @Nullable Float actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final float expected, final @Nullable Float actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Float expected, final float actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Float expected, final float actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Float expected, final @Nullable Float actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Float expected, final @Nullable Float actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final int expected, final int actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final int expected, final int actual, final @Nullable String message) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final int expected, final @Nullable Integer actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final int expected, final @Nullable Integer actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Integer expected, final int actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Integer expected, final int actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Integer expected, final @Nullable Integer actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Integer expected, final @Nullable Integer actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final long expected, final long actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final long expected, final long actual, final @Nullable String message) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final long expected, final @Nullable Long actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final long expected, final @Nullable Long actual, final @Nullable String message) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Long expected, final long actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final @Nullable Long expected, final long actual, final @Nullable String message) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Long expected, final @Nullable Long actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Long expected, final @Nullable Long actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Object expected, final @Nullable Object actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Object expected, final @Nullable Object actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final short expected, final short actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final short expected, final short actual, final @Nullable String message) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final short expected, final @Nullable Short actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final short expected, final @Nullable Short actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Short expected, final short actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Short expected, final short actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertEquals(final @Nullable Short expected, final @Nullable Short actual) {
		Assertions.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Short expected, final @Nullable Short actual,
			final @Nullable String message
	) {
		Assertions.assertEquals(expected, actual, message);
	}
	
	public static void assertFalse(final boolean condition) {
		Assertions.assertFalse(condition);
	}
	
	public static void assertFalse(final boolean condition, final @Nullable String message) {
		Assertions.assertFalse(condition, message);
	}
	
	public static void assertNotEquals(final byte unexpected, final byte actual) {
		Assertions.assertNotEquals(unexpected, actual);
		
	}
	
	public static void assertNotEquals(final byte unexpected, final byte actual, final @Nullable String message) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final byte unexpected, final @Nullable Byte actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final byte unexpected, final @Nullable Byte actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Byte unexpected, final byte actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Byte unexpected, final byte actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Byte unexpected, final @Nullable Byte actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Byte unexpected, final @Nullable Byte actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final char unexpected, final char actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final char unexpected, final char actual, final @Nullable String message) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final char unexpected, final @Nullable Character actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final char unexpected, final @Nullable Character actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Character unexpected, final char actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Character unexpected, final char actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Character unexpected, final @Nullable Character actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Character unexpected, final @Nullable Character actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final double unexpected, final double actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final double unexpected, final double actual, final double delta) {
		Assertions.assertNotEquals(unexpected, actual, delta);
	}
	
	public static void assertNotEquals(
			final double unexpected, final double actual, final double delta,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, delta, message);
	}
	
	public static void assertNotEquals(final double unexpected, final double actual, final @Nullable String message) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final double unexpected, final @Nullable Double actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final double unexpected, final @Nullable Double actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Double unexpected, final double actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Double unexpected, final double actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Double unexpected, final @Nullable Double actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Double unexpected, final @Nullable Double actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final float unexpected, final float actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final float unexpected, final float actual, final float delta) {
		Assertions.assertNotEquals(unexpected, actual, delta);
	}
	
	public static void assertNotEquals(
			final float unexpected, final float actual, final float delta,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, delta, message);
	}
	
	public static void assertNotEquals(final float unexpected, final float actual, final @Nullable String message) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final float unexpected, final @Nullable Float actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final float unexpected, final @Nullable Float actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Float unexpected, final float actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Float unexpected, final float actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Float unexpected, final @Nullable Float actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Float unexpected, final @Nullable Float actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final int unexpected, final int actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final int unexpected, final int actual, final @Nullable String message) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final int unexpected, final @Nullable Integer actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final int unexpected, final @Nullable Integer actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Integer unexpected, final int actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Integer unexpected, final int actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Integer unexpected, final @Nullable Integer actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Integer unexpected, final @Nullable Integer actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final long unexpected, final long actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final long unexpected, final long actual, final @Nullable String message) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final long unexpected, final @Nullable Long actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final long unexpected, final @Nullable Long actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Long unexpected, final long actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Long unexpected, final long actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Long unexpected, final @Nullable Long actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Long unexpected, final @Nullable Long actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Object unexpected, final @Nullable Object actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Object unexpected, final @Nullable Object actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final short unexpected, final short actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final short unexpected, final short actual, final @Nullable String message) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final short unexpected, final @Nullable Short actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final short unexpected, final @Nullable Short actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Short unexpected, final short actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Short unexpected, final short actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotEquals(final @Nullable Short unexpected, final @Nullable Short actual) {
		Assertions.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Short unexpected, final @Nullable Short actual,
			final @Nullable String message
	) {
		Assertions.assertNotEquals(unexpected, actual, message);
	}
	
	public static void assertNotNull(final @Nullable Object actual) {
		Assertions.assertNotNull(actual);
	}
	
	public static void assertNotNull(final @Nullable Object actual, final @Nullable String message) {
		Assertions.assertNotNull(actual, message);
	}
	
	public static void assertNotSame(final @Nullable Object unexpected, final @Nullable Object actual) {
		Assertions.assertNotSame(unexpected, actual);
	}
	
	public static void assertNotSame(
			final @Nullable Object unexpected, final @Nullable Object actual,
			final @Nullable String message
	) {
		Assertions.assertNotSame(unexpected, actual, message);
	}
	
	public static void assertNull(final @Nullable Object actual) {
		Assertions.assertNull(actual);
	}
	
	public static void assertNull(final @Nullable Object actual, final @Nullable String message) {
		Assertions.assertNull(actual, message);
	}
	
	public static void assertSame(final @Nullable Object expected, final @Nullable Object actual) {
		Assertions.assertSame(expected, actual);
	}
	
	public static void assertSame(
			final @Nullable Object expected, final @Nullable Object actual,
			final @Nullable String message
	) {
		Assertions.assertSame(expected, actual, message);
	}
	
	public static void assertTrue(final boolean condition) {
		Assertions.assertTrue(condition);
	}
	
	public static void assertTrue(final boolean condition, final @Nullable String message) {
		Assertions.assertTrue(condition, message);
	}
	
	public static void fail() {
		Assertions.fail();
	}
	
	public static void fail(final @Nullable String message) {
		Assertions.fail(message);
	}
}
