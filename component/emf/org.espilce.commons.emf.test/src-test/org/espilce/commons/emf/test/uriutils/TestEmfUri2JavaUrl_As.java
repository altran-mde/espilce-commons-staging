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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.UriUtils;
import org.espilce.commons.exception.UnconvertibleException;
import org.junit.jupiter.api.Test;

public class TestEmfUri2JavaUrl_As {
	@SuppressWarnings("null")
	@Test
	public void uriNull() throws Exception {
		assertThrows(NullPointerException.class, () -> UriUtils.asJavaUrl((URI) null));
	}
	
	@Test
	public void empty() throws Exception {
		final URI uri = URI.createURI("");
		assertThrows(UnconvertibleException.class, () -> UriUtils.asJavaUrl(uri));
	}
	
	@Test
	public void emptyWithScheme() throws Exception {
		final URI uri = URI.createURI("http://");
		final java.net.URL javaUrl = UriUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http://"), javaUrl);
	}
	
	@Test
	public void uriOther() throws Exception {
		final URI uri = URI.createURI("https://example.com/MyFile.ext");
		final java.net.URL javaUrl = UriUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("https://example.com/MyFile.ext"), javaUrl);
	}
	
	@Test
	public void file() throws Exception {
		final URI uri = URI.createURI("MyFile.ext");
		assertThrows(UnconvertibleException.class, () -> UriUtils.asJavaUrl(uri));
	}
	
	@Test
	public void fileNested() throws Exception {
		final URI uri = URI.createURI("http:/myProject/folder/deep/myFile.ext");
		final java.net.URL javaUrl = UriUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject/folder/deep/myFile.ext"), javaUrl);
	}
	
	@Test
	public void fileSlashesExcess() throws Exception {
		final URI uri = URI.createURI("http:////myProject///folder///deep/myFile.ext//");
		final java.net.URL javaUrl = UriUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:////myProject///folder///deep/myFile.ext//"), javaUrl);
	}
	
	@Test
	public void folderSlash() throws Exception {
		final URI uri = URI.createURI("http:/myProject/myFolder/");
		final java.net.URL javaUrl = UriUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject/myFolder/"), javaUrl);
	}
	
	@Test
	public void folderSlashesInbetween() throws Exception {
		final URI uri = URI.createURI("http:/myProject///myFolder");
		final java.net.URL javaUrl = UriUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject///myFolder"), javaUrl);
	}
	
	@Test
	public void fragment() throws Exception {
		final URI uri = URI.createURI("http:/myProject///myFolder").appendFragment("fragment");
		final java.net.URL javaUrl = UriUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject///myFolder#fragment"), javaUrl);
	}
	
	@Test
	public void query() throws Exception {
		final URI uri = URI.createURI("http:/myProject///myFolder").appendQuery("query");
		final java.net.URL javaUrl = UriUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject///myFolder?query"), javaUrl);
	}
	
	@Test
	public void fragmentQuery() throws Exception {
		final URI uri = URI.createURI("http:/myProject///myFolder").appendFragment("fragment").appendQuery("query");
		final java.net.URL javaUrl = UriUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("http:/myProject///myFolder?query#fragment"), javaUrl);
	}
	
	@Test
	public void uriBroken() throws Exception {
		final URI uri = URI.createURI("fasfasdf");
		assertThrows(UnconvertibleException.class, () -> UriUtils.asJavaUrl(uri));
	}
	
	@Test
	public void relativeUri() throws Exception {
		final URI uri = URI.createURI("mailto:/resource/...////");
		final java.net.URL javaUrl = UriUtils.asJavaUrl(uri);
		
		assertEquals(new java.net.URL("mailto:/resource/...////"), javaUrl);
	}
}
