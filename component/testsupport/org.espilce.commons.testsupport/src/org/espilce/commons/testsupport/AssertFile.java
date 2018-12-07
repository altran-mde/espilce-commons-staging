package org.espilce.commons.testsupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.annotation.Nullable;

public class AssertFile {
	public void assertDoesntExist(final @Nullable String message, final @Nullable File file) {
		assertNotNull(message, file);
		assertFalse(message, file.exists());
	}
	
	public void assertDoesntExist(final @Nullable File file) {
		assertDoesntExist("exists: " + file, file);
	}
	
	public void assertIsFile(final @Nullable String message, final @Nullable File file) {
		assertNotNull(message, file);
		assertTrue(message, file.isFile());
	}
	
	public void assertIsFile(final @Nullable File file) {
		assertIsFile("isFile: " + file, file);
	}
	
	public void assertIsDirectory(final @Nullable String message, final @Nullable File file) {
		assertNotNull(message, file);
		assertTrue(message, file.isDirectory());
	}
	
	public void assertIsDirectory(final @Nullable File file) {
		assertIsDirectory("isDirectory: " + file, file);
	}
	
	public void assertCanRead(final @Nullable String message, final @Nullable File file) {
		assertNotNull(message, file);
		assertTrue(message, file.canRead());
	}
	
	public void assertCanRead(final @Nullable File file) {
		assertCanRead("canRead: " + file, file);
	}
	
	public void assertCanWrite(final @Nullable String message, final @Nullable File file) {
		assertNotNull(message, file);
		assertTrue(message, file.canWrite());
	}
	
	public void assertCanWrite(final @Nullable File file) {
		assertCanWrite("canWrite: " + file, file);
	}
	
	public void assertCanExectue(final @Nullable String message, final @Nullable File file) {
		assertNotNull(message, file);
		assertTrue(message, file.canExecute());
	}
	
	public void assertCanExecute(final @Nullable File file) {
		assertCanExectue("canExecute: " + file, file);
	}
	
	public void assertContentEquals(final @Nullable String message, final @Nullable File expected,
			final @Nullable File actual) {
		assertCanRead(message, expected);
		assertCanRead(message, actual);
		
		try {
			final String expectedContent = FileUtils.readFileToString(expected);
			final String actualContent = FileUtils.readFileToString(actual);
			
			assertEquals(message, expectedContent, actualContent);
		} catch (final IOException e) {
			throw new AssertionError(message, e);
		}
	}
	
	public void assertContentEquals(final @Nullable File expected, final @Nullable File actual) {
		assertContentEquals("difference in " + actual, expected, actual);
	}
	
	public void assertContentEqualsNormalizedNewline(final @Nullable String message, final @Nullable File expected,
			final @Nullable File actual) {
		assertCanRead(message, expected);
		assertCanRead(message, actual);
		
		try {
			final String expectedContent = FileUtils.readFileToString(expected);
			final String actualContent = FileUtils.readFileToString(actual);
			
			Assert2.assertEqualsNormalizedNewline(message, expectedContent, actualContent);
		} catch (final IOException e) {
			throw new AssertionError(message, e);
		}
	}
	
	public void assertContentEqualsNormalizedNewline(final @Nullable File expected, final @Nullable File actual) {
		assertContentEqualsNormalizedNewline("difference in " + actual, expected, actual);
	}
}
