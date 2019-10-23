package org.espilce.commons.lang.test.conversionutils.javafile.javapath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.junit5.EnabledOnUnix;
import org.espilce.commons.lang.test.junit5.EnabledOnWin;
import org.junit.jupiter.api.Test;

public class TestJavaFile2JavaPath_doc_to {
	@Test
	public void empty() throws Exception {
		final Path expected = Paths.get("");
		final Path actual = ConversionUtils.toJavaPath(new File(""));
		assertEquals(expected, actual);
	}
	
	@Test
	public void dot() throws Exception {
		final Path expected = Paths.get(".");
		final Path actual = ConversionUtils.toJavaPath(new File("."));
		assertEquals(expected, actual);
	}

	@Test
	public void file() throws Exception {
		final Path expected = Paths.get("MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new File("MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void relative() throws Exception {
		final Path expected = Paths.get("some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new File("some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void startDoubleDot() throws Exception {
		final Path expected = Paths.get("../some/path/.");
		final Path actual = ConversionUtils.toJavaPath(new File("../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void slashAbsolute() throws Exception {
		final Path expected = Paths.get("/some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new File("/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void doubleSlashAbsolute() throws Exception {
		final Path expected = Paths.get("//some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new File("//some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void tripleSlashAbsolute() throws Exception {
		final Path expected = Paths.get("//some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new File("///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void quadrupleSlashAbsolute() throws Exception {
		final Path expected = Paths.get("//some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new File("////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderTrailingSlash() throws Exception {
		final Path expected = Paths.get("/some/path");
		final Path actual = ConversionUtils.toJavaPath(new File("/some/path/"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderNoTrailingSlash() throws Exception {
		final Path expected = Paths.get("/some/path");
		final Path actual = ConversionUtils.toJavaPath(new File("/some/path"));
		assertEquals(expected, actual);
	}

	@Test
	public void folderExcessSlashes() throws Exception {
		final Path expected = Paths.get("/some/path");
		final Path actual = ConversionUtils.toJavaPath(new File("/some//////path"));
		assertEquals(expected, actual);
	}

	@Test
	public void relativeLeadingSlash() throws Exception {
		final Path expected = Paths.get("/../some/path/.");
		final Path actual = ConversionUtils.toJavaPath(new File("/../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	public void encodedQuery() throws Exception {
		final Path expected = Paths.get("/myProject/myFolder%23query");
		final Path actual = ConversionUtils.toJavaPath(new File("/myProject/myFolder%23query"));
		assertEquals(expected, actual);
	}

	@Test
	public void drive() throws Exception {
		final Path expected = Paths.get("c:/some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new File("c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	public void startDoubleDotBackslash() throws Exception {
		final Path expected = Paths.get("..\\some\\path\\.");
		final Path actual = ConversionUtils.toJavaPath(new File("..\\some\\path\\."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void backslashAbsolute() throws Exception {
		final Path expected = Paths.get("\\some\\path\\MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new File("\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void driveBackslash() throws Exception {
		final Path expected = Paths.get("c:\\some\\path\\MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new File("c:\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void nullParam() throws Exception {
		assertNull(ConversionUtils.toJavaPath((File) null));
	}
	
	@Test
	@EnabledOnWin
	public void queryFragment_win() throws Exception {
		assertNull(ConversionUtils.toJavaPath(new File("/myProject/myFolder?query#fragment")));
	}
	
	@Test
	@EnabledOnUnix
	public void queryFragment_unix() throws Exception {
		final Path expected = Paths.get("/myProject/myFolder?query#fragment");
		final Path actual = ConversionUtils.toJavaPath(new File("/myProject/myFolder?query#fragment"));
		assertEquals(expected, actual);
	}
	
	@Test
	@EnabledOnWin
	public void slashDrive_win() throws Exception {
		final Path expected = Paths.get("c:/some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new File("/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	@EnabledOnUnix
	public void slashDrive_unix() throws Exception {
		final Path expected = Paths.get("/c:/some/path/MyFile.ext");
		final Path actual = ConversionUtils.toJavaPath(new File("/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
}
