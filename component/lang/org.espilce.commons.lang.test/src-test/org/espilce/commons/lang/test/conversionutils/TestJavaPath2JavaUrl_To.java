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

import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaPath2JavaUrl_To {
	@Test
	public void fileNull() throws Exception {
		assertNull(ConversionUtils.toJavaUrl((Path) null));
	}
	
	@Test
	public void empty() throws Exception {
		final Path path = Paths.get("");
		final java.net.URL javaUrl = new java.net.URL("file:");
		
		assertEquals(javaUrl, ConversionUtils.toJavaUrl(path));
	}
	
	@Test
	public void slash() throws Exception {
		final Path path = Paths.get("/");
		final java.net.URL javaUrl = new java.net.URL("file:/");
		
		assertEquals(javaUrl, ConversionUtils.toJavaUrl(path));
	}
	
	@Test
	public void dot() throws Exception {
		final Path path = Paths.get(".");
		final java.net.URL javaUrl = new java.net.URL("file:.");
		
		assertEquals(javaUrl, ConversionUtils.toJavaUrl(path));
	}
	
	@Test
	public void dotSlash() throws Exception {
		final Path path = Paths.get("./");
		final java.net.URL javaUrl = new java.net.URL("file:.");
		
		assertEquals(javaUrl, ConversionUtils.toJavaUrl(path));
	}
	
	@Test
	public void relative() throws Exception {
		final Path path = Paths.get("..");
		final java.net.URL javaUrl = new java.net.URL("file:..");
		
		assertEquals(javaUrl, ConversionUtils.toJavaUrl(path));
	}
	
	@Test
	public void relativeMulti() throws Exception {
		final Path path = Paths.get("some/../where/");
		final java.net.URL javaUrl = new java.net.URL("file:some/../where");
		
		assertEquals(javaUrl, ConversionUtils.toJavaUrl(path));
	}
	
	@Test
	public void absoluteNestedFile() throws Exception {
		final Path path = Paths.get("//some/path/MyFile.ext");
		final java.net.URL javaUrl = ConversionUtils.toJavaUrl(path);
		
		assertEquals(new java.net.URL("file://some/path/MyFile.ext"), javaUrl);
	}
	
	@Test
	public void file() throws Exception {
		final Path path = Paths.get("MyFile.ext");
		final java.net.URL javaUrl = new java.net.URL("file:MyFile.ext");
		
		assertEquals(javaUrl, ConversionUtils.toJavaUrl(path));
	}
	
	@Test
	public void absoluteFileNested() throws Exception {
		final Path path = Paths.get("//path/to/myFile.ext");
		final java.net.URL javaUrl = ConversionUtils.toJavaUrl(path);
		
		assertEquals(new java.net.URL("file://path/to/myFile.ext"), javaUrl);
	}
	
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final Path path = Paths.get("//myProject///folder///deep/myFile.ext//");
		final java.net.URL javaUrl = ConversionUtils.toJavaUrl(path);
		
		assertEquals(new java.net.URL("file://myProject/folder/deep/myFile.ext"), javaUrl);
	}
	
	@Test
	public void absoluteFolderSlash() throws Exception {
		final Path path = Paths.get("//myProject/myFolder/");
		final java.net.URL javaUrl = ConversionUtils.toJavaUrl(path);
		
		assertEquals(new java.net.URL("file://myProject/myFolder/"), javaUrl);
	}
	
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final Path path = Paths.get("//myProject///myFolder");
		final java.net.URL javaUrl = ConversionUtils.toJavaUrl(path);
		
		assertEquals(new java.net.URL("file://myProject/myFolder/"), javaUrl);
	}
	
	@Test
	public void absoluteFragment() throws Exception {
		final Path path = Paths.get("//myProject///myFolder#query");
		final java.net.URL javaUrl = ConversionUtils.toJavaUrl(path);
		
		assertEquals(new java.net.URL("file://myProject/myFolder%23query/"), javaUrl);
	}
	
	@Test
	public void path() throws Exception {
		final Path path = Paths.get("//resource/..////");
		final java.net.URL javaUrl = ConversionUtils.toJavaUrl(path);
		
		assertEquals(new java.net.URL("file://resource/../"), javaUrl);
	}
	
}
