/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri.javafile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.net.URI;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.junit5.EnabledOnUnix;
import org.espilce.commons.lang.test.junit5.EnabledOnWin;
import org.junit.jupiter.api.Test;

public class TestJavaUri2JavaFile_doc_to {
	@Test
	public void empty() throws Exception {
		final File expected = new File("");
		final File actual = ConversionUtils.toJavaFile(new URI(""));
		assertEquals(expected, actual);
	}
	
	@Test
	public void dot() throws Exception {
		final File expected = new File(".");
		final File actual = ConversionUtils.toJavaFile(new URI("."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void file() throws Exception {
		final File expected = new File("MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(new URI("MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void noSchemeRelative() throws Exception {
		final File expected = new File("some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(new URI("some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void noSchemeLeadingSlash() throws Exception {
		final File expected = new File("/some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(new URI("/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void noSchemeStartDoubleDot() throws Exception {
		final File expected = new File("../some/path/.");
		final File actual = ConversionUtils.toJavaFile(new URI("../some/path/."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void schemeDot() throws Exception {
		final File expected = new File(".");
		final File actual = ConversionUtils.toJavaFile(new URI("file:."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void schemeStartDoubleDot() throws Exception {
		final File expected = new File("../some/path/.");
		final File actual = ConversionUtils.toJavaFile(new URI("file:../some/path/."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void slashAbsolute() throws Exception {
		final File expected = new File("/some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(new URI("file:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void doubleSlashAbsolute() throws Exception {
		final File expected = new File("//some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(new URI("file://some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void tripleSlashAbsolute() throws Exception {
		final File expected = new File("/some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(new URI("file:///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void quadrupleSlashAbsolute() throws Exception {
		final File expected = new File("//some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(new URI("file:////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderTrailingSlash() throws Exception {
		final File expected = new File("/some/path");
		final File actual = ConversionUtils.toJavaFile(new URI("file:/some/path/"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderNoTrailingSlash() throws Exception {
		final File expected = new File("/some/path");
		final File actual = ConversionUtils.toJavaFile(new URI("file:/some/path"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderExcessSlashes() throws Exception {
		final File expected = new File("/some/path");
		final File actual = ConversionUtils.toJavaFile(new URI("file:/some//////path"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void relativeLeadingSlash() throws Exception {
		final File expected = new File("/../some/path/.");
		final File actual = ConversionUtils.toJavaFile(new URI("file:/../some/path/."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void encodedQuery() throws Exception {
		final File expected = new File("/myProject/myFolder#query");
		final File actual = ConversionUtils.toJavaFile(new URI("file:/myProject/myFolder%23query"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void queryFragment() throws Exception {
		assertNull(ConversionUtils.toJavaFile(new URI("file:/myProject/myFolder?query#fragment")));
	}
	
	@Test
	public void noSchemeDrive() throws Exception {
		assertNull(ConversionUtils.toJavaFile(new URI("c:/some/path/MyFile.ext")));
	}
	
	@Test
	public void web() throws Exception {
		assertNull(ConversionUtils.toJavaFile(new URI("http://example.com")));
	}
	
	@Test
	public void nullParam() throws Exception {
		assertNull(ConversionUtils.toJavaFile((URI) null));
	}
	
	@Test
	@EnabledOnWin
	public void driveAbsolute_win() throws Exception {
		final File expected = new File("c:/some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(new URI("file:/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	@EnabledOnUnix
	public void driveAbsolute_unix() throws Exception {
		final File expected = new File("/c:/some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(new URI("file:/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
}
