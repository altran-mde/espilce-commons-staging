/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javapath.javafile;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
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

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = Path.class, returnType = File.class)
public class TestJavaPath2JavaFile_absolute extends ATestJavaPath2JavaFile implements TestIAbsolute {
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}MyFile.ext, {}MyFile.ext",
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
			"{}MyFile.ext, {}MyFile.ext",
	})
	public void absoluteFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject///folder///deep/myFile.ext//, {}myProject///folder///deep/myFile.ext//",
	})
	public void absoluteFileSlashesExcess_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}myProject///folder///deep/myFile.ext//, {}myProject///folder///deep/myFile.ext//",
	})
	public void absoluteFileSlashesExcess_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject/myFolder/, {}myProject/myFolder/",
	})
	public void absoluteFolderSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}myProject/myFolder/, {}myProject/myFolder/",
	})
	public void absoluteFolderSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject///myFolder, {}myProject///myFolder",
	})
	public void absoluteFolderSlashesInbetween_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}myProject///myFolder, {}myProject///myFolder",
	})
	public void absoluteFolderSlashesInbetween_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject/myFolder?query#fragment, {}myProject/myFolder?query#fragment",
	})
	public void absoluteFragmentQuery_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertThrows(InvalidPathException.class, () -> Paths.get(inputStr));
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}myProject/myFolder?query#fragment, {}myProject/myFolder?query#fragment",
	})
	public void absoluteFragmentQuery_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}some/path/MyFile.ext, {}some/path/MyFile.ext",
	})
	public void absoluteNestedFile_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}some/path/MyFile.ext, {}some/path/MyFile.ext",
	})
	public void absoluteNestedFile_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}resource/..////, {}resource/..////",
	})
	public void absolutePath_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}resource/..////, {}resource/..////",
	})
	public void absolutePath_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}myProject/myFolder#query, {}myProject/myFolder#query",
	})
	public void absolutePseudoFragment_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}myProject/myFolder#query, {}myProject/myFolder#query",
	})
	public void absolutePseudoFragment_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}, {}",
	})
	public void root_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		if (inputStr.endsWith("//") || inputStr.endsWith("\\\\")) {
			assertThrows(InvalidPathException.class, () -> Paths.get(inputStr));
		} else {
			assertConversionEquals(fun, inputStr, expectedStr);
		}
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}, {}",
	})
	public void root_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}some/path/MyFile.ext,    {}some/path/MyFile.ext",
	})
	public void absoluteWindowsPathSingleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}some/path/MyFile.ext,    {}some/path/MyFile.ext",
	})
	public void absoluteWindowsPathSingleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}/some/path/MyFile.ext,       {}/some/path/MyFile.ext",
	})
	public void absoluteWindowsPathDoubleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"{}/some/path/MyFile.ext,       {}/some/path/MyFile.ext",
	})
	public void absoluteWindowsPathDoubleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"{}//some/path/MyFile.ext,        {}//some/path/MyFile.ext",
	})
	public void absoluteWindowsPathTripleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"///{}some/path/MyFile.ext,        ///{}some/path/MyFile.ext",
	})
	public void absoluteWindowsPathTripleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
}
