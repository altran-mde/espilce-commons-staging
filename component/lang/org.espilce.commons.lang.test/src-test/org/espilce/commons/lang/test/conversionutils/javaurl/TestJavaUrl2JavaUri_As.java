/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javaurl;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URL;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUrl2JavaUri_As implements TestJavaUrl2JavaUri {
	@Override
	@SuppressWarnings("null")
	@Test(expected = NullPointerException.class)
	public void paramNull() throws Exception {
		final URL input = (URL) null;
		ConversionUtils.asJavaUri(input);
	}
	
	@Override
	@Test
	public void root() throws Exception {
		final URL input = new URL("file:/");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("file:/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void empty() throws Exception {
		final URL input = new URL("file:");
		ConversionUtils.asJavaUri(input);
	}
	
	@Override
	@Test
	public void emptyWithScheme() throws Exception {
		final URL input = new URL("http:///");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("http:///");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void otherSchema() throws Exception {
		final URL input = new URL("https://example.com/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("https://example.com/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFile() throws Exception {
		final URL input = new URL("file:MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("file:MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final URL url = new URL("file:/c:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(url);
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final URL url = new URL("file://c:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(url);
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final URL url = new URL("file:///c:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(url);
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteNestedFile() throws Exception {
		final URL input = new URL("http:/myProject/folder/deep/myFile.ext");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("http:/myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final URL input = new URL("http:////myProject///folder///deep/myFile.ext//");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("http:////myProject///folder///deep/myFile.ext//");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlash() throws Exception {
		final URL input = new URL("http:/myProject/myFolder/");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("http:/myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final URL input = new URL("http:/myProject///myFolder");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("http:/myProject///myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void fragment() throws Exception {
		final URL input = new URL("http:/myProject///myFolder#fragment");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("http:/myProject///myFolder#fragment");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void query() throws Exception {
		final URL input = new URL("http:/myProject///myFolder?query");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("http:/myProject///myFolder?query");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void fragmentQuery() throws Exception {
		final URL input = new URL("http:/myProject///myFolder?query#fragment");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("http:/myProject///myFolder?query#fragment");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void inputNoSlashes() throws Exception {
		final URL input = new URL("file:fasfasdf");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("file:fasfasdf");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeUri() throws Exception {
		final URL input = new URL("mailto:/resource/...////");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("mailto:/resource/...////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFile() throws Exception {
		final URL input = new URL("file:/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("file:/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePath() throws Exception {
		final URL input = new URL("file:/resource/..////");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("file:/resource/..////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeNestedFile() throws Exception {
		final URL input = new URL("file:some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("file:some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final URL input = new URL("file:myProject///folder///deep/myFile.ext//");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("file:myProject///folder///deep/myFile.ext//");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlash() throws Exception {
		final URL input = new URL("file:myProject/myFolder/");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("file:myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final URL input = new URL("file:myProject///myFolder");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("file:myProject///myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePath() throws Exception {
		final URL input = new URL("file:resource/..////");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("file:resource/..////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void multiRelativePath() throws Exception {
		final URL input = new URL("file:resource/../some/dir/../../file.ext");
		final URI actual = ConversionUtils.asJavaUri(input);
		final URI expected = new URI("file:resource/../some/dir/../../file.ext");
		assertEquals(expected, actual);
	}
}
