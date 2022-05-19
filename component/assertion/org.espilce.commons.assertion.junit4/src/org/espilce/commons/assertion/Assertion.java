package org.espilce.commons.assertion;

import org.eclipse.jdt.annotation.Nullable;
import org.junit.Assert;

public class Assertion {
	public static void assertArrayEquals(final boolean @Nullable [] expected, final boolean @Nullable [] actual) {
		Assert.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final boolean @Nullable [] expected, final boolean @Nullable [] actual,
			final @Nullable String message
	) {
		Assert.assertArrayEquals(message, expected, actual);
	}
	
	public static void assertArrayEquals(final byte @Nullable [] expected, final byte @Nullable [] actual) {
		Assert.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final byte @Nullable [] expected, final byte @Nullable [] actual,
			final @Nullable String message
	) {
		Assert.assertArrayEquals(message, expected, actual);
	}
	
	public static void assertArrayEquals(final char @Nullable [] expected, final char @Nullable [] actual) {
		Assert.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final char @Nullable [] expected, final char @Nullable [] actual,
			final @Nullable String message
	) {
		Assert.assertArrayEquals(message, expected, actual);
	}
	
	public static void assertArrayEquals(
			final double @Nullable [] expected, final double @Nullable [] actual,
			final double delta
	) {
		Assert.assertArrayEquals(expected, actual, delta);
	}
	
	public static void assertArrayEquals(
			final double @Nullable [] expected, final double @Nullable [] actual,
			final double delta,
			final @Nullable String message
	) {
		Assert.assertArrayEquals(message, expected, actual, delta);
	}
	
	public static void assertArrayEquals(
			final float @Nullable [] expected, final float @Nullable [] actual,
			final float delta
	) {
		Assert.assertArrayEquals(expected, actual, delta);
	}
	
	public static void assertArrayEquals(
			final float @Nullable [] expected, final float @Nullable [] actual,
			final float delta,
			final @Nullable String message
	) {
		Assert.assertArrayEquals(message, expected, actual, delta);
	}
	
	public static void assertArrayEquals(final int @Nullable [] expected, final int @Nullable [] actual) {
		Assert.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final int @Nullable [] expected, final int @Nullable [] actual,
			final @Nullable String message
	) {
		Assert.assertArrayEquals(message, expected, actual);
	}
	
	public static void assertArrayEquals(final long @Nullable [] expected, final long @Nullable [] actual) {
		Assert.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final long @Nullable [] expected, final long @Nullable [] actual,
			final @Nullable String message
	) {
		Assert.assertArrayEquals(message, expected, actual);
	}
	
	public static void assertArrayEquals(
			final @Nullable Object @Nullable [] expected,
			final @Nullable Object @Nullable [] actual
	) {
		Assert.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final @Nullable Object @Nullable [] expected,
			final @Nullable Object @Nullable [] actual,
			final @Nullable String message
	) {
		Assert.assertArrayEquals(message, expected, actual);
	}
	
	public static void assertArrayEquals(final short @Nullable [] expected, final short @Nullable [] actual) {
		Assert.assertArrayEquals(expected, actual);
	}
	
	public static void assertArrayEquals(
			final short @Nullable [] expected, final short @Nullable [] actual,
			final @Nullable String message
	) {
		Assert.assertArrayEquals(message, expected, actual);
	}
	
	public static void assertEquals(final byte expected, final byte actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final byte expected, final byte actual, final @Nullable String message) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final byte expected, final @Nullable Byte actual) {
		Assert.assertEquals((Byte) expected, actual);
	}
	
	public static void assertEquals(final byte expected, final @Nullable Byte actual, final @Nullable String message) {
		Assert.assertEquals(message, (Byte) expected, actual);
	}
	
	public static void assertEquals(final @Nullable Byte expected, final byte actual) {
		Assert.assertEquals(expected, (Byte) actual);
	}
	
	public static void assertEquals(final @Nullable Byte expected, final byte actual, final @Nullable String message) {
		Assert.assertEquals(message, expected, (Byte) actual);
	}
	
	public static void assertEquals(final @Nullable Byte expected, final @Nullable Byte actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Byte expected, final @Nullable Byte actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final char expected, final char actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final char expected, final char actual, final @Nullable String message) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final char expected, final @Nullable Character actual) {
		Assert.assertEquals((Character) expected, actual);
	}
	
	public static void assertEquals(
			final char expected, final @Nullable Character actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, (Character) expected, actual);
	}
	
	public static void assertEquals(final @Nullable Character expected, final char actual) {
		Assert.assertEquals(expected, (Character) actual);
	}
	
	public static void assertEquals(
			final @Nullable Character expected, final char actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, (Character) actual);
	}
	
	public static void assertEquals(final @Nullable Character expected, final @Nullable Character actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Character expected, final @Nullable Character actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final double expected, final double actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final double expected, final double actual, final double delta) {
		Assert.assertEquals(expected, actual, delta);
	}
	
	public static void assertEquals(
			final double expected, final double actual, final double delta,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, actual, delta);
	}
	
	public static void assertEquals(final double expected, final double actual, final @Nullable String message) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final double expected, final @Nullable Double actual) {
		Assert.assertEquals((Double) expected, actual);
	}
	
	public static void assertEquals(
			final double expected, final @Nullable Double actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, (Double) expected, actual);
	}
	
	public static void assertEquals(final @Nullable Double expected, final double actual) {
		Assert.assertEquals(expected, (Double) actual);
	}
	
	public static void assertEquals(
			final @Nullable Double expected, final double actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, (Double) actual);
	}
	
	public static void assertEquals(final @Nullable Double expected, final @Nullable Double actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Double expected, final @Nullable Double actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final float expected, final float actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final float expected, final float actual, final float delta) {
		Assert.assertEquals(expected, actual, delta);
	}
	
	public static void assertEquals(
			final float expected, final float actual, final float delta,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, actual, delta);
	}
	
	public static void assertEquals(final float expected, final float actual, final @Nullable String message) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final float expected, final @Nullable Float actual) {
		Assert.assertEquals((Float) expected, actual);
	}
	
	public static void assertEquals(
			final float expected, final @Nullable Float actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, (Float) expected, actual);
	}
	
	public static void assertEquals(final @Nullable Float expected, final float actual) {
		Assert.assertEquals(expected, (Float) actual);
	}
	
	public static void assertEquals(
			final @Nullable Float expected, final float actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, (Float) actual);
	}
	
	public static void assertEquals(final @Nullable Float expected, final @Nullable Float actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Float expected, final @Nullable Float actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final int expected, final int actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final int expected, final int actual, final @Nullable String message) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final int expected, final @Nullable Integer actual) {
		Assert.assertEquals((Integer) expected, actual);
	}
	
	public static void assertEquals(
			final int expected, final @Nullable Integer actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, (Integer) expected, actual);
	}
	
	public static void assertEquals(final @Nullable Integer expected, final int actual) {
		Assert.assertEquals(expected, (Integer) actual);
	}
	
	public static void assertEquals(
			final @Nullable Integer expected, final int actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, (Integer) actual);
	}
	
	public static void assertEquals(final @Nullable Integer expected, final @Nullable Integer actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Integer expected, final @Nullable Integer actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final long expected, final long actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final long expected, final long actual, final @Nullable String message) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final long expected, final @Nullable Long actual) {
		Assert.assertEquals((Long) expected, actual);
	}
	
	public static void assertEquals(final long expected, final @Nullable Long actual, final @Nullable String message) {
		Assert.assertEquals(message, (Long) expected, actual);
	}
	
	public static void assertEquals(final @Nullable Long expected, final long actual) {
		Assert.assertEquals(expected, (Long) actual);
	}
	
	public static void assertEquals(final @Nullable Long expected, final long actual, final @Nullable String message) {
		Assert.assertEquals(message, expected, (Long) actual);
	}
	
	public static void assertEquals(final @Nullable Long expected, final @Nullable Long actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Long expected, final @Nullable Long actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final @Nullable Object expected, final @Nullable Object actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Object expected, final @Nullable Object actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final short expected, final short actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(final short expected, final short actual, final @Nullable String message) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertEquals(final short expected, final @Nullable Short actual) {
		Assert.assertEquals((Short) expected, actual);
	}
	
	public static void assertEquals(
			final short expected, final @Nullable Short actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, (Short) expected, actual);
	}
	
	public static void assertEquals(final @Nullable Short expected, final short actual) {
		Assert.assertEquals(expected, (Short) actual);
	}
	
	public static void assertEquals(
			final @Nullable Short expected, final short actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, (Short) actual);
	}
	
	public static void assertEquals(final @Nullable Short expected, final @Nullable Short actual) {
		Assert.assertEquals(expected, actual);
	}
	
	public static void assertEquals(
			final @Nullable Short expected, final @Nullable Short actual,
			final @Nullable String message
	) {
		Assert.assertEquals(message, expected, actual);
	}
	
	public static void assertFalse(final boolean condition) {
		Assert.assertFalse(condition);
	}
	
	public static void assertFalse(final boolean condition, final @Nullable String message) {
		Assert.assertFalse(message, condition);
	}
	
	public static void assertNotEquals(final byte unexpected, final byte actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final byte unexpected, final byte actual, final @Nullable String message) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final byte unexpected, final @Nullable Byte actual) {
		Assert.assertNotEquals((Byte) unexpected, actual);
	}
	
	public static void assertNotEquals(
			final byte unexpected, final @Nullable Byte actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, (Byte) unexpected, actual);
	}
	
	public static void assertNotEquals(final @Nullable Byte unexpected, final byte actual) {
		Assert.assertNotEquals(unexpected, (Byte) actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Byte unexpected, final byte actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, (Byte) actual);
	}
	
	public static void assertNotEquals(final @Nullable Byte unexpected, final @Nullable Byte actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Byte unexpected, final @Nullable Byte actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final char unexpected, final char actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final char unexpected, final char actual, final @Nullable String message) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final char unexpected, final @Nullable Character actual) {
		Assert.assertNotEquals((Character) unexpected, actual);
	}
	
	public static void assertNotEquals(
			final char unexpected, final @Nullable Character actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, (Character) unexpected, actual);
	}
	
	public static void assertNotEquals(final @Nullable Character unexpected, final char actual) {
		Assert.assertNotEquals(unexpected, (Character) actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Character unexpected, final char actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, (Character) actual);
	}
	
	public static void assertNotEquals(final @Nullable Character unexpected, final @Nullable Character actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Character unexpected, final @Nullable Character actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final double unexpected, final double actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final double unexpected, final double actual, final double delta) {
		Assert.assertNotEquals(unexpected, actual, delta);
	}
	
	public static void assertNotEquals(
			final double unexpected, final double actual, final double delta,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual, delta);
	}
	
	public static void assertNotEquals(final double unexpected, final double actual, final @Nullable String message) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final double unexpected, final @Nullable Double actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final double unexpected, final @Nullable Double actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final @Nullable Double unexpected, final double actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Double unexpected, final double actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final @Nullable Double unexpected, final @Nullable Double actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Double unexpected, final @Nullable Double actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final float unexpected, final float actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final float unexpected, final float actual, final float delta) {
		Assert.assertNotEquals(unexpected, actual, delta);
	}
	
	public static void assertNotEquals(
			final float unexpected, final float actual, final float delta,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual, delta);
	}
	
	public static void assertNotEquals(final float unexpected, final float actual, final @Nullable String message) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final float unexpected, final @Nullable Float actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final float unexpected, final @Nullable Float actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final @Nullable Float unexpected, final float actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Float unexpected, final float actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final @Nullable Float unexpected, final @Nullable Float actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Float unexpected, final @Nullable Float actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final int unexpected, final int actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final int unexpected, final int actual, final @Nullable String message) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final int unexpected, final @Nullable Integer actual) {
		Assert.assertNotEquals((Integer) unexpected, actual);
	}
	
	public static void assertNotEquals(
			final int unexpected, final @Nullable Integer actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, (Integer) unexpected, actual);
	}
	
	public static void assertNotEquals(final @Nullable Integer unexpected, final int actual) {
		Assert.assertNotEquals(unexpected, (Integer) actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Integer unexpected, final int actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, (Integer) actual);
	}
	
	public static void assertNotEquals(final @Nullable Integer unexpected, final @Nullable Integer actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Integer unexpected, final @Nullable Integer actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final long unexpected, final long actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final long unexpected, final long actual, final @Nullable String message) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final long unexpected, final @Nullable Long actual) {
		Assert.assertNotEquals((Long) unexpected, actual);
	}
	
	public static void assertNotEquals(
			final long unexpected, final @Nullable Long actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, (Long) unexpected, actual);
	}
	
	public static void assertNotEquals(final @Nullable Long unexpected, final long actual) {
		Assert.assertNotEquals(unexpected, (Long) actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Long unexpected, final long actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, (Long) actual);
	}
	
	public static void assertNotEquals(final @Nullable Long unexpected, final @Nullable Long actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Long unexpected, final @Nullable Long actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final @Nullable Object unexpected, final @Nullable Object actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Object unexpected, final @Nullable Object actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final short unexpected, final short actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(final short unexpected, final short actual, final @Nullable String message) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotEquals(final short unexpected, final @Nullable Short actual) {
		Assert.assertNotEquals((Short) unexpected, actual);
	}
	
	public static void assertNotEquals(
			final short unexpected, final @Nullable Short actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, (Short) unexpected, actual);
	}
	
	public static void assertNotEquals(final @Nullable Short unexpected, final short actual) {
		Assert.assertNotEquals(unexpected, (Short) actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Short unexpected, final short actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, (Short) actual);
	}
	
	public static void assertNotEquals(final @Nullable Short unexpected, final @Nullable Short actual) {
		Assert.assertNotEquals(unexpected, actual);
	}
	
	public static void assertNotEquals(
			final @Nullable Short unexpected, final @Nullable Short actual,
			final @Nullable String message
	) {
		Assert.assertNotEquals(message, unexpected, actual);
	}
	
	public static void assertNotNull(final @Nullable Object actual) {
		Assert.assertNotNull(actual);
	}
	
	public static void assertNotNull(final @Nullable Object actual, final @Nullable String message) {
		Assert.assertNotNull(message, actual);
	}
	
	public static void assertNotSame(final @Nullable Object unexpected, final @Nullable Object actual) {
		Assert.assertNotSame(unexpected, actual);
	}
	
	public static void assertNotSame(
			final @Nullable Object unexpected, final @Nullable Object actual,
			final @Nullable String message
	) {
		Assert.assertNotSame(message, unexpected, actual);
	}
	
	public static void assertNull(final @Nullable Object actual) {
		Assert.assertNull(actual);
	}
	
	public static void assertNull(final @Nullable Object actual, final @Nullable String message) {
		Assert.assertNull(message, actual);
	}
	
	public static void assertSame(final @Nullable Object expected, final @Nullable Object actual) {
		Assert.assertSame(expected, actual);
	}
	
	public static void assertSame(
			final @Nullable Object expected, final @Nullable Object actual,
			final @Nullable String message
	) {
		Assert.assertSame(message, expected, actual);
	}
	
	public static void assertTrue(final boolean condition) {
		Assert.assertTrue(condition);
	}
	
	public static void assertTrue(final boolean condition, final @Nullable String message) {
		Assert.assertTrue(message, condition);
	}
	
	public static void fail() {
		Assert.fail();
	}
	
	public static void fail(final @Nullable String message) {
		Assert.fail(message);
	}
}
