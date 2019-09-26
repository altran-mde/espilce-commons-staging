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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.TestConversion;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = Path.class, returnType = URL.class)
public class TestJavaPath2JavaUrl_relative extends ATestJavaPath2JavaUrl implements TestIRelative {
	
	@Override
	@TestConversion(value = ".", backslash = false)
	public void current(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL(".");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion("./some/path/MyFile.ext")
	public void currentRelativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("./some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion("./")
	public void currentSlash(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		// trailing slash is swallowed by File()
		final URL expected = new URL(".");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion("resource/../some/dir/../../file.ext")
	public void multiRelativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("resource/../some/dir/../../file.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion(value = "file:..", backslash = false)
	public void parent(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("..");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion(value = "file:MyFile.ext", backslash = false)
	public void relativeFile(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion("myProject///folder///deep/myFile.ext//")
	public void relativeFileSlashesExcess(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion("myProject/myFolder/")
	public void relativeFolderSlash(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion("myProject///myFolder")
	public void relativeFolderSlashesInbetween(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion("myProject/myFolder?query#fragment")
	public void relativeFragmentQuery(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("myProject/myFolder%3Fquery%23fragment");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion("some/path/MyFile.ext")
	public void relativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion("resource/..////")
	public void relativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion("myProject///myFolder#query")
	public void relativePseudoFragment(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("myProject/myFolder%23query");
		assertEquals(expected, actual);
	}
	
	@Override
	@TestConversion("../resource/..////")
	public void startRelativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path input = Paths.get(inputStr);
		final Object actual = fun.apply(input);
		final URL expected = new URL("../resource/..");
		assertEquals(expected, actual);
	}
}
