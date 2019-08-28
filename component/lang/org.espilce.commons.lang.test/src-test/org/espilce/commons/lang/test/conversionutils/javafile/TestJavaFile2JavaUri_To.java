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
import static org.junit.Assert.assertNull;

import java.io.File;
import java.net.URI;

import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaFile2JavaUri_To {
	@Test
	public void fileNull() throws Exception {
		assertNull(ConversionUtils.toJavaUri((File) null));
	}
	
	@Test
	public void empty() throws Exception {
		final File file = new File("");
		final URI javaUri = new URI("file:/" + System.getProperty("user.dir").replace('\\', '/') + "/");
		
		assertEquals(javaUri, ConversionUtils.toJavaUri(file));
	}
	
	@Test
	public void absoluteNestedFile() throws Exception {
		final File file = new File("//some/path/MyFile.ext");
		final URI javaUri = ConversionUtils.toJavaUri(file);
		
		assertEquals(new URI("file:////some/path/MyFile.ext"), javaUri);
	}
	
	@Test
	public void file() throws Exception {
		final File file = new File("MyFile.ext");
		final URI javaUri = new URI("file:/" + System.getProperty("user.dir").replace('\\', '/') + "/MyFile.ext");
		
		assertEquals(javaUri, ConversionUtils.toJavaUri(file));
	}
	
	@Test
	public void absoluteFileNested() throws Exception {
		final File file = new File("//path/to/myFile.ext");
		final URI javaUri = ConversionUtils.toJavaUri(file);
		
		assertEquals(new URI("file:////path/to/myFile.ext"), javaUri);
	}
	
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final File file = new File("//myProject///folder///deep/myFile.ext//");
		final URI javaUri = ConversionUtils.toJavaUri(file);
		
		assertEquals(new URI("file:////myProject/folder/deep/myFile.ext"), javaUri);
	}
	
	@Test
	public void absoluteFolderSlash() throws Exception {
		final File file = new File("//myProject/myFolder/");
		final URI javaUri = ConversionUtils.toJavaUri(file);
		
		assertEquals(new URI("file:////myProject/myFolder"), javaUri);
	}
	
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final File file = new File("//myProject///myFolder");
		final URI javaUri = ConversionUtils.toJavaUri(file);
		
		assertEquals(new URI("file:////myProject/myFolder"), javaUri);
	}
	
	@Test
	public void absoluteFragmentQuery() throws Exception {
		final File file = new File("//myProject///myFolder?query#fragment");
		final URI javaUri = ConversionUtils.toJavaUri(file);
		
		assertEquals(new URI("file:////myProject/myFolder%3Fquery%23fragment"), javaUri);
	}
	
	@Test
	public void path() throws Exception {
		final File file = new File("//resource/..////");
		final URI javaUri = ConversionUtils.toJavaUri(file);
		
		assertEquals(new URI("file:////resource/.."), javaUri);
	}
}
