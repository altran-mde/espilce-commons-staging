/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javaurl.javapath;

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;

import java.net.URL;
import java.nio.file.Path;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.ConversionSource;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URL.class, returnType = Path.class)
public class TestJavaUrl2JavaPath_absolute extends ATestJavaUrl2JavaPath implements TestIAbsolute {
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}MyFile.ext, {}MyFile.ext",
			"file://MyFile.ext",
			"file:\\\\MyFile.ext",
	})
	public void absoluteFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals_Exceptional(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}MyFile.ext, {}MyFile.ext",
	}, replace = true)
	public void absoluteFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals_Exceptional(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///folder///deep/myFile.ext//, {}myProject/folder/deep/myFile.ext"
	})
	public void absoluteFileSlashesExcess_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///folder///deep/myFile.ext//,            {}myProject/folder/deep/myFile.ext",
			"file:\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\,   \\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\",
			"file:\\\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\, \\\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\",
			"file:c:\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\, c:\\myProject\\\\\\folder\\\\\\deep\\myFile.ext\\\\",
	}, replace = true)
	public void absoluteFileSlashesExcess_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject/myFolder/, {}myProject/myFolder"
	})
	public void absoluteFolderSlash_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject/myFolder/,     {}myProject/myFolder",
			"file:\\myProject\\myFolder\\,   \\myProject\\myFolder\\",
			"file:\\\\myProject\\myFolder\\, \\\\myProject\\myFolder\\",
			"file:c:\\myProject\\myFolder\\, c:\\myProject\\myFolder\\",
	}, replace = true)
	public void absoluteFolderSlash_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///myFolder, {}myProject/myFolder"
	})
	public void absoluteFolderSlashesInbetween_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///myFolder,      {}myProject/myFolder",
			"file:\\myProject\\\\\\myFolder,   \\myProject\\\\\\myFolder",
			"file:\\\\myProject\\\\\\myFolder, \\\\myProject\\\\\\myFolder",
			"file:c:\\myProject\\\\\\myFolder, c:\\myProject\\\\\\myFolder",
	}, replace = true)
	public void absoluteFolderSlashesInbetween_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///myFolder?query#fragment, {}myProject///myFolder"
	})
	public void absoluteFragmentQuery_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final URL input = new URL(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///myFolder?query#fragment, {}myProject///myFolder"
	})
	public void absoluteFragmentQuery_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		final URL input = new URL(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}resource/..////, {}resource/.."
	})
	public void absolutePath_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}resource/..////,        {}resource/..",
			"file:\\resource\\..\\\\\\\\,   \\resource\\..\\\\\\\\",
			"file:\\\\resource\\..\\\\\\\\, \\\\resource\\..\\\\\\\\",
			"file:c:\\resource\\..\\\\\\\\, c:\\resource\\..\\\\\\\\",
	}, replace = true)
	public void absolutePath_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject/myFolder%23query, {}myProject/myFolder#query"
	})
	public void absolutePseudoFragment_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject/myFolder%23query, {}myProject/myFolder#query"
	}, replace = true)
	public void absolutePseudoFragment_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}, {}",
			"file://",
			"file:\\\\",
	})
	public void root_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final String expected = expectedStr != null ? expectedStr : "";
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{},   {}",
			"file://",
			"file:\\,   \\",
			"file:\\\\, \\\\",
			"file:c:\\, c:\\",
	})
	public void root_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final String expected = expectedStr != null ? expectedStr : "";
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"file:/{}some/path/MyFile.ext, {}some/path/MyFile.ext",
			"file://some/path/MyFile.ext, //some/path/MyFile.ext",
			"file:///some/path/MyFile.ext, /some/path/MyFile.ext",
			"file:\\\\some\\path\\MyFile.ext, //some/path/MyFile.ext",
			"file:\\\\\\some\\path\\MyFile.ext, /some/path/MyFile.ext",
	})
	public void absoluteWindowsPathSingleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:/{}some/path/MyFile.ext, /{}some/path/MyFile.ext",
	}, replace = true)
	public void absoluteWindowsPathSingleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"file://{}some/path/MyFile.ext, {}some/path/MyFile.ext",
			"file:///some/path/MyFile.ext, /some/path/MyFile.ext",
			"file:////some/path/MyFile.ext, //some/path/MyFile.ext",
	})
	public void absoluteWindowsPathDoubleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file://{}some/path/MyFile.ext, //{}some/path/MyFile.ext",
	}, replace = true)
	public void absoluteWindowsPathDoubleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"file:///{}some/path/MyFile.ext, {}some/path/MyFile.ext",
			"file:////some/path/MyFile.ext, //some/path/MyFile.ext",
			"file://///some/path/MyFile.ext, //some/path/MyFile.ext",
			"file:\\\\\\\\some\\path\\MyFile.ext, //some/path/MyFile.ext",
			"file:\\\\\\\\\\some\\path\\MyFile.ext, //some/path/MyFile.ext",
	})
	public void absoluteWindowsPathTripleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:///{}some/path/MyFile.ext, ///{}some/path/MyFile.ext",
	}, replace = true)
	public void absoluteWindowsPathTripleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	})
	public void absoluteNestedFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	}, replace = true)
	public void absoluteNestedFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
}
