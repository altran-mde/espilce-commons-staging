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
			"/MyFile.ext,  file:/MyFile.ext",
			"//MyFile.ext, file:////MyFile.ext",
	})
	public void absoluteFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}MyFile.ext,   file:/MyFile.ext",
			"c:/MyFile.ext,  c:/MyFile.ext",
	}, replace = true)
	public void absoluteFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject///folder///deep/myFile.ext//, file:/{}myProject/folder/deep/myFile.ext",
			"/myProject///folder///deep/myFile.ext//,  file:/myProject/folder/deep/myFile.ext",
			"//myProject///folder///deep/myFile.ext//, file:////myProject/folder/deep/myFile.ext",
	})
	public void absoluteFileSlashesExcess_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}myProject///folder///deep/myFile.ext//,            file:/myProject/folder/deep/myFile.ext",
			"c:/myProject///folder///deep/myFile.ext//,           c:/myProject/folder/deep/myFile.ext",
	}, replace = true)
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
			"//myProject/myFolder/, file:////myProject/myFolder",
	})
	public void absoluteFolderSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}myProject/myFolder/,     file:/myProject/myFolder",
			"c:/myProject/myFolder/,    c:/myProject/myFolder",
	}, replace = true)
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
			"//myProject///myFolder, file:////myProject/myFolder",
	})
	public void absoluteFolderSlashesInbetween_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}myProject///myFolder,      file:/myProject/myFolder",
			"c:/myProject///myFolder,     c:/myProject/myFolder",
	}, replace = true)
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
			"//myProject/myFolder?query#fragment, file:////myProject/myFolder%3Fquery%23fragment",
	})
	public void absoluteFragmentQuery_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}myProject/myFolder?query#fragment,    file:/myProject/myFolder%3Fquery%23fragment",
			"c:/myProject/myFolder?query#fragment,   c:/myProject/myFolder%3Fquery%23fragment",
	}, replace = true)
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
			"//some/path/MyFile.ext, file:////some/path/MyFile.ext",
	})
	public void absoluteNestedFile_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}some/path/MyFile.ext,     file:/some/path/MyFile.ext",
			"c:/some/path/MyFile.ext,    c:/some/path/MyFile.ext",
	}, replace = true)
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
			"//resource/..////, file:////resource/.."
	})
	public void absolutePath_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}resource/..////,        file:{}resource/..",
			"//resource/..////, file:/resource/..",
			"c:\\resource\\..\\\\\\\\",
			"\\resource\\..\\\\\\\\, file:%5Cresource%5C..%5C%5C%5C%5C",
			"\\\\resource\\..\\\\\\\\, file:%5C%5Cresource%5C..%5C%5C%5C%5C",
	})
	public void absolutePath_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals_Exceptional(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject/myFolder#query, file:/{}myProject/myFolder%23query",
			"/myProject/myFolder#query,  file:/myProject/myFolder%23query",
			"//myProject/myFolder#query, file:////myProject/myFolder%23query",
	})
	public void absolutePseudoFragment_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}myProject/myFolder#query,    file:/myProject/myFolder%23query",
			"c:/myProject/myFolder#query,   c:/myProject/myFolder%23query",
	}, replace = true)
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
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{},   file:/",
			"c:/,  c:/",
	}, replace = true)
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
	@ConversionSource(value = {
			"/{}some/path/MyFile.ext,      file:/some/path/MyFile.ext",
			"/c:/some/path/MyFile.ext,     file:/c:/some/path/MyFile.ext",
			"\\c:\\some\\path\\MyFile.ext",
	}, replace = true)
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
	@ConversionSource(value = {
			"//{}some/path/MyFile.ext,       file:/some/path/MyFile.ext",
			"//c:/some/path/MyFile.ext,      file:/c:/some/path/MyFile.ext",
			"\\\\c:\\some\\path\\MyFile.ext",
	}, replace = true)
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
	@ConversionSource(value = {
			"///{}some/path/MyFile.ext,        file:/some/path/MyFile.ext",
			"///c:/some/path/MyFile.ext,       file:/c:/some/path/MyFile.ext",
			"\\\\\\c:\\some\\path\\MyFile.ext",
	}, replace = true)
	public void absoluteWindowsPathTripleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals_Exceptional(fun, inputStr, expectedStr);
	}
}
