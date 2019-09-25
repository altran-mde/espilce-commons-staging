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

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;
import static org.espilce.commons.lang.test.junit5.AssertConversion.assertNullResult;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.conversionutils.TestIBase;
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.espilce.commons.lang.test.conversionutils.TestIScheme;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.ConversionSource;
import org.espilce.commons.lang.test.junit5.TestConversion;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URL.class, returnType = URI.class)
public class TestJavaUrl2JavaUri
		implements TestIBase, TestIAbsolute, TestIRelative, TestIScheme
{
	
	//// TestIAbsolute ////
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}MyFile.ext, file:{}MyFile.ext"
	}, backslash = false)
	public void absoluteFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}MyFile.ext, file:{}MyFile.ext"
	}, backslash = false)
	public void absoluteFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///folder///deep/myFile.ext//, file:{}myProject///folder///deep/myFile.ext//"
	}, backslash = false)
	public void absoluteFileSlashesExcess_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///folder///deep/myFile.ext//, file:{}myProject///folder///deep/myFile.ext//"
	}, backslash = false)
	public void absoluteFileSlashesExcess_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject/myFolder/, file:{}myProject/myFolder/"
	}, backslash = false)
	public void absoluteFolderSlash_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject/myFolder/, file:{}myProject/myFolder/"
	}, backslash = false)
	public void absoluteFolderSlash_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///myFolder, file:{}myProject///myFolder"
	}, backslash = false)
	public void absoluteFolderSlashesInbetween_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///myFolder, file:{}myProject///myFolder"
	}, backslash = false)
	public void absoluteFolderSlashesInbetween_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///myFolder?query#fragment, file:{}myProject///myFolder?query#fragment"
	}, backslash = false)
	public void absoluteFragmentQuery_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///myFolder?query#fragment, file:{}myProject///myFolder?query#fragment"
	}, backslash = false)
	public void absoluteFragmentQuery_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}some/path/MyFile.ext, file:{}some/path/MyFile.ext"
	}, backslash = false)
	public void absoluteNestedFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}some/path/MyFile.ext, file:{}some/path/MyFile.ext"
	}, backslash = false)
	public void absoluteNestedFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}resource/..////, file:{}resource/..////"
	}, backslash = false)
	public void absolutePath_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}resource/..////, file:{}resource/..////"
	}, backslash = false)
	public void absolutePath_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject/myFolder%23query, file:{}myProject/myFolder%23query"
	}, backslash = false)
	public void absolutePseudoFragment_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject/myFolder%23query, file:{}myProject/myFolder%23query"
	}, backslash = false)
	public void absolutePseudoFragment_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}, file:{}"
	}, backslash = false)
	public void root_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}, file:{}"
	}, backslash = false)
	public void root_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
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
			"file:/{}some/path/MyFile.ext, file:/{}some/path/MyFile.ext"
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
			"file://{}some/path/MyFile.ext, file://{}some/path/MyFile.ext"
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
			"file:///{}some/path/MyFile.ext, file:///{}some/path/MyFile.ext"
	})
	public void absoluteWindowsPathTripleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	
	//// TestIRelative ////
	
	
	@Override
	@TestConversion(value = ".", backslash = false)
	public void current(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr);
	}
	
	@Override
	@TestConversion(value = "./some/path/MyFile.ext", backslash = false)
	public void currentRelativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr);
	}
	
	@Override
	@TestConversion(value = "./", backslash = false)
	public void currentSlash(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr);
	}
	
	@Override
	@TestConversion(value = "file:resource/../some/dir/../../file.ext", backslash = false)
	public void multiRelativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:resource/../some/dir/../../file.ext");
	}
	
	@Override
	@TestConversion(value = "..", backslash = false)
	public void parent(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr);
	}
	
	@Override
	@TestConversion(value = "file:MyFile.ext", backslash = false)
	public void relativeFile(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:MyFile.ext");
	}
	
	@Override
	@TestConversion(value = "file:myProject///folder///deep/myFile.ext//", backslash = false)
	public void relativeFileSlashesExcess(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:myProject///folder///deep/myFile.ext//");
	}
	
	@Override
	@TestConversion(value = "file:myProject/myFolder/", backslash = false)
	public void relativeFolderSlash(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:myProject/myFolder/");
	}
	
	@Override
	@TestConversion(value = "file:myProject///myFolder", backslash = false)
	public void relativeFolderSlashesInbetween(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:myProject///myFolder");
	}
	
	@Override
	@TestConversion(value = "file:myProject///myFolder?query#fragment", backslash = false)
	public void relativeFragmentQuery(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:myProject///myFolder?query#fragment");
	}
	
	@Override
	@TestConversion(value = "file:some/path/MyFile.ext", backslash = false)
	public void relativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:some/path/MyFile.ext");
	}
	
	@Override
	@TestConversion(value = "file:resource/..////", backslash = false)
	public void relativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:resource/..////");
	}
	
	@Override
	@TestConversion(value = "file:myProject///myFolder%23query", backslash = false)
	public void relativePseudoFragment(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:myProject///myFolder%23query");
	}
	
	@Override
	@TestConversion(value = "file:../resource/..////", backslash = false)
	public void startRelativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:../resource/..////");
	}
	
	
	//// TestIBase ////
	
	
	@Override
	@TestConversion(value = "", backslash = false)
	public void empty(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL input = new URL("file:" + inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(/* null */ backslash = false)
	public void paramNull(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL input = null;
		assertNullResult(fun, input);
	}
	
	
	//// TestIScheme ////
	
	
	@Override
	@TestConversion(value = "mailto:resource/...////", backslash = false)
	public void relativeUri(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "mailto:resource/...////");
	}
	
	@Override
	@TestConversion(value = " ", backslash = false)
	public void emptyWithScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL input = new URL("file:" + inputStr);
		assertConversionEquals(fun, input, "file:%20");
	}
	
	@Override
	@TestConversion(value = "http:/myProject///myFolder#fragment", backslash = false)
	public void fragment(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "http:/myProject///myFolder#fragment");
	}
	
	@Override
	@TestConversion(value = "http:/myProject///myFolder?query#fragment", backslash = false)
	public void fragmentQuery(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "http:/myProject///myFolder?query#fragment");
	}
	
	@Override
	@TestConversion(value = "file:fasfasdf", backslash = false)
	public void inputNoSlashes(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:fasfasdf");
	}
	
	@Override
	@TestConversion(value = "https://example.com/MyFile.ext", backslash = false)
	public void otherSchema(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "https://example.com/MyFile.ext");
	}
	
	@Override
	@TestConversion(value = "file:/myProject///myFolder?query", backslash = false)
	public void query(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "file:/myProject///myFolder?query");
	}
	
	private void assertConversionEquals(final ConversionFunction fun, final String inputStr)
			throws URISyntaxException, MalformedURLException {
		final URL input = new URL("file:" + inputStr);
		final URI expected = new URI("file:" + inputStr);
		assertConversionEquals(fun, input, expected);
	}
	
	private void assertConversionEquals(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws URISyntaxException, MalformedURLException {
		final URL input = new URL(inputStr);
		assertConversionEquals(fun, input, expectedStr);
	}
	
	private void assertConversionEquals(final ConversionFunction fun, final URL input, final String expectedStr)
			throws URISyntaxException {
		final Object actual = fun.apply(input);
		final URI expected = new URI(expectedStr);
		assertEquals(expected, actual);
	}
	
	private void assertConversionEquals(final ConversionFunction fun, final URL input, final URI expected) {
		final Object actual = fun.apply(input);
		assertEquals(expected, actual);
	}
}