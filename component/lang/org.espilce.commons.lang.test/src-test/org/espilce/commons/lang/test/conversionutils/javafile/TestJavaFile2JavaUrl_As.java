/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javafile;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaFile2JavaUrl_As {
	@SuppressWarnings("null")
	@Test(expected = NullPointerException.class)
	public void fileNull() throws Exception {
		ConversionUtils.asJavaUrl((File) null);
	}
	
	@Test
	public void empty() throws Exception {
		final File file = new File("");
		final java.net.URL javaUrl = new java.net.URL("file:/" + System.getProperty("user.dir") + "/");
		
		assertEquals(javaUrl, ConversionUtils.asJavaUrl(file));
	}
	
	@Test
	public void absoluteNestedFile() throws Exception {
		final File file = new File("//some/path/MyFile.ext");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(file);
		
		assertEquals(new java.net.URL("file:////some/path/MyFile.ext"), javaUrl);
	}
	
	@Test
	public void file() throws Exception {
		final File file = new File("MyFile.ext");
		final java.net.URL javaUrl = new java.net.URL("file:/" + System.getProperty("user.dir") + "/MyFile.ext");
		
		assertEquals(javaUrl, ConversionUtils.asJavaUrl(file));
	}
	
	@Test
	public void absoluteFileNested() throws Exception {
		final File file = new File("//path/to/myFile.ext");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(file);
		
		assertEquals(new java.net.URL("file:////path/to/myFile.ext"), javaUrl);
	}
	
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final File file = new File("//myProject///folder///deep/myFile.ext//");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(file);
		
		assertEquals(new java.net.URL("file:////myProject/folder/deep/myFile.ext"), javaUrl);
	}
	
	@Test
	public void absoluteFolderSlash() throws Exception {
		final File file = new File("//myProject/myFolder/");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(file);
		
		assertEquals(new java.net.URL("file:////myProject/myFolder"), javaUrl);
	}
	
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final File file = new File("//myProject///myFolder");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(file);
		
		assertEquals(new java.net.URL("file:////myProject/myFolder"), javaUrl);
	}
	
	@Test
	public void absoluteFragmentQuery() throws Exception {
		final File file = new File("//myProject///myFolder?query#fragment");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(file);
		
		assertEquals(new java.net.URL("file:////myProject/myFolder%3Fquery%23fragment"), javaUrl);
	}
	
	@Test
	public void path() throws Exception {
		final File file = new File("//resource/..////");
		final java.net.URL javaUrl = ConversionUtils.asJavaUrl(file);
		
		assertEquals(new java.net.URL("file:////resource/.."), javaUrl);
	}
}
