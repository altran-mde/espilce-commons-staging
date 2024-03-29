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

import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.text.StringUtils2;

public class Assert2 {
	public static void assertEqualsNormalizedNewline(
			final @Nullable String message,
			final @Nullable Object expected,
			final @Nullable Object actual
	) {
		if (expected == null || actual == null) {
			assertEquals(expected, actual, message);
		} else {
			assertEquals(
					StringUtils2.normalizeNewline(expected.toString()),
					StringUtils2.normalizeNewline(actual.toString()),
					message
			);
		}
	}
	
	public static void assertEqualsNormalizedNewline(final @Nullable Object expected, final @Nullable Object actual) {
		assertEqualsNormalizedNewline(null, expected, actual);
	}
}
