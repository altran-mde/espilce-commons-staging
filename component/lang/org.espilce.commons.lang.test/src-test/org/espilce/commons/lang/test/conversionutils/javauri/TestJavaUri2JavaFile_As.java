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

import java.io.File;
import java.net.URI;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUri2JavaFile_As {
	@SuppressWarnings("null")
	@Test(expected = NullPointerException.class)
	public void fileNull() throws Exception {
		ConversionUtils.asJavaFile((URI) null);
	}
	
	@Test
	public void root() throws Exception {
		final URI uri = new URI("file:/");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("/"), javaFile);
	}
	
	@Test
	public void absoluteNestedFile() throws Exception {
		final URI uri = new URI("file:/some/path/MyFile.ext");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("/some/path/MyFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final URI uri = new URI("file:/c:/some/path/MyFile.ext");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("c:/some/path/MyFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final URI uri = new URI("file://c:/some/path/MyFile.ext");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("c:/some/path/MyFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final URI uri = new URI("file:///c:/some/path/MyFile.ext");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("c:/some/path/MyFile.ext"), javaFile);
	}
	
	@Test
	public void relativeNestedFile() throws Exception {
		final URI uri = new URI("file:some/path/MyFile.ext");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("some/path/MyFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteFile() throws Exception {
		final URI uri = new URI("file:/MyFile.ext");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("/MyFile.ext"), javaFile);
	}
	
	@Test
	public void relativeFile() throws Exception {
		final URI uri = new URI("file:MyFile.ext");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("MyFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final URI uri = new URI("file:/myProject///folder///deep/myFile.ext//");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("/myProject/folder/deep/myFile.ext"), javaFile);
	}
	
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final URI uri = new URI("file:myProject///folder///deep/myFile.ext//");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("myProject/folder/deep/myFile.ext"), javaFile);
	}
	
	@Test
	public void absoluteFolderSlash() throws Exception {
		final URI uri = new URI("file:/myProject/myFolder/");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("/myProject/myFolder"), javaFile);
	}
	
	@Test
	public void relativeFolderSlash() throws Exception {
		final URI uri = new URI("file:myProject/myFolder/");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("myProject/myFolder"), javaFile);
	}
	
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final URI uri = new URI("file:/myProject///myFolder");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("/myProject/myFolder"), javaFile);
	}
	
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final URI uri = new URI("file:myProject///myFolder");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("myProject/myFolder"), javaFile);
	}
	
	@Test(expected = UnconvertibleException.class)
	public void absoluteFragmentQuery() throws Exception {
		final URI uri = new URI("file:/myProject///myFolder?query#fragment");
		ConversionUtils.asJavaFile(uri);
	}
	
	@Test(expected = UnconvertibleException.class)
	public void relativeFragmentQuery() throws Exception {
		final URI uri = new URI("file:myProject///myFolder?query#fragment");
		ConversionUtils.asJavaFile(uri);
	}
	
	@Test
	public void absolutePath() throws Exception {
		final URI uri = new URI("file:/resource/..////");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("/resource/.."), javaFile);
	}
	
	@Test
	public void relativePath() throws Exception {
		final URI uri = new URI("file:resource/..////");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("resource/.."), javaFile);
	}
	
	@Test
	public void multiRelativePath() throws Exception {
		final URI uri = new URI("file:resource/../some/dir/../../file.ext");
		final File javaFile = ConversionUtils.asJavaFile(uri);
		
		assertEquals(new File("resource/../some/dir/../../file.ext"), javaFile);
	}
	
	@Test(expected = UnconvertibleException.class)
	public void invalidProtocol() throws Exception {
		final URI uri = new URI("http:/myProject/myFolder");
		ConversionUtils.asJavaFile(uri);
	}
}
