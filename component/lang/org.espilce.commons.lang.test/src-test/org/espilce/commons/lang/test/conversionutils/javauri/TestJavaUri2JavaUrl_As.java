/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URL;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUri2JavaUrl_As implements TestJavaUri2JavaUrl {
	@Override
	@SuppressWarnings("null")
	@Test(expected = NullPointerException.class)
	public void paramNull() throws Exception {
		final URI input = (URI) null;
		ConversionUtils.asJavaUrl(input);
	}
	
	@Override
	@Test
	public void root() throws Exception {
		final URI input = URI.create("http:/");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("http:/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void empty() throws Exception {
		final URI input = URI.create("");
		ConversionUtils.asJavaUrl(input);
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void inputBroken() throws Exception {
		final URI input = URI.create("fasfasdf");
		ConversionUtils.asJavaUrl(input);
	}
	
	@Override
	@Test
	public void emptyWithScheme() throws Exception {
		final URI input = URI.create("http:///");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("http:///");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFile() throws Exception {
		final URI input = new URI("file:MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final URI input = new URI("file:myProject///folder///deep/myFile.ext//");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:myProject///folder///deep/myFile.ext//");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final URI input = new URI("file:myProject///myFolder");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:myProject///myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePath() throws Exception {
		final URI input = new URI("file:resource/..////");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:resource/..////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void multiRelativePath() throws Exception {
		final URI input = new URI("file:resource/../some/dir/../../file.ext");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:resource/../some/dir/../../file.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlash() throws Exception {
		final URI input = new URI("file:myProject/myFolder/");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final URI input = new URI("file:/c:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final URI input = new URI("file://c:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final URI input = new URI("file:///c:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void otherSchema() throws Exception {
		final URI input = URI.create("https://example.com/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("https://example.com/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void noSchema() throws Exception {
		final URI input = URI.create("MyFile.ext");
		ConversionUtils.asJavaUrl(input);
	}
	
	@Override
	@Test
	public void absoluteNestedFile() throws Exception {
		final URI input = URI.create("http:/myProject/folder/deep/myFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("http:/myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final URI input = URI.create("http:////myProject///folder///deep/myFile.ext//");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("http:////myProject///folder///deep/myFile.ext//");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlash() throws Exception {
		final URI input = URI.create("http:/myProject/myFolder/");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("http:/myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final URI input = URI.create("http:/myProject///myFolder");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("http:/myProject///myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void fragment() throws Exception {
		final URI input = URI.create("http:/myProject///myFolder#fragment");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("http:/myProject///myFolder#fragment");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void query() throws Exception {
		final URI input = URI.create("http:/myProject///myFolder?query");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("http:/myProject///myFolder?query");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void fragmentQuery() throws Exception {
		final URI input = URI.create("http:/myProject///myFolder?query#fragment");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("http:/myProject///myFolder?query#fragment");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeUri() throws Exception {
		final URI input = URI.create("mailto:/resource/...////");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("mailto:/resource/...////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFile() throws Exception {
		final URI input = URI.create("file:/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePath() throws Exception {
		final URI input = URI.create("file:/resource/..////");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:/resource/..////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeNestedFile() throws Exception {
		final URI input = URI.create("file:some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void inputNoSlashes() throws Exception {
		final URI input = URI.create("file:fasfasdf");
		final URL actual = ConversionUtils.asJavaUrl(input);
		final URL expected = new URL("file:fasfasdf");
		assertEquals(expected, actual);
	}
}
