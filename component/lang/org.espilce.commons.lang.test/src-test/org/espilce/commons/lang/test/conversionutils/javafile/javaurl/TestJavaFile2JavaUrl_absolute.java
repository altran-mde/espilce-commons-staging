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

import java.io.File;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.ConversionSource;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = File.class, returnType = URL.class)
public class TestJavaFile2JavaUrl_absolute extends ATestJavaFile2JavaUrl implements TestIAbsolute {
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}MyFile.ext, file:/c:/MyFile.ext",
			"/MyFile.ext,  /MyFile.ext",
			"//MyFile.ext, file:////MyFile.ext",
	})
	public void absoluteFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}MyFile.ext,   file:/MyFile.ext",
			"c:/MyFile.ext,  c:/MyFile.ext",
			"\\MyFile.ext,   %5cMyFile.ext",
			"c:\\MyFile.ext, c:%5cMyFile.ext",
			"\\\\MyFile.ext, %5c%5cMyFile.ext",
	})
	public void absoluteFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject///folder///deep/myFile.ext//, file:/{}myProject/folder/deep/myFile.ext",
			"/myProject///folder///deep/myFile.ext//,  /myProject/folder/deep/myFile.ext",
			"//myProject///folder///deep/myFile.ext//, file:////myProject/folder/deep/myFile.ext",
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
			"c:/myProject///folder///deep/myFile.ext//,           c:/myProject/folder/deep/myFile.ext",
			"\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\,   %5CmyProject%5C%5C%5Cfolder%5C%5C%5Cdeep%5CmyFile.ext%5C%5C",
			"c:\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\, c:%5CmyProject%5C%5C%5Cfolder%5C%5C%5Cdeep%5CmyFile.ext%5C%5C",
			"\\\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\, %5C%5CmyProject%5C%5C%5Cfolder%5C%5C%5Cdeep%5CmyFile.ext%5C%5C",
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
			"/myProject/myFolder/,  /myProject/myFolder",
			"//myProject/myFolder/, file:////myProject/myFolder",
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
			"c:/myProject/myFolder/,    c:/myProject/myFolder",
			"\\myProject\\myFolder\\,   %5CmyProject%5CmyFolder%5C",
			"c:\\myProject\\myFolder\\, c:%5CmyProject%5CmyFolder%5C",
			"\\\\myProject\\myFolder\\, %5C%5CmyProject%5CmyFolder%5C",
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
			"/myProject///myFolder,  /myProject/myFolder",
			"//myProject///myFolder, file:////myProject/myFolder",
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
			"c:/myProject///myFolder,     c:/myProject/myFolder",
			"\\myProject\\\\\\myFolder,   %5CmyProject%5C%5C%5CmyFolder",
			"c:\\myProject\\\\\\myFolder, c:%5CmyProject%5C%5C%5CmyFolder",
			"\\\\myProject\\\\\\myFolder, %5C%5CmyProject%5C%5C%5CmyFolder",
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
			"/myProject/myFolder?query#fragment,  /myProject/myFolder%3Fquery%23fragment",
			"//myProject/myFolder?query#fragment, file:////myProject/myFolder%3Fquery%23fragment",
	})
	public void absoluteFragmentQuery_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}myProject/myFolder?query#fragment,    file:/myProject/myFolder%3Fquery%23fragment",
			"c:/myProject/myFolder?query#fragment,   c:/myProject/myFolder%3Fquery%23fragment",
			"\\myProject\\myFolder?query#fragment,   %5CmyProject%5CmyFolder%3Fquery%23fragment",
			"c:\\myProject\\myFolder?query#fragment, c:%5CmyProject%5CmyFolder%3Fquery%23fragment",
			"\\\\myProject\\myFolder?query#fragment, %5C%5CmyProject%5CmyFolder%3Fquery%23fragment",
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
			"/some/path/MyFile.ext,  /some/path/MyFile.ext",
			"//some/path/MyFile.ext, file:////some/path/MyFile.ext",
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
			"c:/some/path/MyFile.ext,    c:/some/path/MyFile.ext",
			"\\some\\path\\MyFile.ext,   %5Csome%5Cpath%5CMyFile.ext",
			"c:\\some\\path\\MyFile.ext, c:%5Csome%5Cpath%5CMyFile.ext",
			"\\\\some\\path\\MyFile.ext, %5C%5Csome%5Cpath%5CMyFile.ext",
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
			"/resource/..////,  /resource/..",
			"//resource/..////, file:////resource/.."
	})
	public void absolutePath_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}resource/..////,        file:/resource/..",
			"c:/resource/..////,       c:/resource/..",
			"\\resource\\..\\\\\\\\,   %5Cresource%5C..%5C%5C%5C%5C",
			"c:\\resource\\..\\\\\\\\, c:%5Cresource%5C..%5C%5C%5C%5C",
			"\\\\resource\\..\\\\\\\\, %5C%5Cresource%5C..%5C%5C%5C%5C",
	
	})
	public void absolutePath_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject/myFolder#query, file:/{}myProject/myFolder%23query",
			"/myProject/myFolder#query,  /myProject/myFolder%23query",
			"//myProject/myFolder#query, file:////myProject/myFolder%23query",
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
			"c:/myProject/myFolder#query,   c:/myProject/myFolder%23query",
			"\\myProject\\myFolder#query,   %5CmyProject%5CmyFolder%23query",
			"c:\\myProject\\myFolder#query, c:%5CmyProject%5CmyFolder%23query",
			"\\\\myProject\\myFolder#query, %5C%5CmyProject%5CmyFolder%23query",
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
			"/,  /",
			"//, file:////",
	})
	public void root_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{},   file:/",
			"c:/,  c:/",
			"\\,   %5C",
			"c:\\, c:%5C",
			"\\\\, %5C%5C",
	})
	public void root_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"/{}some/path/MyFile.ext,    file:///{}some/path/MyFile.ext",
			"\\\\some\\path\\MyFile.ext, file:////some/path/MyFile.ext",
			"///some/path/MyFile.ext,    file:////some/path/MyFile.ext",
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
			"\\\\some\\path\\MyFile.ext,   %5C%5Csome%5Cpath%5CMyFile.ext",
			"\\c:\\some\\path\\MyFile.ext",
			"\\\\\\some\\path\\MyFile.ext, %5C%5C%5Csome%5Cpath%5CMyFile.ext",
	})
	public void absoluteWindowsPathSingleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals_Exceptional(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"//{}some/path/MyFile.ext,       file:///{}some/path/MyFile.ext",
			"\\\\\\some\\path\\MyFile.ext,   file:////some/path/MyFile.ext",
			"\\\\\\\\some\\path\\MyFile.ext, file:////some/path/MyFile.ext",
			"////some/path/MyFile.ext,       file:////some/path/MyFile.ext",
	})
	public void absoluteWindowsPathDoubleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"//{}some/path/MyFile.ext,       file:/some/path/MyFile.ext",
			"//c:/some/path/MyFile.ext,      file:/c:/some/path/MyFile.ext",
			"\\\\\\some\\path\\MyFile.ext,   %5C%5C%5Csome%5Cpath%5CMyFile.ext",
			"\\\\c:\\some\\path\\MyFile.ext",
			"\\\\\\\\some\\path\\MyFile.ext, %5C%5C%5C%5Csome%5Cpath%5CMyFile.ext",
	})
	public void absoluteWindowsPathDoubleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals_Exceptional(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"///{}some/path/MyFile.ext,        file:///{}some/path/MyFile.ext",
			"\\\\\\\\some\\path\\MyFile.ext,   file:////some/path/MyFile.ext",
			"\\\\\\\\\\some\\path\\MyFile.ext, file:////some/path/MyFile.ext",
			"/////some/path/MyFile.ext,        file:////some/path/MyFile.ext",
	})
	public void absoluteWindowsPathTripleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"///{}some/path/MyFile.ext,        file:/some/path/MyFile.ext",
			"///c:/some/path/MyFile.ext,       file:/c:/some/path/MyFile.ext",
			"\\\\\\\\some\\path\\MyFile.ext,   %5C%5C%5C%5Csome%5Cpath%5CMyFile.ext",
			"\\\\\\c:\\some\\path\\MyFile.ext",
			"\\\\\\\\\\some\\path\\MyFile.ext, %5C%5C%5C%5C%5Csome%5Cpath%5CMyFile.ext",
	})
	public void absoluteWindowsPathTripleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals_Exceptional(fun, inputStr, expectedStr);
	}
}
