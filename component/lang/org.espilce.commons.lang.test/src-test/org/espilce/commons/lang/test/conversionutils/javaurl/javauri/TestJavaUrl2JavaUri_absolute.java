/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javaurl.javauri;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.ConversionSource;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URL.class, returnType = URI.class)
public class TestJavaUrl2JavaUri_absolute extends ATestJavaUrl2JavaUri implements TestIAbsolute {
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}MyFile.ext, file:{}MyFile.ext"
	})
	public void absoluteFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}MyFile.ext,   file:{}MyFile.ext",
			"file:\\MyFile.ext,   %5CMyFile.ext",
			"file:\\\\MyFile.ext, %5C%5CMyFile.ext",
			"file:c:\\MyFile.ext, c%3A%5CMyFile.ext",
	})
	public void absoluteFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///folder///deep/myFile.ext//, file:{}myProject///folder///deep/myFile.ext//"
	})
	public void absoluteFileSlashesExcess_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///folder///deep/myFile.ext//,            file:{}myProject///folder///deep/myFile.ext//",
			"file:\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\,   %5CmyProject%5C%5C%5Cfolder%5C%5C%5Cdeep%5CmyFile.ext%5C%5C",
			"file:\\\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\, %5C%5CmyProject%5C%5C%5Cfolder%5C%5C%5Cdeep%5CmyFile.ext%5C%5C",
			"file:c:\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\, c%3A%5CmyProject%5C%5C%5Cfolder%5C%5C%5Cdeep%5CmyFile.ext%5C%5C",
	})
	public void absoluteFileSlashesExcess_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject/myFolder/, file:{}myProject/myFolder/"
	})
	public void absoluteFolderSlash_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject/myFolder/,     file:{}myProject/myFolder/",
			"file:\\myProject\\myFolder\\,   %5CmyProject%5CmyFolder%5C",
			"file:\\\\myProject\\myFolder\\, %5C%5CmyProject%5CmyFolder%5C",
			"file:c:\\myProject\\myFolder\\, c%3A%5CmyProject%5CmyFolder%5C",
	})
	public void absoluteFolderSlash_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///myFolder, file:{}myProject///myFolder"
	})
	public void absoluteFolderSlashesInbetween_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///myFolder,      file:{}myProject///myFolder",
			"file:\\myProject\\\\\\myFolder,   %5CmyProject%5C%5C%5CmyFolder",
			"file:\\\\myProject\\\\\\myFolder, %5C%5CmyProject%5C%5C%5CmyFolder",
			"file:c:\\myProject\\\\\\myFolder, c%3A%5CmyProject%5C%5C%5CmyFolder",
	})
	public void absoluteFolderSlashesInbetween_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///myFolder?query#fragment, file:{}myProject///myFolder?query#fragment"
	})
	public void absoluteFragmentQuery_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///myFolder?query#fragment,      file:{}myProject///myFolder?query#fragment",
			"file:\\myProject\\\\\\myFolder?query#fragment,   %5CmyProject%5C%5C%5CmyFolder?query#fragment",
			"file:\\\\myProject\\\\\\myFolder?query#fragment, %5C%5CmyProject%5C%5C%5CmyFolder?query#fragment",
			"file:c:\\myProject\\\\\\myFolder?query#fragment, c%3A%5CmyProject%5C%5C%5CmyFolder?query#fragment",
	})
	public void absoluteFragmentQuery_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}some/path/MyFile.ext, file:{}some/path/MyFile.ext"
	})
	public void absoluteNestedFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}some/path/MyFile.ext,     file:{}some/path/MyFile.ext",
			"file:\\some\\path\\MyFile.ext,   %5Csome%5Cpath%5CMyFile.ext",
			"file:\\\\some\\path\\MyFile.ext, %5C%5Csome%5Cpath%5CMyFile.ext",
			"file:c:\\some\\path\\MyFile.ext, c%3A%5Csome%5Cpath%5CMyFile.ext",
	})
	public void absoluteNestedFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}resource/..////, file:{}resource/..////"
	})
	public void absolutePath_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}resource/..////,        file:{}resource/..////",
			"file:\\resource\\..\\\\\\\\,   %5Cresource%5C..%5C%5C%5C%5C",
			"file:\\\\resource\\..\\\\\\\\, %5C%5Cresource%5C..%5C%5C%5C%5C",
			"file:c:\\resource\\..\\\\\\\\, c%3A%5Cresource%5C..%5C%5C%5C%5C",
	})
	public void absolutePath_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject/myFolder%23query, file:{}myProject/myFolder%23query"
	})
	public void absolutePseudoFragment_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject/myFolder%23query,    file:{}myProject/myFolder%23query",
			"file:\\myProject\\myFolder%23query,   %5CmyProject%5CmyFolder%23query",
			"file:\\\\myProject\\myFolder%23query, %5C%5CmyProject%5CmyFolder%23query",
			"file:c:\\myProject\\myFolder%23query, c%3A%5CmyProject%5CmyFolder%23query",
	})
	public void absolutePseudoFragment_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}, file:{}"
	})
	public void root_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		if (inputStr.endsWith("//") || inputStr.endsWith("\\\\")) {
			assertThrows(URISyntaxException.class, () -> new URI(inputStr));
		} else {
			assertConversionEquals(fun, inputStr, expectedStr);
		}
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{},   file:{}",
			"file://,   file:",
			"file:\\,   %5C",
			"file:\\\\, %5C%5C",
			"file:c:\\, c%3A%5C",
	})
	public void root_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final URL input = new URL(inputStr);
		URI expected;
		if ("file:".equals(expectedStr)) {
			expected = new URI(null, "", null);
		} else {
			expected = new URI(expectedStr);
		}
		assertConversionEquals(fun, input, expected);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"file:/{}some/path/MyFile.ext, file:/{}some/path/MyFile.ext"
	})
	public void absoluteWindowsPathSingleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"file:/{}some/path/MyFile.ext,      file:/{}some/path/MyFile.ext",
			"file:\\\\some\\path\\MyFile.ext,   %5C%5Csome%5Cpath%5CMyFile.ext",
			"file:\\\\\\some\\path\\MyFile.ext, %5C%5C%5Csome%5Cpath%5CMyFile.ext",
			"file:\\c:\\some\\path\\MyFile.ext, %5Cc%3A%5Csome%5Cpath%5CMyFile.ext",
	})
	public void absoluteWindowsPathSingleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"file://{}some/path/MyFile.ext, file://{}some/path/MyFile.ext"
	})
	public void absoluteWindowsPathDoubleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"file://{}some/path/MyFile.ext,       file://{}some/path/MyFile.ext",
			"file:\\\\\\some\\path\\MyFile.ext,   %5C%5C%5Csome%5Cpath%5CMyFile.ext",
			"file:\\\\\\\\some\\path\\MyFile.ext, %5C%5C%5C%5Csome%5Cpath%5CMyFile.ext",
			"file:\\\\c:\\some\\path\\MyFile.ext, %5C%5Cc%3A%5Csome%5Cpath%5CMyFile.ext",
	})
	public void absoluteWindowsPathDoubleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"file:///{}some/path/MyFile.ext, file:///{}some/path/MyFile.ext"
	})
	public void absoluteWindowsPathTripleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"file:///{}some/path/MyFile.ext,        file:///{}some/path/MyFile.ext",
			"file:\\\\\\\\some\\path\\MyFile.ext,   %5C%5C%5C%5Csome%5Cpath%5CMyFile.ext",
			"file:\\\\\\\\\\some\\path\\MyFile.ext, %5C%5C%5C%5C%5Csome%5Cpath%5CMyFile.ext",
			"file:\\\\\\c:\\some\\path\\MyFile.ext, %5C%5C%5Cc%3A%5Csome%5Cpath%5CMyFile.ext",
	})
	public void absoluteWindowsPathTripleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
}