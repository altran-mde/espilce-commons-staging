/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri.javapath;

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIJavaUri;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.ConversionSource;
import org.espilce.commons.lang.test.junit5.TestConversion;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URI.class, returnType = Path.class)
public class TestJavaUri2JavaPath_javaUri extends ATestJavaUri2JavaPath implements TestIJavaUri {
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"{}some/path/MyFile.ext, {}some/path/MyFile.ext",
			"c:/"
	})
	public void absoluteNestedFileNoScheme_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals_Exceptional(fun, input, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}some/path/MyFile.ext, {}some/path/MyFile.ext",
	}, backslash = false)
	public void absoluteNestedFileNoScheme_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		final URI input = new URI(inputStr.replace(":", "%3A"));
		assertConversionEquals(fun, input, expectedStr);
	}
	
	@Override
	@TestConversion("some/path/MyFile.ext")
	public void relativeNestedFileNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, "some/path/MyFile.ext");
	}
	
	@Override
	@TestConversion(".")
	public void currentNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, ".");
	}
	
	@Override
	@TestConversion("./some/path/MyFile.ext")
	public void currentRelativeNestedFileNoScheme(final ConversionFunction fun, final String inputStr)
			throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, "./some/path/MyFile.ext");
	}
	
	@Override
	@TestConversion("./")
	public void currentSlashNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, "./");
	}
	
	@Override
	@TestConversion(value = "", backslash = false)
	public void emptyNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, "");
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
		assertConversionEquals(fun, inputStr, "MyFile.ext");
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"{}, {}",
			"c:/",
			"//"
	})
	public void rootNoScheme_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		if (inputStr.endsWith("//")) {
			assertThrows(URISyntaxException.class, () -> new URI(inputStr));
		} else {
			final URI input = new URI(null, inputStr, null);
			assertConversionEquals_Exceptional(fun, input, expectedStr);
		}
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}, {}"
	}, replace = true)
	public void rootNoScheme_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final URI input;
		if (inputStr.startsWith("//")) {
			assertThrows(URISyntaxException.class, () -> new URI(null, inputStr, null));
		} else {
			if (inputStr.contains(":")) {
				final URI tmp = new URI("file:/" + inputStr.replace(":", "%3A").replace("\\", "%5C"));
				input = new URI("file:/").relativize(tmp);
			} else {
				input = new URI(null, null, inputStr, null);
			}
			assertConversionEquals(fun, input, expectedStr);
		}
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"{}, {}",
			"//"
	})
	public void rootScheme_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		if (inputStr.endsWith("//")) {
			assertThrows(URISyntaxException.class, () -> new URI(inputStr));
		} else {
			final URI input = new URI("file", inputStr, null);
			assertConversionEquals_Exceptional(fun, input, expectedStr);
		}
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}, {}"
	}, replace = true)
	public void rootScheme_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		if (inputStr.endsWith("//")) {
			assertThrows(URISyntaxException.class, () -> new URI(inputStr));
		} else {
			final URI input = new URI("file", inputStr, null);
			assertConversionEquals(fun, input, expectedStr);
		}
	}
	
	@Override
	@TestConversion("../resource/////")
	public void startRelativePathNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, "../resource/");
	}
}
