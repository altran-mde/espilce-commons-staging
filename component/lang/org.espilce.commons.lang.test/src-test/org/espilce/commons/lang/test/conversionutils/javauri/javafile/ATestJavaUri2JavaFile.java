/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri.javafile;

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.espilce.commons.lang.test.junit5.ConversionFunction;

public abstract class ATestJavaUri2JavaFile {
	
	protected void assertConversionEquals(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws URISyntaxException {
		final URI input = new URI(inputStr);
		assertConversionEquals(fun, input, expectedStr);
	}
	
	protected void assertConversionEquals(final ConversionFunction fun, final URI input, final String expectedStr) {
		final Object actual = fun.apply(input);
		final File expected = new File(expectedStr);
		assertEquals(expected, actual);
	}
	
	protected void assertConversionEquals_Exceptional(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws URISyntaxException {
		final URI input = new URI(inputStr);
		
		if (expectedStr != null) {
			final Object actual = fun.apply(input);
			final File expected = new File(expectedStr);
			assertEquals(expected, actual);
		} else {
			assertIllegalConversion(fun, input);
		}
	}
}
