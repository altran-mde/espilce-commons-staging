/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javapath.javauri;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.junit5.EnabledOnUnix;
import org.espilce.commons.lang.test.junit5.EnabledOnWin;
import org.junit.jupiter.api.Test;

public class TestJavaPath2JavaUri_doc_as {
	@Test
	public void empty() throws Exception {
		final URI expected = new URI("");
		final URI actual = ConversionUtils.asJavaUri(Paths.get(""));
		assertEquals(expected, actual);
	}
	
	@Test
	public void dot() throws Exception {
		final URI expected = new URI(".");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void file() throws Exception {
		final URI expected = new URI("MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void relative() throws Exception {
		final URI expected = new URI("some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void startDoubleDot() throws Exception {
		final URI expected = new URI("../some/path/.");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("../some/path/."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderTrailingSlash() throws Exception {
		final URI expected = new URI("some/path");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("some/path/"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderNoTrailingSlash() throws Exception {
		final URI expected = new URI("some/path");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("some/path"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderExcessSlashes() throws Exception {
		final URI expected = new URI("some/path");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("some//////path"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void encodedQuery() throws Exception {
		final URI expected = new URI("myProject/myFolder%2523query");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("myProject/myFolder%23query"));
		assertEquals(expected, actual);
	}
	
	@SuppressWarnings("null")
	@Test
	public void nullParam() throws Exception {
		assertThrows(NullPointerException.class, () -> ConversionUtils.asJavaUri((Path) null));
	}
	
	@Test
	@EnabledOnWin
	public void queryFragment_win() throws Exception {
		assertThrows(InvalidPathException.class, () -> Paths.get("myProject/myFolder?query#fragment"));
	}
	
	@Test
	@EnabledOnUnix
	public void queryFragment_unix() throws Exception {
		final URI expected = new URI("myProject/myFolder%3Fquery%23fragment");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("myProject/myFolder?query#fragment"));
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
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	@EnabledOnWin
	public void drive_win() throws Exception {
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void drive_unix() throws Exception {
		final URI expected = new URI("c%3A/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void relativeLeadingSlash_win() throws Exception {
		final URI expected = new URI("/../some/path/.");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("/../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void relativeLeadingSlash_unix() throws Exception {
		final URI expected = new URI("file:/../some/path/.");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("/../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void slashAbsolute_win() throws Exception {
		final URI expected = new URI("/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void slashAbsolute_unix() throws Exception {
		final URI expected = new URI("file:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void doubleSlashAbsolute_win() throws Exception {
		final URI expected = new URI("file://some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("//some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void doubleSlashAbsolute_unix() throws Exception {
		final URI expected = new URI("file:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("//some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void tripleSlashAbsolute_win() throws Exception {
		final URI expected = new URI("file://some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void tripleSlashAbsolute_unix() throws Exception {
		final URI expected = new URI("file:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void quadrupleSlashAbsolute_win() throws Exception {
		final URI expected = new URI("file://some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void quadrupleSlashAbsolute_unix() throws Exception {
		final URI expected = new URI("file:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void startDoubleDotBackslash_win() throws Exception {
		final URI expected = new URI("../some/path/.");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("..\\some\\path\\."));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void startDoubleDotBackslash_unix() throws Exception {
		final URI expected = new URI("..%5Csome%5Cpath%5C.");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("..\\some\\path\\."));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void backslashAbsolute_win() throws Exception {
		final URI expected = new URI("/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void backslashAbsolute_unix() throws Exception {
		final URI expected = new URI("%5Csome%5Cpath%5CMyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void driveBackslash_win() throws Exception {
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("c:\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void driveBackslash_unix() throws Exception {
		final URI expected = new URI("c%3A%5Csome%5Cpath%5CMyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(Paths.get("c:\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}
}
