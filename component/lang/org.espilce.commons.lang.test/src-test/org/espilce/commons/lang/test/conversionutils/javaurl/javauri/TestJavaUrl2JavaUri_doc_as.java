package org.espilce.commons.lang.test.conversionutils.javaurl.javauri;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.junit5.EnabledOnUnix;
import org.espilce.commons.lang.test.junit5.EnabledOnWin;
import org.junit.jupiter.api.Test;

public class TestJavaUrl2JavaUri_doc_as {
	@Test
	public void empty() throws Exception {
		final URI expected = new URI(null, null, null);
		final URI actual = ConversionUtils.asJavaUri(new URL("file:"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void schemeDot() throws Exception {
		final URI expected = new URI("file:.");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:."));
		assertEquals(expected, actual);
	}

	@Test
	public void schemeStartDoubleDot() throws Exception {
		final URI expected = new URI("file:../some/path/.");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void slashAbsolute() throws Exception {
		final URI expected = new URI("file:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void doubleSlashAbsolute() throws Exception {
		final URI expected = new URI("file://some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(new URL("file://some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void tripleSlashAbsolute() throws Exception {
		final URI expected = new URI("file:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void quadrupleSlashAbsolute() throws Exception {
		final URI expected = new URI("file:////some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderTrailingSlash() throws Exception {
		final URI expected = new URI("file:/some/path/");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:/some/path/"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderNoTrailingSlash() throws Exception {
		final URI expected = new URI("file:/some/path");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:/some/path"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderExcessSlashes() throws Exception {
		final URI expected = new URI("file:/some//////path");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:/some//////path"));
		assertEquals(expected, actual);
	}

	@Test
	public void relativeLeadingSlash() throws Exception {
		final URI expected = new URI("file:/../some/path/.");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:/../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void driveAbsolute() throws Exception {
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void encodedQuery() throws Exception {
		final URI expected = new URI("file:/myProject/myFolder%23query");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:/myProject/myFolder%23query"));
		assertEquals(expected, actual);
	}

	@Test
	public void queryFragment() throws Exception {
		final URI expected = new URI("file:/myProject/myFolder?query#fragment");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:/myProject/myFolder?query#fragment"));
		assertEquals(expected, actual);
	}

	@Test
	public void web() throws Exception {
		final URI expected = new URI("http://example.com");
		final URI actual = ConversionUtils.asJavaUri(new URL("http://example.com"));
		assertEquals(expected, actual);
	}

	@Test
	public void mail() throws Exception {
		final URI expected = new URI("mailto:test@example.com");
		final URI actual = ConversionUtils.asJavaUri(new URL("mailto:test@example.com"));
		assertEquals(expected, actual);
	}
	
	@SuppressWarnings("null")
	@Test
	public void nullParam() throws Exception {
		assertThrows(NullPointerException.class, () -> ConversionUtils.asJavaUri((URL) null));
	}
	
	@Test
	@EnabledOnWin
	public void backslashAbsolute_win() throws Exception {
		final URI expected = new URI("file:/some/path/MyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	@EnabledOnUnix
	public void backslashAbsolute_unix() throws Exception {
		final URI expected = new URI("%5Csome%5Cpath%5CMyFile.ext");
		final URI actual = ConversionUtils.asJavaUri(new URL("file:\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}
}
