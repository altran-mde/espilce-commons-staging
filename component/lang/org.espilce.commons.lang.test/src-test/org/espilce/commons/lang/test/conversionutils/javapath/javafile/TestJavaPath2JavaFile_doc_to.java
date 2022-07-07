/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javapath.javafile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.junit5.EnabledOnUnix;
import org.espilce.commons.lang.test.junit5.EnabledOnWin;
import org.junit.jupiter.api.Test;

public class TestJavaPath2JavaFile_doc_to {
	@Test
	public void empty() throws Exception {
		final File expected = new File("");
		final File actual = ConversionUtils.toJavaFile(Paths.get(""));
		assertEquals(expected, actual);
	}
	
	@Test
	public void dot() throws Exception {
		final File expected = new File(".");
		final File actual = ConversionUtils.toJavaFile(Paths.get("."));
		assertEquals(expected, actual);
	}

	@Test
	public void file() throws Exception {
		final File expected = new File("MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(Paths.get("MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void relative() throws Exception {
		final File expected = new File("some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(Paths.get("some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void startDoubleDot() throws Exception {
		final File expected = new File("../some/path/.");
		final File actual = ConversionUtils.toJavaFile(Paths.get("../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void slashAbsolute() throws Exception {
		final File expected = new File("/some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(Paths.get("/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void doubleSlashAbsolute() throws Exception {
		final File expected = new File("//some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(Paths.get("//some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void tripleSlashAbsolute() throws Exception {
		final File expected = new File("//some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(Paths.get("///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void quadrupleSlashAbsolute() throws Exception {
		final File expected = new File("//some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(Paths.get("////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderTrailingSlash() throws Exception {
		final File expected = new File("/some/path");
		final File actual = ConversionUtils.toJavaFile(Paths.get("/some/path/"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderNoTrailingSlash() throws Exception {
		final File expected = new File("/some/path");
		final File actual = ConversionUtils.toJavaFile(Paths.get("/some/path"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderExcessSlashes() throws Exception {
		final File expected = new File("/some/path");
		final File actual = ConversionUtils.toJavaFile(Paths.get("/some//////path"));
		assertEquals(expected, actual);
	}

	@Test
	public void relativeLeadingSlash() throws Exception {
		final File expected = new File("/../some/path/.");
		final File actual = ConversionUtils.toJavaFile(Paths.get("/../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void encodedQuery() throws Exception {
		final File expected = new File("/myProject/myFolder%23query");
		final File actual = ConversionUtils.toJavaFile(Paths.get("/myProject/myFolder%23query"));
		assertEquals(expected, actual);
	}

	@Test
	public void drive() throws Exception {
		final File expected = new File("c:/some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(Paths.get("c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void startDoubleDotBackslash() throws Exception {
		final File expected = new File("..\\some\\path\\.");
		final File actual = ConversionUtils.toJavaFile(Paths.get("..\\some\\path\\."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void backslashAbsolute() throws Exception {
		final File expected = new File("\\some\\path\\MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(Paths.get("\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void driveBackslash() throws Exception {
		final File expected = new File("c:\\some\\path\\MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(Paths.get("c:\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void nullParam() throws Exception {
		assertNull(ConversionUtils.toJavaFile((Path) null));
	}
	
	@Test
	@EnabledOnWin
	public void queryFragment_win() throws Exception {
		assertThrows(
				InvalidPathException.class, () -> Paths.get("/myProject/myFolder?query#fragment")
		);
	}
	
	@Test
	@EnabledOnUnix
	public void queryFragment_unix() throws Exception {
		final File expected = new File("/myProject/myFolder?query#fragment");
		final File actual = ConversionUtils.toJavaFile(Paths.get("/myProject/myFolder?query#fragment"));
		assertEquals(expected, actual);
	}
	
	@Test
	@EnabledOnWin
	public void slashDrive_win() throws Exception {
		assertThrows(InvalidPathException.class, () -> Paths.get("/c:/some/path/MyFile.ext"));
	}
	
	@Test
	@EnabledOnUnix
	public void slashDrive_unix() throws Exception {
		final File expected = new File("/c:/some/path/MyFile.ext");
		final File actual = ConversionUtils.toJavaFile(Paths.get("/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
}
