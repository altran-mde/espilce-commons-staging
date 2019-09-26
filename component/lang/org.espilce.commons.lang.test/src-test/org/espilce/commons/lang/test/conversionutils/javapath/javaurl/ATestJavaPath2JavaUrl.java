/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javapath.javaurl;

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.test.junit5.ConversionFunction;

public abstract class ATestJavaPath2JavaUrl {
	
	protected void assertConversionEquals(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws MalformedURLException {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL(expectedStr);
		assertEquals(expected, actual);
	}
	
	protected void assertConversionEquals_Exceptional(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws MalformedURLException {
		final Path input = Paths.get(inputStr);
		
		if (expectedStr != null) {
			final Object actual = fun.apply(input);
			final URL expected = new URL(expectedStr);
			assertEquals(expected, actual);
		} else {
			assertIllegalConversion(fun, input);
		}
	}
}
