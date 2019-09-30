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
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.TestConversion;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = File.class, returnType = URL.class)
public class TestJavaFile2JavaUrl_relative extends ATestJavaFile2JavaUrl implements TestIRelative {
	
	@Override
	@TestConversion(value = ".", backslash = false)
	public void current(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr);
	}
	
	@Override
	@TestConversion("./some/path/MyFile.ext")
	public void currentRelativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr);
	}
	
	@Override
	@TestConversion("./")
	public void currentSlash(final ConversionFunction fun, final String inputStr) throws Exception {
		// trailing slash is swallowed by File()
		final URL expected = new URL("file:.");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("resource/../some/dir/../../file.ext")
	public void multiRelativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr);
	}
	
	@Override
	@TestConversion(value = "..", backslash = false)
	public void parent(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr);
	}
	
	@Override
	@TestConversion(value = "MyFile.ext", backslash = false)
	public void relativeFile(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr);
	}
	
	@Override
	@TestConversion("myProject///folder///deep/myFile.ext//")
	public void relativeFileSlashesExcess(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL expected = new URL("file:myProject/folder/deep/myFile.ext");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("myProject/myFolder/")
	public void relativeFolderSlash(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL expected = new URL("file:myProject/myFolder");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("myProject///myFolder")
	public void relativeFolderSlashesInbetween(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL expected = new URL("file:myProject/myFolder");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestOnWindows
	@TestConversion("myProject/myFolder?query#fragment")
	public void relativeFragmentQuery_win(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL expected = new URL("file:myProject/myFolder%3Fquery%23fragment");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestOnUnix
	@TestConversion("myProject/myFolder?query#fragment")
	public void relativeFragmentQuery_unix(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL expected = new URL("file:myProject/myFolder%3Fquery%23fragment");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("some/path/MyFile.ext")
	public void relativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr);
	}
	
	@Override
	@TestConversion("resource/..////")
	public void relativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL expected = new URL("file:resource/..");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("myProject///myFolder#query")
	public void relativePseudoFragment(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL expected = new URL("file:myProject/myFolder%23query");
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion("../resource/..////")
	public void startRelativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL expected = new URL("file:../resource/..");
		assertConversionEquals(fun, inputStr, expected);
	}
}
