/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javaurl.javafile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.net.URL;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.junit5.EnabledOnUnix;
import org.espilce.commons.lang.test.junit5.EnabledOnWin;
import org.junit.jupiter.api.Test;

public class TestJavaUrl2JavaFile_doc_as {
	@Test
	public void schemeDot() throws Exception {
		final File expected = new File(".");
		final File actual = ConversionUtils.asJavaFile(new URL("file:."));
		assertEquals(expected, actual);
	}

	@Test
	public void schemeStartDoubleDot() throws Exception {
		final File expected = new File("../some/path/.");
		final File actual = ConversionUtils.asJavaFile(new URL("file:../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void slashAbsolute() throws Exception {
		final File expected = new File("/some/path/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(new URL("file:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void doubleSlashAbsolute() throws Exception {
		final File expected = new File("//some/path/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(new URL("file://some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void tripleSlashAbsolute() throws Exception {
		final File expected = new File("/some/path/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(new URL("file:///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void quadrupleSlashAbsolute() throws Exception {
		final File expected = new File("//some/path/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(new URL("file:////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderTrailingSlash() throws Exception {
		final File expected = new File("/some/path");
		final File actual = ConversionUtils.asJavaFile(new URL("file:/some/path/"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderNoTrailingSlash() throws Exception {
		final File expected = new File("/some/path");
		final File actual = ConversionUtils.asJavaFile(new URL("file:/some/path"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderExcessSlashes() throws Exception {
		final File expected = new File("/some/path");
		final File actual = ConversionUtils.asJavaFile(new URL("file:/some//////path"));
		assertEquals(expected, actual);
	}

	@Test
	public void relativeLeadingSlash() throws Exception {
		final File expected = new File("/../some/path/.");
		final File actual = ConversionUtils.asJavaFile(new URL("file:/../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void backslashAbsolute() throws Exception {
		final File expected = new File("\\some\\path\\MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(new URL("file:\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void encodedQuery() throws Exception {
		final File expected = new File("/myProject/myFolder#query");
		final File actual = ConversionUtils.asJavaFile(new URL("file:/myProject/myFolder%23query"));
		assertEquals(expected, actual);
	}

	@Test
	public void queryFragment() throws Exception {
		assertThrows(
				UnconvertibleException.class,
				() -> ConversionUtils.asJavaFile(new URL("file:/myProject/myFolder?query#fragment"))
		);
	}

	@Test
	public void web() throws Exception {
		assertThrows(UnconvertibleException.class, () -> ConversionUtils.asJavaFile(new URL("http://example.com")));
	}

	@SuppressWarnings("null")
	@Test
	public void nullParam() throws Exception {
		assertThrows(NullPointerException.class, () -> ConversionUtils.asJavaFile((URL) null));
	}
	
	@Test
	@EnabledOnWin
	public void driveAbsolute_win() throws Exception {
		final File expected = new File("c:/some/path/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(new URL("file:/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	@EnabledOnUnix
	public void driveAbsolute_unix() throws Exception {
		final File expected = new File("/c:/some/path/MyFile.ext");
		final File actual = ConversionUtils.asJavaFile(new URL("file:/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
}
