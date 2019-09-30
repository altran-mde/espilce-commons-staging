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
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.ConversionSource;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URI.class, returnType = Path.class)
public class TestJavaUri2JavaPath_absolute extends ATestJavaUri2JavaPath implements TestIAbsolute {
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}MyFile.ext, {}MyFile.ext",
			"file://MyFile.ext"
	}, backslash = false)
	public void absoluteFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals_Exceptional(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}MyFile.ext, {}MyFile.ext",
			"file://MyFile.ext"
	}, backslash = false)
	public void absoluteFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals_Exceptional(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///folder///deep/myFile.ext//, {}myProject/folder/deep/myFile.ext"
	}, backslash = false)
	public void absoluteFileSlashesExcess_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///folder///deep/myFile.ext//, {}myProject/folder/deep/myFile.ext"
	}, backslash = false)
	public void absoluteFileSlashesExcess_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject/myFolder/, {}myProject/myFolder"
	}, backslash = false)
	public void absoluteFolderSlash_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject/myFolder/, {}myProject/myFolder"
	}, backslash = false)
	public void absoluteFolderSlash_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///myFolder, {}myProject/myFolder"
	}, backslash = false)
	public void absoluteFolderSlashesInbetween_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///myFolder, {}myProject/myFolder"
	}, backslash = false)
	public void absoluteFolderSlashesInbetween_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///myFolder?query#fragment, {}myProject///myFolder"
	}, backslash = false)
	public void absoluteFragmentQuery_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///myFolder?query#fragment, {}myProject///myFolder"
	}, backslash = false)
	public void absoluteFragmentQuery_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	}, backslash = false)
	public void absoluteNestedFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	}, backslash = false)
	public void absoluteNestedFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}resource/..////, {}resource/.."
	}, backslash = false)
	public void absolutePath_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}resource/..////, {}resource/.."
	}, backslash = false)
	public void absolutePath_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject/myFolder%23query, {}myProject/myFolder#query"
	}, backslash = false)
	public void absolutePseudoFragment_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject/myFolder%23query, {}myProject/myFolder#query"
	}, backslash = false)
	public void absolutePseudoFragment_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}, {}",
	}, backslash = false)
	public void root_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		if (inputStr.endsWith("//")) {
			assertThrows(URISyntaxException.class, () -> new URI(inputStr));
		} else {
			assertConversionEquals(fun, inputStr, expectedStr);
		}
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}, {}",
	}, backslash = false)
	public void root_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		if (inputStr.endsWith("//")) {
			assertThrows(URISyntaxException.class, () -> new URI(inputStr));
		} else {
			assertConversionEquals(fun, inputStr, expectedStr);
		}
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:/{}some/path/MyFile.ext, {}some/path/MyFile.ext",
			"file://some/path/MyFile.ext, //some/path/MyFile.ext",
			"file:///some/path/MyFile.ext, /some/path/MyFile.ext",
	}, backslash = false)
	public void absoluteWindowsPathSingleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:/{}some/path/MyFile.ext, {}some/path/MyFile.ext",
			"file://some/path/MyFile.ext, //some/path/MyFile.ext",
			"file:///some/path/MyFile.ext, /some/path/MyFile.ext",
	}, backslash = false)
	public void absoluteWindowsPathSingleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file://{}some/path/MyFile.ext, {}some/path/MyFile.ext",
			"file:///some/path/MyFile.ext, /some/path/MyFile.ext",
			"file:////some/path/MyFile.ext, //some/path/MyFile.ext",
	}, backslash = false)
	public void absoluteWindowsPathDoubleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file://{}some/path/MyFile.ext, {}some/path/MyFile.ext",
			"file:///some/path/MyFile.ext, /some/path/MyFile.ext",
			"file:////some/path/MyFile.ext, //some/path/MyFile.ext",
	}, backslash = false)
	public void absoluteWindowsPathDoubleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:///{}some/path/MyFile.ext, {}some/path/MyFile.ext",
			"file:////some/path/MyFile.ext, //some/path/MyFile.ext",
			"file://///some/path/MyFile.ext, //some/path/MyFile.ext",
	}, backslash = false)
	public void absoluteWindowsPathTripleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:///{}some/path/MyFile.ext, {}some/path/MyFile.ext",
			"file:////some/path/MyFile.ext, //some/path/MyFile.ext",
			"file://///some/path/MyFile.ext, //some/path/MyFile.ext",
	}, backslash = false)
	public void absoluteWindowsPathTripleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
}
