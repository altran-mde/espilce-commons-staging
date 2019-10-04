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

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.ConversionSource;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = Path.class, returnType = URL.class)
public class TestJavaPath2JavaUrl_absolute extends ATestJavaPath2JavaUrl implements TestIAbsolute {
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}MyFile.ext, file:/c:/MyFile.ext",
			"/MyFile.ext,  file:/MyFile.ext",
			"//MyFile.ext, file://MyFile.ext",
	})
	public void absoluteFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		if (inputStr.startsWith("//") || inputStr.startsWith("\\\\")) {
			assertThrows(InvalidPathException.class, () -> Paths.get(inputStr));
		} else {
			assertConversionEquals(fun, inputStr, expectedStr);
		}
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}MyFile.ext,   file:/MyFile.ext",
			"c:/MyFile.ext,  file:c%3A/MyFile.ext",
			"\\MyFile.ext,   file:%5CMyFile.ext",
			"c:\\MyFile.ext, file:c%3A%5CMyFile.ext",
			"\\\\MyFile.ext, file:%5C%5CMyFile.ext",
	})
	public void absoluteFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject///folder///deep/myFile.ext//, file:/{}myProject/folder/deep/myFile.ext",
			"/myProject///folder///deep/myFile.ext//,  file:/myProject/folder/deep/myFile.ext",
			"//myProject///folder///deep/myFile.ext//, file://myProject/folder/deep/myFile.ext",
	})
	public void absoluteFileSlashesExcess_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}myProject///folder///deep/myFile.ext//,            file:/myProject/folder/deep/myFile.ext",
			"c:/myProject///folder///deep/myFile.ext//,           file:c%3A/myProject/folder/deep/myFile.ext",
			"\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\,   file:%5CmyProject%5C%5C%5Cfolder%5C%5C%5Cdeep%5CmyFile.ext%5C%5C",
			"c:\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\, file:c%3A%5CmyProject%5C%5C%5Cfolder%5C%5C%5Cdeep%5CmyFile.ext%5C%5C",
			"\\\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\, file:%5C%5CmyProject%5C%5C%5Cfolder%5C%5C%5Cdeep%5CmyFile.ext%5C%5C",
	})
	public void absoluteFileSlashesExcess_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject/myFolder/, file:/{}myProject/myFolder",
			"/myProject/myFolder/,  file:/myProject/myFolder",
			"//myProject/myFolder/, file://myProject/myFolder/",
	})
	public void absoluteFolderSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}myProject/myFolder/,     file:/myProject/myFolder",
			"c:/myProject/myFolder/,    file:c%3A/myProject/myFolder",
			"\\myProject\\myFolder\\,   file:%5CmyProject%5CmyFolder%5C",
			"c:\\myProject\\myFolder\\, file:c%3A%5CmyProject%5CmyFolder%5C",
			"\\\\myProject\\myFolder\\, file:%5C%5CmyProject%5CmyFolder%5C",
	})
	public void absoluteFolderSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject///myFolder, file:/{}myProject/myFolder",
			"/myProject///myFolder,  file:/myProject/myFolder",
			"//myProject///myFolder, file://myProject/myFolder/",
	})
	public void absoluteFolderSlashesInbetween_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}myProject///myFolder,      file:/myProject/myFolder",
			"c:/myProject///myFolder,     file:c%3A/myProject/myFolder",
			"\\myProject\\\\\\myFolder,   file:%5CmyProject%5C%5C%5CmyFolder",
			"c:\\myProject\\\\\\myFolder, file:c%3A%5CmyProject%5C%5C%5CmyFolder",
			"\\\\myProject\\\\\\myFolder, file:%5C%5CmyProject%5C%5C%5CmyFolder",
	})
	public void absoluteFolderSlashesInbetween_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject/myFolder?query#fragment, file:/{}myProject/myFolder%3Fquery%23fragment",
			"/myProject/myFolder?query#fragment,  file:/myProject/myFolder%3Fquery%23fragment",
			"//myProject/myFolder?query#fragment, file://myProject/myFolder%3Fquery%23fragment",
	})
	public void absoluteFragmentQuery_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertThrows(InvalidPathException.class, () -> Paths.get(inputStr));
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}myProject/myFolder?query#fragment,    file:/myProject/myFolder%3Fquery%23fragment",
			"c:/myProject/myFolder?query#fragment,   file:c%3A/myProject/myFolder%3Fquery%23fragment",
			"\\myProject\\myFolder?query#fragment,   file:%5CmyProject%5CmyFolder%3Fquery%23fragment",
			"c:\\myProject\\myFolder?query#fragment, file:c%3A%5CmyProject%5CmyFolder%3Fquery%23fragment",
			"\\\\myProject\\myFolder?query#fragment, file:%5C%5CmyProject%5CmyFolder%3Fquery%23fragment",
	})
	public void absoluteFragmentQuery_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}some/path/MyFile.ext, file:/{}some/path/MyFile.ext",
			"/some/path/MyFile.ext,  file:/some/path/MyFile.ext",
			"//some/path/MyFile.ext, file://some/path/MyFile.ext",
	})
	public void absoluteNestedFile_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}some/path/MyFile.ext,     file:/some/path/MyFile.ext",
			"c:/some/path/MyFile.ext,    file:c%3A/some/path/MyFile.ext",
			"\\some\\path\\MyFile.ext,   file:%5Csome%5Cpath%5CMyFile.ext",
			"c:\\some\\path\\MyFile.ext, file:c%3A%5Csome%5Cpath%5CMyFile.ext",
			"\\\\some\\path\\MyFile.ext, file:%5C%5Csome%5Cpath%5CMyFile.ext",
	})
	public void absoluteNestedFile_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}resource/..////, file:/{}resource/../",
			"/resource/..////,  file:/resource/..",
			"//resource/..////, file://resource/../"
	})
	public void absolutePath_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}resource/..////,        file:/resource/..",
			"c:/resource/..////,       file:c%3A/resource/..",
			"\\resource\\..\\\\\\\\,   file:%5Cresource%5C..%5C%5C%5C%5C",
			"c:\\resource\\..\\\\\\\\, file:c%3A%5Cresource%5C..%5C%5C%5C%5C",
			"\\\\resource\\..\\\\\\\\, file:%5C%5Cresource%5C..%5C%5C%5C%5C",
	
	})
	public void absolutePath_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject/myFolder#query, file:/{}myProject/myFolder%23query",
			"/myProject/myFolder#query,  file:/myProject/myFolder%23query",
			"//myProject/myFolder#query, file://myProject/myFolder%23query/",
	})
	public void absolutePseudoFragment_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}myProject/myFolder#query,    file:/myProject/myFolder%23query",
			"c:/myProject/myFolder#query,   file:c%3A/myProject/myFolder%23query",
			"\\myProject\\myFolder#query,   file:%5CmyProject%5CmyFolder%23query",
			"c:\\myProject\\myFolder#query, file:c%3A%5CmyProject%5CmyFolder%23query",
			"\\\\myProject\\myFolder#query, file:%5C%5CmyProject%5CmyFolder%23query",
	})
	public void absolutePseudoFragment_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}, file:/{}",
			"/,  file:/",
			"//, file:////",
	})
	public void root_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		if (inputStr.startsWith("//") || inputStr.startsWith("\\\\")) {
			assertThrows(InvalidPathException.class, () -> Paths.get(inputStr));
		} else {
			assertConversionEquals(fun, inputStr, expectedStr);
		}
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{},   file:/",
			"c:/,  file:c%3A",
			"\\,   file:%5C",
			"c:\\, file:c%3A%5C",
			"\\\\, file:%5C%5C",
	})
	public void root_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}some/path/MyFile.ext, file:/{}some/path/MyFile.ext",
			"//some/path/MyFile.ext, file://some/path/MyFile.ext",
			"/some/path/MyFile.ext,  file:/some/path/MyFile.ext",
	})
	public void absoluteWindowsPathSingleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"/{}some/path/MyFile.ext,      file:/some/path/MyFile.ext",
			"/c:/some/path/MyFile.ext,     file:/c:/some/path/MyFile.ext",
			"\\\\some\\path\\MyFile.ext,   file:%5C%5Csome%5Cpath%5CMyFile.ext",
			"\\c:\\some\\path\\MyFile.ext, file:%5Cc%3A%5Csome%5Cpath%5CMyFile.ext",
			"\\\\\\some\\path\\MyFile.ext, file:%5C%5C%5Csome%5Cpath%5CMyFile.ext",
	})
	public void absoluteWindowsPathSingleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"/{}some/path/MyFile.ext, file:/{}some/path/MyFile.ext",
			"///some/path/MyFile.ext, file://some/path/MyFile.ext",
			"//some/path/MyFile.ext,  file://some/path/MyFile.ext",
	})
	public void absoluteWindowsPathDoubleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		if (inputStr.charAt(2) == ':') {
			assertThrows(InvalidPathException.class, () -> Paths.get(inputStr));
		} else {
			assertConversionEquals(fun, inputStr, expectedStr);
		}
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"//{}some/path/MyFile.ext,       file:/some/path/MyFile.ext",
			"//c:/some/path/MyFile.ext,      file:/c:/some/path/MyFile.ext",
			"\\\\\\some\\path\\MyFile.ext,   file:%5C%5C%5Csome%5Cpath%5CMyFile.ext",
			"\\\\c:\\some\\path\\MyFile.ext, file:%5C%5Cc%3A%5Csome%5Cpath%5CMyFile.ext",
			"\\\\\\\\some\\path\\MyFile.ext, file:%5C%5C%5C%5Csome%5Cpath%5CMyFile.ext",
	})
	public void absoluteWindowsPathDoubleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"///{}some/path/MyFile.ext,        file:/{}some/path/MyFile.ext",
			"////some/path/MyFile.ext,         file://some/path/MyFile.ext",
			"/////some/path/MyFile.ext,        file://some/path/MyFile.ext",
			"\\\\\\\\\\some\\path\\MyFile.ext, file://some/path/MyFile.ext",
	})
	public void absoluteWindowsPathTripleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		if (inputStr.charAt(4) == ':') {
			assertThrows(InvalidPathException.class, () -> Paths.get(inputStr));
		} else {
			assertConversionEquals(fun, inputStr, expectedStr);
		}
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"///{}some/path/MyFile.ext,        file:/some/path/MyFile.ext",
			"///c:/some/path/MyFile.ext,       file:/c:/some/path/MyFile.ext",
			"\\\\\\\\some\\path\\MyFile.ext,   file:%5C%5C%5C%5Csome%5Cpath%5CMyFile.ext",
			"\\\\\\c:\\some\\path\\MyFile.ext, file:%5C%5C%5Cc%3A%5Csome%5Cpath%5CMyFile.ext",
			"\\\\\\\\\\some\\path\\MyFile.ext, file:%5C%5C%5C%5C%5Csome%5Cpath%5CMyFile.ext",
	})
	public void absoluteWindowsPathTripleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
}
