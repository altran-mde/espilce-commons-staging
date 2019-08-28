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
import static org.junit.Assert.assertNull;

import java.io.File;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUrl2JavaFile_To {
	@Test
	public void fileNull() throws Exception {
		assertNull(ConversionUtils.toJavaFile((URL) null));
	}
	
	@Test
	public void emptyWithScheme() throws Exception {
		final URL url = new URL("file://");
		final File javaFile = ConversionUtils.toJavaFile(url);
		
		assertNull(javaFile);
	}
	
	@Test
	public void root() throws Exception {
		final URL url = new URL("file:/");
		final File javaFile = new File("/");
		
		assertEquals(javaFile, ConversionUtils.toJavaFile(url));
	}
	
	@Test
	public void absoluteNestedFile() throws Exception {
		final URL url = new URL("file:/some/path/MyFile.ext");
		final File javaFile = ConversionUtils.toJavaFile(url);
		
		assertEquals(new File("/some/path/MyFile.ext"), javaFile);
	}
	
	@Test
	public void file() throws Exception {
		final URL url = new URL("file:/MyFile.ext");
		final File javaFile = new File("/MyFile.ext");
		
		assertEquals(javaFile, ConversionUtils.toJavaFile(url));
	}
	
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final URL url = new URL("file:/c:/some/path/MyFile.ext");
		final File javaFile = ConversionUtils.toJavaFile(url);
		
		assertEquals(new File("c:/some/path/MyFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final URL url = new URL("file://c:/some/path/MyFile.ext");
		final File javaFile = ConversionUtils.toJavaFile(url);
		
		assertEquals(new File("c:/some/path/MyFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final URL url = new URL("file:///c:/some/path/MyFile.ext");
		final File javaFile = ConversionUtils.toJavaFile(url);
		
		assertEquals(new File("c:/some/path/MyFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteFileNested() throws Exception {
		final URL url = new URL("file:/path/to/myFile.ext");
		final File javaFile = ConversionUtils.toJavaFile(url);
		
		assertEquals(new File("/path/to/myFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final URL url = new URL("file:/myProject///folder///deep/myFile.ext//");
		final File javaFile = ConversionUtils.toJavaFile(url);
		
		assertEquals(new File("/myProject/folder/deep/myFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteFolderSlash() throws Exception {
		final URL url = new URL("file:/myProject/myFolder/");
		final File javaFile = ConversionUtils.toJavaFile(url);
		
		assertEquals(new File("/myProject/myFolder"), javaFile);
	}
	
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final URL url = new URL("file:/myProject///myFolder");
		final File javaFile = ConversionUtils.toJavaFile(url);
		
		assertEquals(new File("/myProject/myFolder"), javaFile);
	}
	
	@Test
	public void absoluteFragmentQuery() throws Exception {
		final URL url = new URL("file:/myProject///myFolder?query#fragment");
		assertNull(ConversionUtils.toJavaFile(url));
	}
	
	@Test
	public void path() throws Exception {
		final URL url = new URL("file:/resource/..////");
		final File javaFile = ConversionUtils.toJavaFile(url);
		
		assertEquals(new File("/resource/.."), javaFile);
	}
	
	@Test
	public void invalidProtocol() throws Exception {
		final URL url = new URL("http:/myProject/myFolder");
		assertNull(ConversionUtils.toJavaFile(url));
	}
}
