/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javapath.javaurl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.junit5.EnabledOnUnix;
import org.espilce.commons.lang.test.junit5.EnabledOnWin;
import org.junit.jupiter.api.Test;

public class TestJavaPath2JavaUrl_doc_to {
	@Test
	public void empty() throws Exception {
		final URL expected = new URL("file:");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get(""));
		assertEquals(expected, actual);
	}
	
	@Test
	public void dot() throws Exception {
		final URL expected = new URL("file:.");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void file() throws Exception {
		final URL expected = new URL("file:MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void relative() throws Exception {
		final URL expected = new URL("file:some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void startDoubleDot() throws Exception {
		final URL expected = new URL("file:../some/path/.");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("../some/path/."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderTrailingSlash() throws Exception {
		final URL expected = new URL("file:some/path");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("some/path/"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderNoTrailingSlash() throws Exception {
		final URL expected = new URL("file:some/path");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("some/path"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderExcessSlashes() throws Exception {
		final URL expected = new URL("file:some/path");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("some//////path"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void encodedQuery() throws Exception {
		final URL expected = new URL("file:myProject/myFolder%2523query");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("myProject/myFolder%23query"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void nullParam() throws Exception {
		assertNull(ConversionUtils.toJavaUrl((Path) null));
	}
	
	@Test
	@EnabledOnWin
	public void queryFragment_win() throws Exception {
		assertThrows(InvalidPathException.class, () -> Paths.get("myProject/myFolder?query#fragment"));
	}
	
	@Test
	@EnabledOnUnix
	public void queryFragment_unix() throws Exception {
		final URL expected = new URL("file:myProject/myFolder%3Fquery%23fragment");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("myProject/myFolder?query#fragment"));
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
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	@EnabledOnWin
	public void drive_win() throws Exception {
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void drive_unix() throws Exception {
		final URL expected = new URL("file:c%3A/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void relativeLeadingSlash() throws Exception {
		final URL expected = new URL("file:/../some/path/.");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("/../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void slashAbsolute() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void doubleSlashAbsolute_win() throws Exception {
		final URL expected = new URL("file://some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("//some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void doubleSlashAbsolute_unix() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("//some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void tripleSlashAbsolute_win() throws Exception {
		final URL expected = new URL("file://some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void tripleSlashAbsolute_unix() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void quadrupleSlashAbsolute_win() throws Exception {
		final URL expected = new URL("file://some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void quadrupleSlashAbsolute_unix() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void startDoubleDotBackslash_win() throws Exception {
		final URL expected = new URL("file:../some/path/.");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("..\\some\\path\\."));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void startDoubleDotBackslash_unix() throws Exception {
		final URL expected = new URL("file:..%5Csome%5Cpath%5C.");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("..\\some\\path\\."));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void backslashAbsolute_win() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void backslashAbsolute_unix() throws Exception {
		final URL expected = new URL("file:%5Csome%5Cpath%5CMyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void driveBackslash_win() throws Exception {
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("c:\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void driveBackslash_unix() throws Exception {
		final URL expected = new URL("file:c%3A%5Csome%5Cpath%5CMyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(Paths.get("c:\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}
}
