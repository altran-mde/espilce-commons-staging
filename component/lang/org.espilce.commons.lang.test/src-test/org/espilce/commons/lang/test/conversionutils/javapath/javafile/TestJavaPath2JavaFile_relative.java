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
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.TestConversion;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = Path.class, returnType = File.class)
public class TestJavaPath2JavaFile_relative extends ATestJavaPath2JavaFile implements TestIRelative {
	@Override
	@TestConversion(value = ".", backslash = false)
	public void current(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File(".");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("./some/path/MyFile.ext")
	public void currentRelativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File("./some/path/MyFile.ext");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("./")
	public void currentSlash(final ConversionFunction fun, final String inputStr) throws Exception {
		// trailing slash is swallowed by File()
		final File expected = new File(".");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("resource/../some/dir/../../file.ext")
	public void multiRelativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File("resource/../some/dir/../../file.ext");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion(value = "..", backslash = false)
	public void parent(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File("..");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion(value = "MyFile.ext", backslash = false)
	public void relativeFile(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File("MyFile.ext");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("myProject///folder///deep/myFile.ext//")
	public void relativeFileSlashesExcess(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File("myProject/folder/deep/myFile.ext");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("myProject/myFolder/")
	public void relativeFolderSlash(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File("myProject/myFolder");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("myProject///myFolder")
	public void relativeFolderSlashesInbetween(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File("myProject/myFolder");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("myProject/myFolder?query#fragment")
	public void relativeFragmentQuery(final ConversionFunction fun, final String inputStr) throws Exception {
		assertThrows(InvalidPathException.class, () -> Paths.get(inputStr));
	}
	
	@Override
	@TestConversion("some/path/MyFile.ext")
	public void relativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File("some/path/MyFile.ext");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("resource/..////")
	public void relativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File("resource/..");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("myProject///myFolder#query")
	public void relativePseudoFragment(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File("myProject///myFolder#query");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("../resource/..////")
	public void startRelativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		final File expected = new File("../resource/..");
		assertConversionEquals(fun, inputStr, expected);
	}
}
