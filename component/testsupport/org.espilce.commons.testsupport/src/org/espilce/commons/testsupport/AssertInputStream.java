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
import static org.espilce.commons.assertion.Assertion.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.annotation.Nullable;

public class AssertInputStream {
	
	public void assertContentEquals(
			final @Nullable String message, final @Nullable InputStream expected,
			final @Nullable InputStream actual
	) {
		assertNotNull(expected, message);
		assertNotNull(actual, message);
		
		try {
			final String expectedContent = IOUtils.toString(expected);
			final String actualContent = IOUtils.toString(actual);
			
			assertEquals(expectedContent, actualContent, message);
		} catch (final IOException e) {
			throw new AssertionError(message, e);
		}
	}
	
	public void assertContentEquals(final @Nullable InputStream expected, final @Nullable InputStream actual) {
		assertContentEquals("difference in " + actual, expected, actual);
	}
	
	public void assertContentEqualsNormalizedNewline(
			final @Nullable String message,
			final @Nullable InputStream expected,
			final @Nullable InputStream actual
	) {
		assertNotNull(expected, message);
		assertNotNull(actual, message);
		
		try {
			final String expectedContent = IOUtils.toString(expected);
			final String actualContent = IOUtils.toString(actual);
			
			Assert2.assertEqualsNormalizedNewline(message, expectedContent, actualContent);
		} catch (final IOException e) {
			throw new AssertionError(message, e);
		}
	}
	
	public void assertContentEqualsNormalizedNewline(
			final @Nullable InputStream expected,
			final @Nullable InputStream actual
	) {
		assertContentEqualsNormalizedNewline("difference in " + actual, expected, actual);
	}
}
