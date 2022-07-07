/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javafile.javaurl;

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.espilce.commons.lang.test.junit5.ConversionFunction;

public abstract class ATestJavaFile2JavaUrl {
	protected void assertConversionEquals(final ConversionFunction fun, final String inputStr)
			throws MalformedURLException {
		final URL expected = new URL("file:" + inputStr);
		assertConversionEquals(fun, inputStr, expected);
		
	}
	
	protected void assertConversionEquals(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws MalformedURLException {
		final URL expected = new URL(expectedStr);
		assertConversionEquals(fun, inputStr, expected);
	}
	
	protected void assertConversionEquals(final ConversionFunction fun, final String inputStr, final URL expected) {
		final File input = new File(inputStr);
		final Object actual = fun.apply(input);
		assertEquals(expected, actual);
	}
	
	protected void assertConversionEquals_Exceptional(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws MalformedURLException {
		final File input = new File(inputStr);
		
		if (expectedStr != null) {
			final Object actual = fun.apply(input);
			final URL expected = new URL(expectedStr);
			assertEquals(expected, actual);
		} else {
			assertIllegalConversion(fun, input);
		}
	}
}
