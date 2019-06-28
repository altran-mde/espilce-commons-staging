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

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUri2JavaUrl_As {
	@SuppressWarnings("null")
	@Test(expected = NullPointerException.class)
	public void uriNull() throws Exception {
		ConversionUtils.asJavaUrl((URI) null);
	}
	
	@Test(expected = UnconvertibleException.class)
	public void empty() throws Exception {
		final URI uri = URI.create("");
		ConversionUtils.asJavaUrl(uri);
	}
	
	@Test
	public void emptyWithScheme() throws Exception {
		final URI uri = URI.create("http:///");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:///"), javaUrl);
	}
	
	@Test
	public void uriOther() throws Exception {
		final URI uri = URI.create("https://example.com/MyFile.ext");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("https://example.com/MyFile.ext"), javaUrl);
	}
	
	@Test(expected = UnconvertibleException.class)
	public void file() throws Exception {
		final URI uri = URI.create("MyFile.ext");
		ConversionUtils.asJavaUrl(uri);
	}
	
	@Test
	public void fileNested() throws Exception {
		final URI uri = URI.create("http:/myProject/folder/deep/myFile.ext");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject/folder/deep/myFile.ext"), javaUrl);
	}
	
	@Test
	public void fileSlashesExcess() throws Exception {
		final URI uri = URI.create("http:////myProject///folder///deep/myFile.ext//");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:////myProject///folder///deep/myFile.ext//"), javaUrl);
	}
	
	@Test
	public void folderSlash() throws Exception {
		final URI uri = URI.create("http:/myProject/myFolder/");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject/myFolder/"), javaUrl);
	}
	
	@Test
	public void folderSlashesInbetween() throws Exception {
		final URI uri = URI.create("http:/myProject///myFolder");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject///myFolder"), javaUrl);
	}
	
	@Test
	public void fragment() throws Exception {
		final URI uri = URI.create("http:/myProject///myFolder#fragment");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject///myFolder#fragment"), javaUrl);
	}
	
	@Test
	public void query() throws Exception {
		final URI uri = URI.create("http:/myProject///myFolder?query");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject///myFolder?query"), javaUrl);
	}
	
	@Test
	public void fragmentQuery() throws Exception {
		final URI uri = URI.create("http:/myProject///myFolder?query#fragment");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject///myFolder?query#fragment"), javaUrl);
	}
	
	@Test(expected = UnconvertibleException.class)
	public void uriBroken() throws Exception {
		final URI uri = URI.create("fasfasdf");
		ConversionUtils.asJavaUrl(uri);
	}
	
	@Test
	public void relativeUri() throws Exception {
		final URI uri = URI.create("mailto:/resource/...////");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("mailto:/resource/...////"), javaUrl);
	}
}
