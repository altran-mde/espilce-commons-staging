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

public class TestJavaUri2JavaPath_To {
	@Test
	public void fileNull() throws Exception {
		final Path javaPath = ConversionUtils.toJavaPath((URI) null);
		
		assertNull(javaPath);
	}
	
	@Test
	public void root() throws Exception {
		final URI uri = new URI("file:/");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("/"), javaPath);
	}
	
	@Test
	public void rootScheme() throws Exception {
		final URI uri = new URI("file", "/", null);
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("/"), javaPath);
	}
	
	@Test
	public void rootNoScheme() throws Exception {
		final URI uri = new URI(null, "/", null);
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("/"), javaPath);
	}
	
	@Test
	public void emptyNoScheme() throws Exception {
		final URI uri = new URI(null, "", null);
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get(""), javaPath);
	}
	
	@Test
	public void current() throws Exception {
		final URI uri = new URI("file", ".", null);
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("."), javaPath);
	}
	
	@Test
	public void currentNoScheme() throws Exception {
		final URI uri = new URI(null, ".", null);
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("."), javaPath);
	}
	
	@Test
	public void currentSlash() throws Exception {
		final URI uri = new URI("file", "./", null);
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("./"), javaPath);
	}
	
	@Test
	public void currentSlashNoScheme() throws Exception {
		final URI uri = new URI(null, "./", null);
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("./"), javaPath);
	}
	
	@Test
	public void absoluteNestedFile() throws Exception {
		final URI uri = new URI("file:/some/path/MyFile.ext");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("/some/path/MyFile.ext"), javaPath);
	}
	
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final URI uri = new URI("file:/c:/some/path/MyFile.ext");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("c:/some/path/MyFile.ext"), javaPath);
	}
	
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final URI uri = new URI("file://c:/some/path/MyFile.ext");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("c:/some/path/MyFile.ext"), javaPath);
	}
	
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final URI uri = new URI("file:///c:/some/path/MyFile.ext");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("c:/some/path/MyFile.ext"), javaPath);
	}
	
	@Test
	public void absoluteNestedFileNoScheme() throws Exception {
		final URI uri = new URI(null, "/some/path/MyFile.ext", null);
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("/some/path/MyFile.ext"), javaPath);
	}
	
	@Test
	public void relativeNestedFile() throws Exception {
		final URI uri = new URI("file:some/path/MyFile.ext");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("some/path/MyFile.ext"), javaPath);
	}
	
	@Test
	public void relativeNestedFileNoScheme() throws Exception {
		final URI uri = new URI(null, "some/path/MyFile.ext", null);
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("some/path/MyFile.ext"), javaPath);
	}
	
	@Test
	public void currentRelativeNestedFile() throws Exception {
		final URI uri = new URI("file:./some/path/MyFile.ext");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("./some/path/MyFile.ext"), javaPath);
	}
	
	@Test
	public void currentRelativeNestedFileNoScheme() throws Exception {
		final URI uri = new URI(null, "./some/path/MyFile.ext", null);
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("./some/path/MyFile.ext"), javaPath);
	}
	
	@Test
	public void absoluteFile() throws Exception {
		final URI uri = new URI("file:/MyFile.ext");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("/MyFile.ext"), javaPath);
	}
	
	@Test
	public void relativeFile() throws Exception {
		final URI uri = new URI("file:MyFile.ext");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("MyFile.ext"), javaPath);
	}
	
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final URI uri = new URI("file:/myProject///folder///deep/myFile.ext//");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("/myProject/folder/deep/myFile.ext"), javaPath);
	}
	
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final URI uri = new URI("file:myProject///folder///deep/myFile.ext//");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("myProject/folder/deep/myFile.ext"), javaPath);
	}
	
	@Test
	public void absoluteFolderSlash() throws Exception {
		final URI uri = new URI("file:/myProject/myFolder/");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("/myProject/myFolder"), javaPath);
	}
	
	@Test
	public void relativeFolderSlash() throws Exception {
		final URI uri = new URI("file:myProject/myFolder/");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("myProject/myFolder"), javaPath);
	}
	
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final URI uri = new URI("file:/myProject///myFolder");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("/myProject/myFolder"), javaPath);
	}
	
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final URI uri = new URI("file:myProject///myFolder");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("myProject/myFolder"), javaPath);
	}
	
	@Test
	public void absoluteFragmentQuery() throws Exception {
		final URI uri = new URI("file:/myProject///myFolder?query#fragment");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertNull(javaPath);
	}
	
	@Test
	public void relativeFragmentQuery() throws Exception {
		final URI uri = new URI("file:myProject///myFolder?query#fragment");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertNull(javaPath);
	}
	
	@Test
	public void absolutePath() throws Exception {
		final URI uri = new URI("file:/resource/..////");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("/resource/.."), javaPath);
	}
	
	@Test
	public void relativePath() throws Exception {
		final URI uri = new URI("file:resource/..////");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("resource/.."), javaPath);
	}
	
	@Test
	public void startRelativePath() throws Exception {
		final URI uri = new URI("file:../resource/..////");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("../resource/.."), javaPath);
	}
	
	@Test
	public void startRelativePathNoScheme() throws Exception {
		final URI uri = new URI(null, "../resource/////", null);
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("../resource/"), javaPath);
	}
	
	@Test
	public void multiRelativePath() throws Exception {
		final URI uri = new URI("file:resource/../some/dir/../../file.ext");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertEquals(Paths.get("resource/../some/dir/../../file.ext"), javaPath);
	}
	
	@Test
	public void invalidScheme() throws Exception {
		final URI uri = new URI("http:/myProject/myFolder");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertNull(javaPath);
	}
	
	@Test
	public void opaqueScheme() throws Exception {
		final URI uri = new URI("mailto:test@example.com");
		final Path javaPath = ConversionUtils.toJavaPath(uri);
		
		assertNull(javaPath);
	}
}
