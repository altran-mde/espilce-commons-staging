/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.test.uriutils;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.UriUtils;
import org.espilce.commons.exception.UnconvertibleException;
import org.junit.Test;

public class TestEmfUri2JavaUri_As {
	@Test(expected = NullPointerException.class)
	public void uriNull() throws Exception {
		UriUtils.asJavaUri(null);
	}
	
	@Test
	public void empty() throws Exception {
		final URI uri = URI.createURI("");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI(""), javaUri);
	}
	
	@Test(expected = UnconvertibleException.class)
	public void emptyWithScheme() throws Exception {
		final URI uri = URI.createURI("http://");
		UriUtils.asJavaUri(uri);
	}
	
	@Test
	public void uriOther() throws Exception {
		final URI uri = URI.createURI("https://example.com/MyFile.ext");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI("https://example.com/MyFile.ext"), javaUri);
	}
	
	@Test
	public void file() throws Exception {
		final URI uri = URI.createURI("MyFile.ext");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI("MyFile.ext"), javaUri);
	}
	
	@Test
	public void fileNested() throws Exception {
		final URI uri = URI.createURI("/myProject/folder/deep/myFile.ext");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI("/myProject/folder/deep/myFile.ext"), javaUri);
	}
	
	@Test
	public void fileSlashesExcess() throws Exception {
		final URI uri = URI.createURI("////myProject///folder///deep/myFile.ext//");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI("////myProject///folder///deep/myFile.ext//"), javaUri);
	}
	
	@Test
	public void folderSlash() throws Exception {
		final URI uri = URI.createURI("/myProject/myFolder/");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI("/myProject/myFolder/"), javaUri);
	}
	
	@Test
	public void folderSlashesInbetween() throws Exception {
		final URI uri = URI.createURI("/myProject///myFolder");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI("/myProject///myFolder"), javaUri);
	}
	
	@Test
	public void fragment() throws Exception {
		final URI uri = URI.createURI("/myProject///myFolder").appendFragment("fragment");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI("/myProject///myFolder#fragment"), javaUri);
	}
	
	@Test
	public void query() throws Exception {
		final URI uri = URI.createURI("/myProject///myFolder").appendQuery("query");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI("/myProject///myFolder?query"), javaUri);
	}
	
	@Test
	public void fragmentQuery() throws Exception {
		final URI uri = URI.createURI("/myProject///myFolder").appendFragment("fragment").appendQuery("query");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI("/myProject///myFolder?query#fragment"), javaUri);
	}
	
	@Test
	public void uriBroken() throws Exception {
		final URI uri = URI.createURI("fasfasdf");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI("fasfasdf"), javaUri);
	}
	
	@Test
	public void uriPlatformResourceBroken() throws Exception {
		final URI uri = URI.createURI("platform:/resource/...////");
		final java.net.URI javaUri = UriUtils.asJavaUri(uri);
		
		assertEquals(new java.net.URI("platform:/resource/...////"), javaUri);
	}
}
