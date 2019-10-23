package org.espilce.commons.lang.test.conversionutils.javafile.javaurl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.junit5.EnabledOnUnix;
import org.espilce.commons.lang.test.junit5.EnabledOnWin;
import org.junit.jupiter.api.Test;

public class TestJavaFile2JavaUrl_doc_as {
	@Test
	public void empty() throws Exception {
		final URL expected = new URL("file:");
		final URL actual = ConversionUtils.asJavaUrl(new File(""));
		assertEquals(expected, actual);
	}
	
	@Test
	public void dot() throws Exception {
		final URL expected = new URL("file:.");
		final URL actual = ConversionUtils.asJavaUrl(new File("."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void file() throws Exception {
		final URL expected = new URL("file:MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void relative() throws Exception {
		final URL expected = new URL("file:some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void startDoubleDot() throws Exception {
		final URL expected = new URL("file:../some/path/.");
		final URL actual = ConversionUtils.asJavaUrl(new File("../some/path/."));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderTrailingSlash() throws Exception {
		final URL expected = new URL("file:some/path");
		final URL actual = ConversionUtils.asJavaUrl(new File("some/path/"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderNoTrailingSlash() throws Exception {
		final URL expected = new URL("file:some/path");
		final URL actual = ConversionUtils.asJavaUrl(new File("some/path"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void folderExcessSlashes() throws Exception {
		final URL expected = new URL("file:some/path");
		final URL actual = ConversionUtils.asJavaUrl(new File("some//////path"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void encodedQuery() throws Exception {
		final URL expected = new URL("file:myProject/myFolder%2523query");
		final URL actual = ConversionUtils.asJavaUrl(new File("myProject/myFolder%23query"));
		assertEquals(expected, actual);
	}
	
	@SuppressWarnings("null")
	@Test
	public void nullParam() throws Exception {
		assertThrows(NullPointerException.class, () -> ConversionUtils.asJavaUrl((File) null));
	}
	
	@Test
	public void queryFragment() throws Exception {
		final URL expected = new URL("file:myProject/myFolder%3Fquery%23fragment");
		final URL actual = ConversionUtils.asJavaUrl(new File("myProject/myFolder?query#fragment"));
		assertEquals(expected, actual);
	}
	
	@Test
	public void slashDrive() throws Exception {
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("/c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void drive_win() throws Exception {
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void drive_unix() throws Exception {
		final URL expected = new URL("file:c%3A/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("c:/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void relativeLeadingSlash_win() throws Exception {
		final URL expected = new URL("file:/../some/path/.");
		final URL actual = ConversionUtils.asJavaUrl(new File("/../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void relativeLeadingSlash_unix() throws Exception {
		final URL expected = new URL("file:/../some/path/.");
		final URL actual = ConversionUtils.asJavaUrl(new File("/../some/path/."));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void slashAbsolute_win() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void slashAbsolute_unix() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("/some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void doubleSlashAbsolute_win() throws Exception {
		final URL expected = new URL("file:////some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("//some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void doubleSlashAbsolute_unix() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("//some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void tripleSlashAbsolute_win() throws Exception {
		final URL expected = new URL("file:////some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void tripleSlashAbsolute_unix() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("///some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void quadrupleSlashAbsolute_win() throws Exception {
		final URL expected = new URL("file:////some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void quadrupleSlashAbsolute_unix() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("////some/path/MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void startDoubleDotBackslash_win() throws Exception {
		final URL expected = new URL("file:../some/path/.");
		final URL actual = ConversionUtils.asJavaUrl(new File("..\\some\\path\\."));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void startDoubleDotBackslash_unix() throws Exception {
		final URL expected = new URL("file:..%5Csome%5Cpath%5C.");
		final URL actual = ConversionUtils.asJavaUrl(new File("..\\some\\path\\."));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void backslashAbsolute_win() throws Exception {
		final URL expected = new URL("file:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void backslashAbsolute_unix() throws Exception {
		final URL expected = new URL("file:%5Csome%5Cpath%5CMyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnWin
	public void driveBackslash_win() throws Exception {
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("c:\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}

	@Test
	@EnabledOnUnix
	public void driveBackslash_unix() throws Exception {
		final URL expected = new URL("file:c%3A%5Csome%5Cpath%5CMyFile.ext");
		final URL actual = ConversionUtils.asJavaUrl(new File("c:\\some\\path\\MyFile.ext"));
		assertEquals(expected, actual);
	}
}
