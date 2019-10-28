/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javaurl.javafile;

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;

import java.io.File;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.TestConversion;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URL.class, returnType = File.class)
public class TestJavaUrl2JavaFile_relative extends ATestJavaUrl2JavaFile implements TestIRelative {
	
	@Override
	@TestConversion(value = ".", backslash = false)
	public void current(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL input = new URL("file:.");
		assertConversionEquals(fun, input, ".");
	}
	
	@Override
	@TestConversion(value = "file:./some/path/MyFile.ext", backslash = false)
	public void currentRelativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "./some/path/MyFile.ext");
	}
	
	@Override
	@TestConversion("./")
	public void currentSlash(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL input = new URL("file:./");
		assertConversionEquals(fun, input, "./");
	}
	
	@Override
	@TestConversion(value = "file:resource/../some/dir/../../file.ext", backslash = false)
	public void multiRelativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "resource/../some/dir/../../file.ext");
	}
	
	@Override
	@TestConversion(value = "file:..", backslash = false)
	public void parent(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "..");
	}
	
	@Override
	@TestConversion(value = "file:MyFile.ext", backslash = false)
	public void relativeFile(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "MyFile.ext");
	}
	
	@Override
	@TestConversion(value = "file:myProject///folder///deep/myFile.ext//", backslash = false)
	public void relativeFileSlashesExcess(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "myProject/folder/deep/myFile.ext");
	}
	
	@Override
	@TestConversion(value = "file:myProject/myFolder/", backslash = false)
	public void relativeFolderSlash(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "myProject/myFolder");
	}
	
	@Override
	@TestConversion(value = "file:myProject///myFolder", backslash = false)
	public void relativeFolderSlashesInbetween(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "myProject/myFolder");
	}
	
	@Override
	@TestOnWindows
	@TestConversion(value = "file:myProject///myFolder?query#fragment", backslash = false)
	public void relativeFragmentQuery_win(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL input = new URL(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestOnUnix
	@TestConversion(value = "file:myProject///myFolder?query#fragment", backslash = false)
	public void relativeFragmentQuery_unix(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL input = new URL(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(value = "file:some/path/MyFile.ext", backslash = false)
	public void relativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "some/path/MyFile.ext");
	}
	
	@Override
	@TestConversion(value = "file:resource/..////", backslash = false)
	public void relativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "resource/..");
	}
	
	@Override
	@TestConversion(value = "file:myProject///myFolder%23query", backslash = false)
	public void relativePseudoFragment(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "myProject/myFolder#query/");
	}
	
	@Override
	@TestConversion(value = "file:../resource/..////", backslash = false)
	public void startRelativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "../resource/..");
	}
}
