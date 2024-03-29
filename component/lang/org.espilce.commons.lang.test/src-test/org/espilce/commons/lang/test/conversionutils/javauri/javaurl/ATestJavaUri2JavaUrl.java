/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri.javaurl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.espilce.commons.lang.test.junit5.ConversionFunction;

public abstract class ATestJavaUri2JavaUrl {
	
	protected void assertConversionEquals(final ConversionFunction fun, final String inputStr)
			throws URISyntaxException, MalformedURLException {
		final URI input = new URI("file:" + inputStr);
		final URL expected = new URL("file:" + inputStr);
		assertConversionEquals(fun, input, expected);
	}
	
	protected void assertConversionEquals(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws URISyntaxException, MalformedURLException {
		final URI input = new URI(inputStr);
		assertConversionEquals(fun, input, expectedStr);
	}
	
	protected void assertConversionEquals(final ConversionFunction fun, final URI input, final String expectedStr)
			throws MalformedURLException {
		final Object actual = fun.apply(input);
		final URL expected = new URL(expectedStr);
		assertEquals(expected, actual);
	}
	
	protected void assertConversionEquals(final ConversionFunction fun, final URI input, final URL expected) {
		final Object actual = fun.apply(input);
		assertEquals(expected, actual);
	}
}
