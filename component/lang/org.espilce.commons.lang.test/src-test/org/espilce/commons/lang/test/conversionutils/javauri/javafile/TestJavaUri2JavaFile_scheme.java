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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIScheme;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.TestConversion;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URI.class, returnType = File.class)
public class TestJavaUri2JavaFile_scheme extends ATestJavaUri2JavaFile implements TestIScheme {
	
	@Override
	@TestConversion(value = "file:resource/...////", backslash = false)
	public void relativeUri(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "resource/.../");
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
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestOnUnix
	@TestConversion(value = " ", backslash = false)
	public void blankWithScheme_unix(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI("file", inputStr, null);
		assertConversionEquals(fun, input, " ");
	}
	
	@Override
	@TestConversion(value = "file:/myProject///myFolder#fragment", backslash = false)
	public void fragment(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(value = "file:/myProject///myFolder?query#fragment", backslash = false)
	public void fragmentQuery(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(value = "file:fasfasdf", backslash = false)
	public void inputNoSlashes(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "fasfasdf");
	}
	
	@Override
	@TestConversion(value = "https://example.com/MyFile.ext", backslash = false)
	public void otherSchema(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(value = "file:/myProject///myFolder?query", backslash = false)
	public void query(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
}
