/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri.javaurl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URI;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.junit.jupiter.api.Test;

public class TestJavaUri2JavaUrl_doc_to {
	@Test
	public void schemeDot() throws Exception {
		final URL expected = new URL("file:.");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:."));
		assertEquals(expected, actual);
	}

	@Test
	public void schemeStartDoubleDot() throws Exception {
		final URL expected = new URL("file:../some/path/.");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void slashAbsolute() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void doubleSlashAbsolute() throws Exception {
		final URL expected = new URL("file://some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file://some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void tripleSlashAbsolute() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void quadrupleSlashAbsolute() throws Exception {
		final URL expected = new URL("file:////some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderTrailingSlash() throws Exception {
		final URL expected = new URL("file:/some/path/");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:/some/path/"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderNoTrailingSlash() throws Exception {
		final URL expected = new URL("file:/some/path");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:/some/path"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderExcessSlashes() throws Exception {
		final URL expected = new URL("file:/some//////path");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:/some//////path"));
		assertEquals(expected, actual);
	}

	@Test
	public void relativeLeadingSlash() throws Exception {
		final URL expected = new URL("file:/../some/path/.");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:/../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void driveAbsolute() throws Exception {
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void encodedQuery() throws Exception {
		final URL expected = new URL("file:/myProject/myFolder%23query");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:/myProject/myFolder%23query"));
		assertEquals(expected, actual);
	}

	@Test
	public void queryFragment() throws Exception {
		final URL expected = new URL("file:/myProject/myFolder?query#fragment");
		final URL actual = ConversionUtils.toJavaUrl(new URI("file:/myProject/myFolder?query#fragment"));
		assertEquals(expected, actual);
	}

	@Test
	public void web() throws Exception {
		final URL expected = new URL("http://example.com");
		final URL actual = ConversionUtils.toJavaUrl(new URI("http://example.com"));
		assertEquals(expected, actual);
	}

	@Test
	public void mail() throws Exception {
		final URL expected = new URL("mailto:test@example.com");
		final URL actual = ConversionUtils.toJavaUrl(new URI("mailto:test@example.com"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void empty() throws Exception {
		assertNull(ConversionUtils.toJavaUrl(new URI(null, null, null)));
	}
	
	@Test
	public void nullParam() throws Exception {
		assertNull(ConversionUtils.toJavaUrl((URI) null));
	}
}
