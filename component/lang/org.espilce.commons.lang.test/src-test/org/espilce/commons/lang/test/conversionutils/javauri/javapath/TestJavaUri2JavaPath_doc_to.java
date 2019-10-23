package org.espilce.commons.lang.test.conversionutils.javauri.javapath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.junit5.EnabledOnUnix;
import org.espilce.commons.lang.test.junit5.EnabledOnWin;
import org.junit.jupiter.api.Test;

public class TestJavaUri2JavaPath_doc_to {
	@Test
	public void empty() throws Exception {
		final Path expected = Paths.get("");
		final Path actual = ConversionUtils.toJavaPath(new URI(""));
		assertEquals(expected, actual);
	}
	
	@Test
	public void dot() throws Exception {
		final Path expected = Paths.get(".");
		final Path actual = ConversionUtils.toJavaPath(new URI("."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void file() throws Exception {
		final Path expected = Paths.get("MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new URI("MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void noSchemeRelative() throws Exception {
		final Path expected = Paths.get("some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new URI("some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void noSchemeLeadingSlash() throws Exception {
		final Path expected = Paths.get("/some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new URI("/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void noSchemeStartDoubleDot() throws Exception {
		final Path expected = Paths.get("../some/path/.");
		final Path actual = ConversionUtils.toJavaPath(new URI("../some/path/."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void schemeDot() throws Exception {
		final Path expected = Paths.get(".");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void schemeStartDoubleDot() throws Exception {
		final Path expected = Paths.get("../some/path/.");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:../some/path/."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void slashAbsolute() throws Exception {
		final Path expected = Paths.get("/some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void doubleSlashAbsolute() throws Exception {
		final Path expected = Paths.get("//some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new URI("file://some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void tripleSlashAbsolute() throws Exception {
		final Path expected = Paths.get("/some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void quadrupleSlashAbsolute() throws Exception {
		final Path expected = Paths.get("//some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderTrailingSlash() throws Exception {
		final Path expected = Paths.get("/some/path");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:/some/path/"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderNoTrailingSlash() throws Exception {
		final Path expected = Paths.get("/some/path");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:/some/path"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderExcessSlashes() throws Exception {
		final Path expected = Paths.get("/some/path");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:/some//////path"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void relativeLeadingSlash() throws Exception {
		final Path expected = Paths.get("/../some/path/.");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:/../some/path/."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void encodedQuery() throws Exception {
		final Path expected = Paths.get("/myProject/myFolder#query");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:/myProject/myFolder%23query"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void queryFragment() throws Exception {
		assertNull(ConversionUtils.toJavaPath(new URI("file:/myProject/myFolder?query#fragment")));
	}
	
	@Test
	public void noSchemeDrive() throws Exception {
		assertNull(ConversionUtils.toJavaPath(new URI("c:/some/path/MyFile.ext")));
	}
	
	@Test
	public void web() throws Exception {
		assertNull(ConversionUtils.toJavaPath(new URI("http://example.com")));
	}
	
	@Test
	public void nullParam() throws Exception {
		assertNull(ConversionUtils.toJavaPath((URI) null));
	}
	
	@Test
	@EnabledOnWin
	public void driveAbsolute_win() throws Exception {
		final Path expected = Paths.get("c:/some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	@EnabledOnUnix
	public void driveAbsolute_unix() throws Exception {
		final Path expected = Paths.get("/c:/some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new URI("file:/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
}
