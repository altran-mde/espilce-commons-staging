/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.testsupport;

import static org.espilce.commons.assertion.Assertion.assertEquals;
import static org.espilce.commons.assertion.Assertion.assertFalse;
import static org.espilce.commons.assertion.Assertion.assertNotNull;
import static org.espilce.commons.assertion.Assertion.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.annotation.Nullable;

public class AssertFile {
	@SuppressWarnings("null")
	public static void assertDoesntExist(final @Nullable String message, final @Nullable File file) {
		assertNotNull(file, message);
		assertFalse(file.exists(), message);
	}
	
	public static void assertDoesntExist(final @Nullable File file) {
		assertDoesntExist("exists: " + file, file);
	}
	
	@SuppressWarnings("null")
	public static void assertExists(final @Nullable String message, final @Nullable File file) {
		assertNotNull(file, message);
		assertTrue(file.exists(), message);
	}
	
	public static void assertExists(final @Nullable File file) {
		assertExists("exists: " + file, file);
	}
	
	@SuppressWarnings("null")
	public static void assertIsFile(final @Nullable String message, final @Nullable File file) {
		assertNotNull(file, message);
		assertTrue(file.isFile(), message);
	}
	
	public static void assertIsFile(final @Nullable File file) {
		assertIsFile("isFile: " + file, file);
	}
	
	@SuppressWarnings("null")
	public static void assertIsDirectory(final @Nullable String message, final @Nullable File file) {
		assertNotNull(file, message);
		assertTrue(file.isDirectory(), message);
	}
	
	public static void assertIsDirectory(final @Nullable File file) {
		assertIsDirectory("isDirectory: " + file, file);
	}
	
	@SuppressWarnings("null")
	public static void assertCanRead(final @Nullable String message, final @Nullable File file) {
		assertNotNull(file, message);
		assertTrue(file.canRead(), message);
	}
	
	public static void assertCanRead(final @Nullable File file) {
		assertCanRead("canRead: " + file, file);
	}
	
	@SuppressWarnings("null")
	public static void assertCanWrite(final @Nullable String message, final @Nullable File file) {
		assertNotNull(file, message);
		assertTrue(file.canWrite(), message);
	}
	
	public static void assertCanWrite(final @Nullable File file) {
		assertCanWrite("canWrite: " + file, file);
	}
	
	@SuppressWarnings("null")
	public static void assertCanExectue(final @Nullable String message, final @Nullable File file) {
		assertNotNull(file, message);
		assertTrue(file.canExecute(), message);
	}
	
	public static void assertCanExecute(final @Nullable File file) {
		assertCanExectue("canExecute: " + file, file);
	}
	
	public static void assertContentEquals(
			final @Nullable String message, final @Nullable File expected,
			final @Nullable File actual
	) {
		assertCanRead(message, expected);
		assertCanRead(message, actual);
		
		try {
			final String expectedContent = FileUtils.readFileToString(expected);
			final String actualContent = FileUtils.readFileToString(actual);
			
			assertEquals(expectedContent, actualContent, message);
		} catch (final IOException e) {
			throw new AssertionError(message, e);
		}
	}
	
	public static void assertContentEquals(final @Nullable File expected, final @Nullable File actual) {
		assertContentEquals("difference in " + actual, expected, actual);
	}
	
	public static void assertContentEqualsNormalizedNewline(
			final @Nullable String message,
			final @Nullable File expected,
			final @Nullable File actual
	) {
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
	
	public static void assertContentEqualsNormalizedNewline(
			final @Nullable File expected,
			final @Nullable File actual
	) {
		assertContentEqualsNormalizedNewline("difference in " + actual, expected, actual);
	}
}
