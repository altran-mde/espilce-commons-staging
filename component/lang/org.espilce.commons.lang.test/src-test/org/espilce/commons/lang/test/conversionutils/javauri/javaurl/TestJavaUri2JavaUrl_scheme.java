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

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIScheme;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.TestConversion;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URI.class, returnType = URL.class)
public class TestJavaUri2JavaUrl_scheme extends ATestJavaUri2JavaUrl implements TestIScheme {
	
	@Override
	@TestConversion(value = "mailto:resource/...////", backslash = false)
	public void relativeUri(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "mailto:resource/...////");
	}
	
	@Override
	@TestConversion(value = "", backslash = false)
	public void emptyWithScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		assertThrows(URISyntaxException.class, () -> new URI("file", inputStr, null));
	}
	
	@Override
	@TestOnWindows
	@TestConversion(value = " ", backslash = false)
	public void blankWithScheme_win(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI("file", inputStr, null);
		assertConversionEquals(fun, input, "file:%20");
	}
	
	@Override
	@TestOnUnix
	@TestConversion(value = " ", backslash = false)
	public void blankWithScheme_unix(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI("file", inputStr, null);
		assertConversionEquals(fun, input, "file:%20");
	}
	
	@Override
	@TestConversion(value = "http:/myProject///myFolder#fragment", backslash = false)
	public void fragment(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "http:/myProject///myFolder#fragment");
	}
	
	@Override
	@TestConversion(value = "http:/myProject///myFolder?query#fragment", backslash = false)
	public void fragmentQuery(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "http:/myProject///myFolder?query#fragment");
	}
	
	@Override
	@TestConversion(value = "file:fasfasdf", backslash = false)
	public void inputNoSlashes(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:fasfasdf");
	}
	
	@Override
	@TestConversion(value = "https://example.com/MyFile.ext", backslash = false)
	public void otherSchema(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "https://example.com/MyFile.ext");
	}
	
	@Override
	@TestConversion(value = "file:/myProject///myFolder?query", backslash = false)
	public void query(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:/myProject///myFolder?query");
	}
}
