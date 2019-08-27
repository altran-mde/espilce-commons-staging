/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URI;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUrl2JavaUri_To {
	@Test
	public void uriNull() throws Exception {
		assertNull(ConversionUtils.toJavaUri((URL) null));
	}
	
	@Test
	public void empty() throws Exception {
		final URL uri = new URL("file:");
		assertNull(ConversionUtils.toJavaUri(uri));
	}
	
	@Test
	public void emptyWithScheme() throws Exception {
		final URL uri = new URL("http:///");
		final URI javaUri = ConversionUtils.toJavaUri(uri);
		
		assertEquals(new URI("http:///"), javaUri);
	}
	
	@Test
	public void uriOther() throws Exception {
		final URL uri = new URL("https://example.com/MyFile.ext");
		final URI javaUri = ConversionUtils.toJavaUri(uri);
		
		assertEquals(new URI("https://example.com/MyFile.ext"), javaUri);
	}
	
	@Test
	public void file() throws Exception {
		final URL uri = new URL("file:MyFile.ext");
		assertEquals(new URI("file:MyFile.ext"), ConversionUtils.toJavaUri(uri));
	}
	
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final URL url = new URL("file:/c:/some/path/MyFile.ext");
		final URI javaUri = ConversionUtils.toJavaUri(url);
		
		assertEquals(new URI("file:/c:/some/path/MyFile.ext"), javaUri);
	}
	
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final URL url = new URL("file://c:/some/path/MyFile.ext");
		final URI javaUri = ConversionUtils.toJavaUri(url);
		
		assertEquals(new URI("file:/c:/some/path/MyFile.ext"), javaUri);
	}
	
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final URL url = new URL("file:///c:/some/path/MyFile.ext");
		final URI javaUri = ConversionUtils.toJavaUri(url);
		
		assertEquals(new URI("file:/c:/some/path/MyFile.ext"), javaUri);
	}
	
	@Test
	public void fileNested() throws Exception {
		final URL uri = new URL("http:/myProject/folder/deep/myFile.ext");
		final URI javaUri = ConversionUtils.toJavaUri(uri);
		
		assertEquals(new URI("http:/myProject/folder/deep/myFile.ext"), javaUri);
	}
	
	@Test
	public void fileSlashesExcess() throws Exception {
		final URL uri = new URL("http:////myProject///folder///deep/myFile.ext//");
		final URI javaUri = ConversionUtils.toJavaUri(uri);
		
		assertEquals(new URI("http:////myProject///folder///deep/myFile.ext//"), javaUri);
	}
	
	@Test
	public void folderSlash() throws Exception {
		final URL uri = new URL("http:/myProject/myFolder/");
		final URI javaUri = ConversionUtils.toJavaUri(uri);
		
		assertEquals(new URI("http:/myProject/myFolder/"), javaUri);
	}
	
	@Test
	public void folderSlashesInbetween() throws Exception {
		final URL uri = new URL("http:/myProject///myFolder");
		final URI javaUri = ConversionUtils.toJavaUri(uri);
		
		assertEquals(new URI("http:/myProject///myFolder"), javaUri);
	}
	
	@Test
	public void fragment() throws Exception {
		final URL uri = new URL("http:/myProject///myFolder#fragment");
		final URI javaUri = ConversionUtils.toJavaUri(uri);
		
		assertEquals(new URI("http:/myProject///myFolder#fragment"), javaUri);
	}
	
	@Test
	public void query() throws Exception {
		final URL uri = new URL("http:/myProject///myFolder?query");
		final URI javaUri = ConversionUtils.toJavaUri(uri);
		
		assertEquals(new URI("http:/myProject///myFolder?query"), javaUri);
	}
	
	@Test
	public void fragmentQuery() throws Exception {
		final URL uri = new URL("http:/myProject///myFolder?query#fragment");
		final URI javaUri = ConversionUtils.toJavaUri(uri);
		
		assertEquals(new URI("http:/myProject///myFolder?query#fragment"), javaUri);
	}
	
	@Test
	public void uriNoSlashes() throws Exception {
		final URL uri = new URL("file:fasfasdf");
		assertEquals(new URI("file:fasfasdf"), ConversionUtils.toJavaUri(uri));
	}
	
	@Test
	public void relativeUri() throws Exception {
		final URL uri = new URL("mailto:/resource/...////");
		final URI javaUri = ConversionUtils.toJavaUri(uri);
		
		assertEquals(new URI("mailto:/resource/...////"), javaUri);
	}
}
