package org.espilce.commons.lang.test.conversionutils.javauri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.conversionutils.TestIBase;
import org.espilce.commons.lang.test.conversionutils.TestIJavaUri;
import org.espilce.commons.lang.test.conversionutils.TestIParamsInvalid;
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.espilce.commons.lang.test.conversionutils.TestIScheme;
import org.junit.Test;

public abstract class TestJavaUri2JavaPath
		implements TestIBase, TestIAbsolute, TestIRelative, TestIScheme, TestIJavaUri, TestIParamsInvalid
{
	@Override
	@Test
	public void paramNull() throws Exception {
		final URI input = (URI) null;
		final Path actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void root() throws Exception {
		final URI input = new URI("file:/");
		final Path actual = invoke(input);
		final Path expected = Paths.get("/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void rootScheme() throws Exception {
		final URI input = new URI("file", "/", null);
		final Path actual = invoke(input);
		final Path expected = Paths.get("/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void rootNoScheme() throws Exception {
		final URI input = new URI(null, "/", null);
		final Path actual = invoke(input);
		final Path expected = Paths.get("/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void emptyNoScheme() throws Exception {
		final URI input = new URI(null, "", null);
		final Path actual = invoke(input);
		final Path expected = Paths.get("");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void current() throws Exception {
		final URI input = new URI("file", ".", null);
		final Path actual = invoke(input);
		final Path expected = Paths.get(".");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentNoScheme() throws Exception {
		final URI input = new URI(null, ".", null);
		final Path actual = invoke(input);
		final Path expected = Paths.get(".");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentSlash() throws Exception {
		final URI input = new URI("file", "./", null);
		final Path actual = invoke(input);
		final Path expected = Paths.get("./");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentSlashNoScheme() throws Exception {
		final URI input = new URI(null, "./", null);
		final Path actual = invoke(input);
		final Path expected = Paths.get("./");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteNestedFile() throws Exception {
		final URI input = new URI("file:/some/path/MyFile.ext");
		final Path actual = invoke(input);
		final Path expected = Paths.get("/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final URI input = new URI("file:/c:/some/path/MyFile.ext");
		final Path actual = invoke(input);
		final Path expected = Paths.get("c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final URI input = new URI("file://c:/some/path/MyFile.ext");
		final Path actual = invoke(input);
		final Path expected = Paths.get("c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final URI input = new URI("file:///c:/some/path/MyFile.ext");
		final Path actual = invoke(input);
		final Path expected = Paths.get("c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteNestedFileNoScheme() throws Exception {
		final URI input = new URI(null, "/some/path/MyFile.ext", null);
		final Path actual = invoke(input);
		final Path expected = Paths.get("/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeNestedFile() throws Exception {
		final URI input = new URI("file:some/path/MyFile.ext");
		final Path actual = invoke(input);
		final Path expected = Paths.get("some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeNestedFileNoScheme() throws Exception {
		final URI input = new URI(null, "some/path/MyFile.ext", null);
		final Path actual = invoke(input);
		final Path expected = Paths.get("some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentRelativeNestedFile() throws Exception {
		final URI input = new URI("file:./some/path/MyFile.ext");
		final Path actual = invoke(input);
		final Path expected = Paths.get("./some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentRelativeNestedFileNoScheme() throws Exception {
		final URI input = new URI(null, "./some/path/MyFile.ext", null);
		final Path actual = invoke(input);
		final Path expected = Paths.get("./some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFile() throws Exception {
		final URI input = new URI("file:/MyFile.ext");
		final Path actual = invoke(input);
		final Path expected = Paths.get("/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFile() throws Exception {
		final URI input = new URI("file:MyFile.ext");
		final Path actual = invoke(input);
		final Path expected = Paths.get("MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final URI input = new URI("file:/myProject///folder///deep/myFile.ext//");
		final Path actual = invoke(input);
		final Path expected = Paths.get("/myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final URI input = new URI("file:myProject///folder///deep/myFile.ext//");
		final Path actual = invoke(input);
		final Path expected = Paths.get("myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlash() throws Exception {
		final URI input = new URI("file:/myProject/myFolder/");
		final Path actual = invoke(input);
		final Path expected = Paths.get("/myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlash() throws Exception {
		final URI input = new URI("file:myProject/myFolder/");
		final Path actual = invoke(input);
		final Path expected = Paths.get("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final URI input = new URI("file:/myProject///myFolder");
		final Path actual = invoke(input);
		final Path expected = Paths.get("/myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final URI input = new URI("file:myProject///myFolder");
		final Path actual = invoke(input);
		final Path expected = Paths.get("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFragmentQuery() throws Exception {
		final URI input = new URI("file:/myProject///myFolder?query#fragment");
		final Path actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void relativeFragmentQuery() throws Exception {
		final URI input = new URI("file:myProject///myFolder?query#fragment");
		final Path actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void absolutePath() throws Exception {
		final URI input = new URI("file:/resource/..////");
		final Path actual = invoke(input);
		final Path expected = Paths.get("/resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePath() throws Exception {
		final URI input = new URI("file:resource/..////");
		final Path actual = invoke(input);
		final Path expected = Paths.get("resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void startRelativePath() throws Exception {
		final URI input = new URI("file:../resource/..////");
		final Path actual = invoke(input);
		final Path expected = Paths.get("../resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void startRelativePathNoScheme() throws Exception {
		final URI input = new URI(null, "../resource/////", null);
		final Path actual = invoke(input);
		final Path expected = Paths.get("../resource/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void multiRelativePath() throws Exception {
		final URI input = new URI("file:resource/../some/dir/../../file.ext");
		final Path actual = invoke(input);
		final Path expected = Paths.get("resource/../some/dir/../../file.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void invalidScheme() throws Exception {
		final URI input = new URI("http:/myProject/myFolder");
		final Path actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void opaqueScheme() throws Exception {
		final URI input = new URI("mailto:test@example.com");
		final Path actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void empty() throws Exception {
		final URI input = URI.create("");
		final Path actual = invoke(input);
		final Path expected = Paths.get("");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void emptyWithScheme() throws Exception {
		final URI input = URI.create("file:/");
		final Path actual = invoke(input);
		final Path expected = Paths.get("/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void fragment() throws Exception {
		final URI input = URI.create("file:/myProject///myFolder#fragment");
		final Path actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void query() throws Exception {
		final URI input = URI.create("file:/myProject///myFolder?query");
		final Path actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void fragmentQuery() throws Exception {
		final URI input = URI.create("file:/myProject///myFolder?query#fragment");
		final Path actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void relativeUri() throws Exception {
		final URI input = URI.create("file:/resource/...////");
		final Path actual = invoke(input);
		final Path expected = Paths.get("/resource/.../");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void otherSchema() throws Exception {
		final URI input = URI.create("https://example.com/MyFile.ext");
		final Path actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void inputNoSlashes() throws Exception {
		final URI input = URI.create("file:fasfasdf");
		final Path actual = invoke(input);
		final Path expected = Paths.get("fasfasdf");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void inputBroken() throws Exception {
		final URI input = URI.create("!@#fasfasdf");
		final Path actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void noSchema() throws Exception {
		final URI input = URI.create("MyFile.ext");
		final Path actual = invoke(input);
		final Path expected = Paths.get("MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePseudoFragment() throws Exception {
		final URI input = new URI("file://myProject///myFolder%23query");
		final Path actual = invoke(input);
		final Path expected = Paths.get("/myProject/myFolder#query/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void parent() throws Exception {
		final URI input = new URI("file:..");
		final Path actual = invoke(input);
		final Path expected = Paths.get("..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePseudoFragment() throws Exception {
		final URI input = new URI("file:myProject///myFolder%23query");
		final Path actual = invoke(input);
		final Path expected = Paths.get("myProject/myFolder#query/");
		assertEquals(expected, actual);
	}
	
	protected abstract Path invoke(URI input);
}
