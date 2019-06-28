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
import static org.junit.Assert.assertNull;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaPath2JavaUri_To {
	@Test
	public void fileNull() throws Exception {
		assertNull(ConversionUtils.toJavaUri((Path) null));
	}
	
	@Test
	public void empty() throws Exception {
		final Path path = Paths.get("");
		final URI javaUri = new URI("file:/" + System.getProperty("user.dir").replace('\\', '/') + "/");
		
		assertEquals(javaUri, ConversionUtils.toJavaUri(path));
	}
	
	@Test
	public void absoluteNestedFile() throws Exception {
		final Path path = Paths.get("//some/path/MyFile.ext");
		final URI javaUri = ConversionUtils.toJavaUri(path);
		
		assertEquals(new URI("file://some/path/MyFile.ext"), javaUri);
	}
	
	@Test
	public void file() throws Exception {
		final Path path = Paths.get("MyFile.ext");
		final URI javaUri = new URI("file:/" + System.getProperty("user.dir").replace('\\', '/') + "/MyFile.ext");
		
		assertEquals(javaUri, ConversionUtils.toJavaUri(path));
	}
	
	@Test
	public void absoluteFileNested() throws Exception {
		final Path path = Paths.get("//path/to/myFile.ext");
		final URI javaUri = ConversionUtils.toJavaUri(path);
		
		assertEquals(new URI("file://path/to/myFile.ext"), javaUri);
	}
	
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final Path path = Paths.get("//myProject///folder///deep/myFile.ext//");
		final URI javaUri = ConversionUtils.toJavaUri(path);
		
		assertEquals(new URI("file://myProject/folder/deep/myFile.ext"), javaUri);
	}
	
	@Test
	public void absoluteFolderSlash() throws Exception {
		final Path path = Paths.get("//myProject/myFolder/");
		final URI javaUri = ConversionUtils.toJavaUri(path);
		
		assertEquals(new URI("file://myProject/myFolder/"), javaUri);
	}
	
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final Path path = Paths.get("//myProject///myFolder");
		final URI javaUri = ConversionUtils.toJavaUri(path);
		
		assertEquals(new URI("file://myProject/myFolder/"), javaUri);
	}
	
	@Test
	public void absoluteFragment() throws Exception {
		final Path path = Paths.get("//myProject///myFolder#query");
		final URI javaUri = ConversionUtils.toJavaUri(path);
		
		assertEquals(new URI("file://myProject/myFolder%23query/"), javaUri);
	}
	
	@Test
	public void path() throws Exception {
		final Path path = Paths.get("//resource/..////");
		final URI javaUri = ConversionUtils.toJavaUri(path);
		
		assertEquals(new URI("file://resource/../"), javaUri);
	}
	
}
