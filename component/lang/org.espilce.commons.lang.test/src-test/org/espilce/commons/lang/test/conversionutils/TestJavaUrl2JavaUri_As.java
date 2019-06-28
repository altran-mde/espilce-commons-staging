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

import java.net.URI;
import java.net.URL;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUrl2JavaUri_As {
	@SuppressWarnings("null")
	@Test(expected = NullPointerException.class)
	public void uriNull() throws Exception {
		ConversionUtils.asJavaUri((URL) null);
	}
	
	@Test(expected = UnconvertibleException.class)
	public void empty() throws Exception {
		final URL uri = new URL("file:");
		ConversionUtils.asJavaUri(uri);
	}
	
	@Test
	public void emptyWithScheme() throws Exception {
		final URL uri = new URL("http:///");
		final URI javaUri = ConversionUtils.asJavaUri(uri);
		
		assertEquals(new URI("http:///"), javaUri);
	}
	
	@Test
	public void uriOther() throws Exception {
		final URL uri = new URL("https://example.com/MyFile.ext");
		final URI javaUri = ConversionUtils.asJavaUri(uri);
		
		assertEquals(new URI("https://example.com/MyFile.ext"), javaUri);
	}
	
	@Test
	public void file() throws Exception {
		final URL uri = new URL("file:MyFile.ext");
		assertEquals(new URI("file:MyFile.ext"), ConversionUtils.asJavaUri(uri));
	}
	
	@Test
	public void fileNested() throws Exception {
		final URL uri = new URL("http:/myProject/folder/deep/myFile.ext");
		final URI javaUri = ConversionUtils.asJavaUri(uri);
		
		assertEquals(new URI("http:/myProject/folder/deep/myFile.ext"), javaUri);
	}
	
	@Test
	public void fileSlashesExcess() throws Exception {
		final URL uri = new URL("http:////myProject///folder///deep/myFile.ext//");
		final URI javaUri = ConversionUtils.asJavaUri(uri);
		
		assertEquals(new URI("http:////myProject///folder///deep/myFile.ext//"), javaUri);
	}
	
	@Test
	public void folderSlash() throws Exception {
		final URL uri = new URL("http:/myProject/myFolder/");
		final URI javaUri = ConversionUtils.asJavaUri(uri);
		
		assertEquals(new URI("http:/myProject/myFolder/"), javaUri);
	}
	
	@Test
	public void folderSlashesInbetween() throws Exception {
		final URL uri = new URL("http:/myProject///myFolder");
		final URI javaUri = ConversionUtils.asJavaUri(uri);
		
		assertEquals(new URI("http:/myProject///myFolder"), javaUri);
	}
	
	@Test
	public void fragment() throws Exception {
		final URL uri = new URL("http:/myProject///myFolder#fragment");
		final URI javaUri = ConversionUtils.asJavaUri(uri);
		
		assertEquals(new URI("http:/myProject///myFolder#fragment"), javaUri);
	}
	
	@Test
	public void query() throws Exception {
		final URL uri = new URL("http:/myProject///myFolder?query");
		final URI javaUri = ConversionUtils.asJavaUri(uri);
		
		assertEquals(new URI("http:/myProject///myFolder?query"), javaUri);
	}
	
	@Test
	public void fragmentQuery() throws Exception {
		final URL uri = new URL("http:/myProject///myFolder?query#fragment");
		final URI javaUri = ConversionUtils.asJavaUri(uri);
		
		assertEquals(new URI("http:/myProject///myFolder?query#fragment"), javaUri);
	}
	
	@Test
	public void uriNoSlashes() throws Exception {
		final URL uri = new URL("file:fasfasdf");
		assertEquals(new URI("file:fasfasdf"), ConversionUtils.asJavaUri(uri));
	}
	
	@Test
	public void relativeUri() throws Exception {
		final URL uri = new URL("mailto:/resource/...////");
		final URI javaUri = ConversionUtils.asJavaUri(uri);
		
		assertEquals(new URI("mailto:/resource/...////"), javaUri);
	}
}
