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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import org.espilce.commons.lang.test.conversionutils.TestABase;
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.conversionutils.TestIBase;
import org.espilce.commons.lang.test.conversionutils.TestIJavaUri;
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.espilce.commons.lang.test.conversionutils.TestIScheme;
import org.junit.Test;

public abstract class TestJavaUri2JavaUrl extends TestABase
		implements TestIBase, TestIAbsolute, TestIRelative, TestIScheme, TestIJavaUri
{
	@Override
	@Test
	public void absoluteFile() throws Exception {
		final URI input = URI.create("file:/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final URI input = URI.create("http:////myProject///folder///deep/myFile.ext//");
		final URL actual = invoke(input);
		final URL expected = new URL("http:////myProject///folder///deep/myFile.ext//");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlash() throws Exception {
		final URI input = URI.create("http:/myProject/myFolder/");
		final URL actual = invoke(input);
		final URL expected = new URL("http:/myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final URI input = URI.create("http:/myProject///myFolder");
		final URL actual = invoke(input);
		final URL expected = new URL("http:/myProject///myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteNestedFile() throws Exception {
		final URI input = URI.create("http:/myProject/folder/deep/myFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("http:/myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteNestedFileNoScheme() throws Exception {
		final URI input = new URI(null, "/some/path/MyFile.ext", null);
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void absolutePath() throws Exception {
		final URI input = URI.create("file:/resource/..////");
		final URL actual = invoke(input);
		final URL expected = new URL("file:/resource/..////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePseudoFragment() throws Exception {
		final URI input = new URI("file://myProject///myFolder%23query");
		final URL actual = invoke(input);
		final URL expected = new URL("file://myProject///myFolder%23query");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final URI input = new URI("file://c:/some/path/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final URI input = new URI("file:/c:/some/path/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final URI input = new URI("file:///c:/some/path/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void current() throws Exception {
		final URI input = new URI("file", ".", null);
		final URL actual = invoke(input);
		final URL expected = new URL("file:.");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentNoScheme() throws Exception {
		final URI input = new URI(null, ".", null);
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void currentRelativeNestedFile() throws Exception {
		final URI input = new URI("file:./some/path/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:./some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentRelativeNestedFileNoScheme() throws Exception {
		final URI input = new URI(null, "./some/path/MyFile.ext", null);
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void currentSlash() throws Exception {
		final URI input = new URI("file", "./", null);
		final URL actual = invoke(input);
		final URL expected = new URL("file:./");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentSlashNoScheme() throws Exception {
		final URI input = new URI(null, "./", null);
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void empty() throws Exception {
		final URI input = URI.create("");
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void emptyNoScheme() throws Exception {
		final URI input = new URI(null, "", null);
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void emptyWithScheme() throws Exception {
		final URI input = URI.create("http:///");
		final URL actual = invoke(input);
		final URL expected = new URL("http:///");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void fragment() throws Exception {
		final URI input = URI.create("http:/myProject///myFolder#fragment");
		final URL actual = invoke(input);
		final URL expected = new URL("http:/myProject///myFolder#fragment");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void fragmentQuery() throws Exception {
		final URI input = URI.create("http:/myProject///myFolder?query#fragment");
		final URL actual = invoke(input);
		final URL expected = new URL("http:/myProject///myFolder?query#fragment");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void inputBroken() throws Exception {
		final URI input = URI.create("fasfasdf");
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void inputNoSlashes() throws Exception {
		final URI input = URI.create("file:fasfasdf");
		final URL actual = invoke(input);
		final URL expected = new URL("file:fasfasdf");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void multiRelativePath() throws Exception {
		final URI input = new URI("file:resource/../some/dir/../../file.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:resource/../some/dir/../../file.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void noSchema() throws Exception {
		final URI input = URI.create("MyFile.ext");
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void otherSchema() throws Exception {
		final URI input = URI.create("https://example.com/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("https://example.com/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void paramNull() throws Exception {
		final URI input = (URI) null;
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void parent() throws Exception {
		final URI input = new URI("file:..");
		final URL actual = invoke(input);
		final URL expected = new URL("file:..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void query() throws Exception {
		final URI input = URI.create("http:/myProject///myFolder?query");
		final URL actual = invoke(input);
		final URL expected = new URL("http:/myProject///myFolder?query");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFile() throws Exception {
		final URI input = new URI("file:MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final URI input = new URI("file:myProject///folder///deep/myFile.ext//");
		final URL actual = invoke(input);
		final URL expected = new URL("file:myProject///folder///deep/myFile.ext//");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlash() throws Exception {
		final URI input = new URI("file:myProject/myFolder/");
		final URL actual = invoke(input);
		final URL expected = new URL("file:myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final URI input = new URI("file:myProject///myFolder");
		final URL actual = invoke(input);
		final URL expected = new URL("file:myProject///myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeNestedFile() throws Exception {
		final URI input = URI.create("file:some/path/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeNestedFileNoScheme() throws Exception {
		final URI input = new URI(null, "some/path/MyFile.ext", null);
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void relativePath() throws Exception {
		final URI input = new URI("file:resource/..////");
		final URL actual = invoke(input);
		final URL expected = new URL("file:resource/..////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePseudoFragment() throws Exception {
		final URI input = new URI("file:myProject///myFolder%23query");
		final URL actual = invoke(input);
		final URL expected = new URL("file:myProject///myFolder%23query");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeUri() throws Exception {
		final URI input = URI.create("mailto:/resource/...////");
		final URL actual = invoke(input);
		final URL expected = new URL("mailto:/resource/...////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void root() throws Exception {
		final URI input = URI.create("http:/");
		final URL actual = invoke(input);
		final URL expected = new URL("http:/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void rootNoScheme() throws Exception {
		final URI input = new URI(null, "/", null);
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void rootScheme() throws Exception {
		final URI input = new URI("file", "/", null);
		final URL actual = invoke(input);
		final URL expected = new URL("file:/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void startRelativePath() throws Exception {
		final URI input = new URI("file:../resource/..////");
		final URL actual = invoke(input);
		final URL expected = new URL("file:../resource/..////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void startRelativePathNoScheme() throws Exception {
		final URI input = new URI(null, "../resource/////", null);
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	protected Class<?> getSourceType() { return URI.class; }
	
	@Override
	protected Class<?> getTargetType() { return Path.class; }
	
	protected abstract URL invoke(final URI input);
}
