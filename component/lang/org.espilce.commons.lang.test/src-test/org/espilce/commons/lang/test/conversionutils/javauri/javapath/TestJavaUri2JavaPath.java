/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri.javapath;

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;
import static org.espilce.commons.lang.test.junit5.AssertConversion.assertNullResult;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.conversionutils.TestIBase;
import org.espilce.commons.lang.test.conversionutils.TestIJavaUri;
import org.espilce.commons.lang.test.conversionutils.TestIParamsInvalid;
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.espilce.commons.lang.test.conversionutils.TestIScheme;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.ConversionSource;
import org.espilce.commons.lang.test.junit5.TestConversion;
import org.espilce.commons.lang.test.junit5.TestOnUnix;
import org.espilce.commons.lang.test.junit5.TestOnWindows;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URI.class, returnType = Path.class)
public class TestJavaUri2JavaPath
		implements TestIBase, TestIAbsolute, TestIRelative, TestIScheme, TestIJavaUri, TestIParamsInvalid
{
	
	//// TestIAbsolute ////
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}MyFile.ext, {}MyFile.ext"
	}, backslash = false)
	public void absoluteFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"file:{}MyFile.ext, {}MyFile.ext"
	})
	public void absoluteFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///folder///deep/myFile.ext//, {}myProject/folder/deep/myFile.ext"
	}, backslash = false)
	public void absoluteFileSlashesExcess_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"file:{}myProject///folder///deep/myFile.ext//, {}myProject/folder/deep/myFile.ext"
	})
	public void absoluteFileSlashesExcess_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject/myFolder/, {}myProject/myFolder"
	}, backslash = false)
	public void absoluteFolderSlash_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject/myFolder/, {}myProject/myFolder"
	}, backslash = false)
	public void absoluteFolderSlash_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///myFolder, {}myProject/myFolder"
	}, backslash = false)
	public void absoluteFolderSlashesInbetween_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///myFolder, {}myProject/myFolder"
	}, backslash = false)
	public void absoluteFolderSlashesInbetween_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject///myFolder?query#fragment, {}myProject///myFolder"
	}, backslash = false)
	public void absoluteFragmentQuery_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject///myFolder?query#fragment, {}myProject///myFolder"
	}, backslash = false)
	public void absoluteFragmentQuery_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	)
			throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}resource/..////, {}resource/.."
	}, backslash = false)
	public void absolutePath_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}resource/..////, {}resource/.."
	}, backslash = false)
	public void absolutePath_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}myProject/myFolder%23query, {}myProject/myFolder#query"
	}, backslash = false)
	public void absolutePseudoFragment_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}myProject/myFolder%23query, {}myProject/myFolder#query"
	}, backslash = false)
	public void absolutePseudoFragment_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}, {}",
			"file://"
	}, backslash = false)
	public void root_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals_Exceptional(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}, {}",
			"file://"
	}, backslash = false)
	public void root_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals_Exceptional(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"file:/{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	})
	public void absoluteWindowsPathSingleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"file:/{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	})
	public void absoluteWindowsPathSingleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"file://{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	})
	public void absoluteWindowsPathDoubleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"file://{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	})
	public void absoluteWindowsPathDoubleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource({
			"file:///{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	})
	public void absoluteWindowsPathTripleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource({
			"file:///{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	})
	public void absoluteWindowsPathTripleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	
	//// TestIRelative ////
	
	
	@Override
	@TestConversion(".")
	public void current(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI("file", inputStr, null);
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
		final URI input = new URI("file", inputStr, null);
		assertConversionEquals(fun, input, "./");
	}
	
	@Override
	@TestConversion(value = "file:resource/../some/dir/../../file.ext", backslash = false)
	public void multiRelativePath(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "resource/../some/dir/../../file.ext");
	}
	
	@Override
	@TestConversion("file:..")
	public void parent(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "..");
	}
	
	@Override
	@TestConversion("file:MyFile.ext")
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
	@TestConversion(value = "file:myProject///myFolder?query#fragment", backslash = false)
	public void relativeFragmentQuery(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
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
	
	
	//// TestIBase ////
	
	
	@Override
	@TestConversion("")
	public void empty(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "");
	}
	
	@Override
	@TestConversion(/* null */)
	public void paramNull(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = null;
		assertNullResult(fun, input);
	}
	
	
	//// TestIJavaUri ////
	
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	}, backslash = false)
	public void absoluteNestedFileNoScheme_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	}, backslash = false)
	public void absoluteNestedFileNoScheme_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"file:{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	}, backslash = false)
	public void absoluteNestedFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"file:{}some/path/MyFile.ext, {}some/path/MyFile.ext"
	}, backslash = false)
	public void absoluteNestedFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		assertConversionEquals(fun, inputStr, expectedStr);
	}
	
	@Override
	@TestConversion("some/path/MyFile.ext")
	public void relativeNestedFileNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, "some/path/MyFile.ext");
	}
	
	@Override
	@TestConversion(".")
	public void currentNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, ".");
	}
	
	@Override
	@TestConversion("./some/path/MyFile.ext")
	public void currentRelativeNestedFileNoScheme(final ConversionFunction fun, final String inputStr)
			throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, "./some/path/MyFile.ext");
	}
	
	@Override
	@TestConversion("./")
	public void currentSlashNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, "./");
	}
	
	@Override
	@TestConversion("")
	public void emptyNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, "");
	}
	
	@Override
	@TestConversion("!@#fasfasdf")
	public void inputBroken(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion("MyFile.ext")
	public void noScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "MyFile.ext");
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"{}, {}"
	})
	public void rootNoScheme_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}, {}"
	})
	public void rootNoScheme_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, expectedStr);
	}
	
	@Override
	@TestOnWindows
	@ConversionSource(value = {
			"{}, {}"
	})
	public void rootScheme_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final URI input = new URI("file", inputStr, null);
		assertConversionEquals(fun, input, expectedStr);
	}
	
	@Override
	@TestOnUnix
	@ConversionSource(value = {
			"{}, {}"
	})
	public void rootScheme_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception {
		final URI input = new URI("file", inputStr, null);
		assertConversionEquals(fun, input, expectedStr);
	}
	
	@Override
	@TestConversion("../resource/////")
	public void startRelativePathNoScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertConversionEquals(fun, input, "../resource/");
	}
	
	
	//// TestIScheme ////
	
	
	@Override
	@TestConversion(value = "file:/resource/...////", backslash = false)
	public void relativeUri(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "/resource/.../");
	}
	
	@Override
	@TestConversion(value = " ", backslash = false)
	public void emptyWithScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI("file", inputStr, null);
		assertConversionEquals(fun, input, "");
	}
	
	@Override
	@TestConversion(value = "file:/myProject///myFolder#fragment", backslash = false)
	public void fragment(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(value = "file:/myProject///myFolder?query#fragment", backslash = false)
	public void fragmentQuery(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion("file:fasfasdf")
	public void inputNoSlashes(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "fasfasdf");
	}
	
	@Override
	@TestConversion(value = "https://example.com/MyFile.ext", backslash = false)
	public void otherSchema(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(value = "file:/myProject///myFolder?query", backslash = false)
	public void query(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	
	//// TestIParamsInvalid ////
	
	
	@Override
	@TestConversion(value = "http:/myProject/myFolder", backslash = false)
	public void invalidScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion("mailto:test@example.com")
	public void opaqueScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	private void assertConversionEquals(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws URISyntaxException {
		final URI input = new URI(inputStr);
		assertConversionEquals(fun, input, expectedStr);
	}
	
	private void assertConversionEquals(final ConversionFunction fun, final URI input, final String expectedStr) {
		final Object actual = fun.apply(input);
		final Path expected = Paths.get(expectedStr);
		assertEquals(expected, actual);
	}
	
	private void assertConversionEquals_Exceptional(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws URISyntaxException {
		final URI input = new URI(inputStr);
		
		if (expectedStr != null) {
			final Object actual = fun.apply(input);
			final Path expected = Paths.get(expectedStr);
			assertEquals(expected, actual);
		} else {
			assertIllegalConversion(fun, input);
		}
	}
}
