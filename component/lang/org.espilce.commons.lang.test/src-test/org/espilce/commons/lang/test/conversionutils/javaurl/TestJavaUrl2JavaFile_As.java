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

import java.io.File;
import java.net.URL;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUrl2JavaFile_As implements TestJavaUrl2JavaFile {
	@Override
	@SuppressWarnings("null")
	@Test(expected = NullPointerException.class)
	public void paramNull() throws Exception {
		final URL input = (URL) null;
		ConversionUtils.asJavaFile(input);
	}
	
	@Override
	@Test
	public void root() throws Exception {
		final URL input = new URL("file:/");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void empty() throws Exception {
		final URL input = new URL("file:");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteNestedFile() throws Exception {
		final URL input = new URL("file:/some/path/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeNestedFile() throws Exception {
		final URL input = new URL("file:some/path/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFile() throws Exception {
		final URL input = new URL("file:/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final URL input = new URL("file:/c:/some/path/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final URL input = new URL("file://c:/some/path/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final URL input = new URL("file:///c:/some/path/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFile() throws Exception {
		final URL input = new URL("file:MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final URL input = new URL("file:/myProject///folder///deep/myFile.ext//");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("/myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final URL input = new URL("file:myProject///folder///deep/myFile.ext//");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlash() throws Exception {
		final URL input = new URL("file:/myProject/myFolder/");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("/myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlash() throws Exception {
		final URL input = new URL("file:myProject/myFolder/");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final URL input = new URL("file:/myProject///myFolder");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("/myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final URL input = new URL("file:myProject///myFolder");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void absoluteFragmentQuery() throws Exception {
		final URL input = new URL("file:/myProject///myFolder?query#fragment");
		ConversionUtils.asJavaFile(input);
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void relativeFragmentQuery() throws Exception {
		final URL input = new URL("file:myProject///myFolder?query#fragment");
		ConversionUtils.asJavaFile(input);
	}
	
	@Override
	@Test
	public void absolutePath() throws Exception {
		final URL input = new URL("file:/resource/..////");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("/resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePath() throws Exception {
		final URL input = new URL("file:resource/..////");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void multiRelativePath() throws Exception {
		final URL input = new URL("file:resource/../some/dir/../../file.ext");
		final File actual = ConversionUtils.asJavaFile(input);
		final File expected = new File("resource/../some/dir/../../file.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void invalidProtocol() throws Exception {
		final URL input = new URL("http:/myProject/myFolder");
		ConversionUtils.asJavaFile(input);
	}
}
