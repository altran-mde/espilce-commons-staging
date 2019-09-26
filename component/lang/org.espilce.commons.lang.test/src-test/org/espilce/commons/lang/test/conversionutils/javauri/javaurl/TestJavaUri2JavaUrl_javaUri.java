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

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIJavaUri;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.ConversionSource;
import org.espilce.commons.lang.test.junit5.TestConversion;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URI.class, returnType = URL.class)
public class TestJavaUri2JavaUrl_javaUri extends ATestJavaUri2JavaUrl implements TestIJavaUri {
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	}, backslash = false)
	public void absoluteNestedFileNoScheme_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	}, backslash = false)
	public void absoluteNestedFileNoScheme_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion("some/path/MyFile.ext")
	public void relativeNestedFileNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(".")
	public void currentNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion("./some/path/MyFile.ext")
	public void currentRelativeNestedFileNoScheme(final ConversionFunction fun, final String inputStr)
			throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion("./")
	public void currentSlashNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(value = "", backslash = false)
	public void emptyNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(value = "!@#fasfasdf", backslash = false)
	public void inputBroken(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(value = "MyFile.ext", backslash = false)
	public void noScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"{},   {}",
	})
	public void rootNoScheme_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		if (inputStr.endsWith("//")) {
			assertThrows(URISyntaxException.class, () -> new URI(inputStr));
		} else {
			final URI input = new URI(null, inputStr, null);
			assertIllegalConversion(fun, input);
		}
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}, {}"
	})
	public void rootNoScheme_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"{},   {}",
	}, backslash = false)
	public void rootScheme_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		if (inputStr.endsWith("//")) {
			assertThrows(URISyntaxException.class, () -> new URI(inputStr));
		} else {
			assertConversionEquals(fun, inputStr);
		}
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}, {}"
	}, backslash = false)
	public void rootScheme_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr);
	}
	
	@Override
	@TestConversion("../resource/////")
	public void startRelativePathNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertIllegalConversion(fun, input);
	}
}
