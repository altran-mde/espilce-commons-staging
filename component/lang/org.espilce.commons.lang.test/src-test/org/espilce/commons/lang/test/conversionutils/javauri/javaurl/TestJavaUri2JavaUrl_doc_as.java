package org.espilce.commons.lang.test.conversionutils.javauri.javaurl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URL;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.junit.jupiter.api.Test;

public class TestJavaUri2JavaUrl_doc_as {
	@Test
	public void schemeDot() throws Exception {
		final URL expected = new URL("file:.");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:."));
		assertEquals(expected, actual);
	}

	@Test
	public void schemeStartDoubleDot() throws Exception {
		final URL expected = new URL("file:../some/path/.");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void slashAbsolute() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void doubleSlashAbsolute() throws Exception {
		final URL expected = new URL("file://some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file://some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void tripleSlashAbsolute() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void quadrupleSlashAbsolute() throws Exception {
		final URL expected = new URL("file:////some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderTrailingSlash() throws Exception {
		final URL expected = new URL("file:/some/path/");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:/some/path/"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderNoTrailingSlash() throws Exception {
		final URL expected = new URL("file:/some/path");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:/some/path"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderExcessSlashes() throws Exception {
		final URL expected = new URL("file:/some//////path");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:/some//////path"));
		assertEquals(expected, actual);
	}

	@Test
	public void relativeLeadingSlash() throws Exception {
		final URL expected = new URL("file:/../some/path/.");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:/../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void driveAbsolute() throws Exception {
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void encodedQuery() throws Exception {
		final URL expected = new URL("file:/myProject/myFolder%23query");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:/myProject/myFolder%23query"));
		assertEquals(expected, actual);
	}

	@Test
	public void queryFragment() throws Exception {
		final URL expected = new URL("file:/myProject/myFolder?query#fragment");
		final URL actual = ConversionUtils.asJavaUrl(new URI("file:/myProject/myFolder?query#fragment"));
		assertEquals(expected, actual);
	}

	@Test
	public void web() throws Exception {
		final URL expected = new URL("http://example.com");
		final URL actual = ConversionUtils.asJavaUrl(new URI("http://example.com"));
		assertEquals(expected, actual);
	}

	@Test
	public void mail() throws Exception {
		final URL expected = new URL("mailto:test@example.com");
		final URL actual = ConversionUtils.asJavaUrl(new URI("mailto:test@example.com"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void empty() throws Exception {
		assertThrows(UnconvertibleException.class, () -> ConversionUtils.asJavaUrl(new URI(null, null, null)));
	}
	
	@SuppressWarnings("null")
	@Test
	public void nullParam() throws Exception {
		assertThrows(NullPointerException.class, () -> ConversionUtils.asJavaUrl((URI) null));
	}
}
