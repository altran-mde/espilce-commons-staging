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

import java.io.File;
import java.net.URL;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUrl2JavaFile_As {
	@SuppressWarnings("null")
	@Test(expected = NullPointerException.class)
	public void fileNull() throws Exception {
		ConversionUtils.asJavaFile((URL) null);
	}
	
	@Test
	public void root() throws Exception {
		final URL url = new URL("file:/");
		final File javaFile = new File("/");
		
		assertEquals(javaFile, ConversionUtils.asJavaFile(url));
	}
	
	@Test
	public void empty() throws Exception {
		final URL url = new URL("file:");
		final File javaFile = new File("");
		
		assertEquals(javaFile, ConversionUtils.asJavaFile(url));
	}
	
	@Test
	public void absoluteNestedFile() throws Exception {
		final URL url = new URL("file:/some/path/MyFile.ext");
		final File javaFile = ConversionUtils.asJavaFile(url);
		
		assertEquals(new File("/some/path/MyFile.ext"), javaFile);
	}
	
	@Test
	public void relativeNestedFile() throws Exception {
		final URL url = new URL("file:some/path/MyFile.ext");
		final File javaFile = ConversionUtils.asJavaFile(url);
		
		assertEquals(new File("some/path/MyFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteFile() throws Exception {
		final URL url = new URL("file:/MyFile.ext");
		final File javaFile = new File("/MyFile.ext");
		
		assertEquals(javaFile, ConversionUtils.asJavaFile(url));
	}
	
	@Test
	public void relativeFile() throws Exception {
		final URL url = new URL("file:MyFile.ext");
		final File javaFile = new File("MyFile.ext");
		
		assertEquals(javaFile, ConversionUtils.asJavaFile(url));
	}
	
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final URL url = new URL("file:/myProject///folder///deep/myFile.ext//");
		final File javaFile = ConversionUtils.asJavaFile(url);
		
		assertEquals(new File("/myProject/folder/deep/myFile.ext"), javaFile);
	}
	
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final URL url = new URL("file:myProject///folder///deep/myFile.ext//");
		final File javaFile = ConversionUtils.asJavaFile(url);
		
		assertEquals(new File("myProject/folder/deep/myFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteFolderSlash() throws Exception {
		final URL url = new URL("file:/myProject/myFolder/");
		final File javaFile = ConversionUtils.asJavaFile(url);
		
		assertEquals(new File("/myProject/myFolder"), javaFile);
	}
	
	@Test
	public void relativeFolderSlash() throws Exception {
		final URL url = new URL("file:myProject/myFolder/");
		final File javaFile = ConversionUtils.asJavaFile(url);
		
		assertEquals(new File("myProject/myFolder"), javaFile);
	}
	
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final URL url = new URL("file:/myProject///myFolder");
		final File javaFile = ConversionUtils.asJavaFile(url);
		
		assertEquals(new File("/myProject/myFolder"), javaFile);
	}
	
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final URL url = new URL("file:myProject///myFolder");
		final File javaFile = ConversionUtils.asJavaFile(url);
		
		assertEquals(new File("myProject/myFolder"), javaFile);
	}
	
	@Test(expected = UnconvertibleException.class)
	public void absoluteFragmentQuery() throws Exception {
		final URL url = new URL("file:/myProject///myFolder?query#fragment");
		ConversionUtils.asJavaFile(url);
	}
	
	@Test(expected = UnconvertibleException.class)
	public void relativeFragmentQuery() throws Exception {
		final URL url = new URL("file:myProject///myFolder?query#fragment");
		ConversionUtils.asJavaFile(url);
	}
	
	@Test
	public void absolutePath() throws Exception {
		final URL url = new URL("file:/resource/..////");
		final File javaFile = ConversionUtils.asJavaFile(url);
		
		assertEquals(new File("/resource/.."), javaFile);
	}
	
	@Test
	public void relativePath() throws Exception {
		final URL url = new URL("file:resource/..////");
		final File javaFile = ConversionUtils.asJavaFile(url);
		
		assertEquals(new File("resource/.."), javaFile);
	}
	
	@Test
	public void multiRelativePath() throws Exception {
		final URL url = new URL("file:resource/../some/dir/../../file.ext");
		final File javaFile = ConversionUtils.asJavaFile(url);
		
		assertEquals(new File("resource/../some/dir/../../file.ext"), javaFile);
	}
	
	@Test(expected = UnconvertibleException.class)
	public void invalidProtocol() throws Exception {
		final URL url = new URL("http:/myProject/myFolder");
		ConversionUtils.asJavaFile(url);
	}
}
