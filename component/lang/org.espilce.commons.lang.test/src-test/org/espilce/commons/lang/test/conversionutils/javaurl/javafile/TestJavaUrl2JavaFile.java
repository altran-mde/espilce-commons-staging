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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.net.URL;

import org.espilce.commons.lang.test.conversionutils.TestABase;
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.conversionutils.TestIBase;
import org.espilce.commons.lang.test.conversionutils.TestIJavaUrl;
import org.espilce.commons.lang.test.conversionutils.TestIParamsInvalid;
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.junit.jupiter.api.Test;

public abstract class TestJavaUrl2JavaFile extends TestABase
		implements TestIBase, TestIRelative, TestIAbsolute, TestIParamsInvalid, TestIJavaUrl
{
	@Override
	@Test
	public void absoluteFile_win_win() throws Exception {
		final URL input = new URL("file:/MyFile.ext");
		final File actual = invoke(input);
		final File expected = new File("/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFileSlashesExcess_win_win() throws Exception {
		final URL input = new URL("file:/myProject///folder///deep/myFile.ext//");
		final File actual = invoke(input);
		final File expected = new File("/myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlash_win_win() throws Exception {
		final URL input = new URL("file:/myProject/myFolder/");
		final File actual = invoke(input);
		final File expected = new File("/myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlashesInbetween_win_win() throws Exception {
		final URL input = new URL("file:/myProject///myFolder");
		final File actual = invoke(input);
		final File expected = new File("/myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFragmentQuery_win_win() throws Exception {
		final URL input = new URL("file:/myProject///myFolder?query#fragment");
		final File actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void absoluteNestedFile_win_win() throws Exception {
		final URL input = new URL("file:/some/path/MyFile.ext");
		final File actual = invoke(input);
		final File expected = new File("/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePath_win_win() throws Exception {
		final URL input = new URL("file:/resource/..////");
		final File actual = invoke(input);
		final File expected = new File("/resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePseudoFragment_win_win() throws Exception {
		final URL input = new URL("file:/myProject/myFolder%23query");
		final File actual = invoke(input);
		final File expected = new File("/myProject/myFolder#query");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final URL input = new URL("file://c:/some/path/MyFile.ext");
		final File actual = invoke(input);
		final File expected = new File("c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final URL input = new URL("file:/c:/some/path/MyFile.ext");
		final File actual = invoke(input);
		final File expected = new File("c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final URL input = new URL("file:///c:/some/path/MyFile.ext");
		final File actual = invoke(input);
		final File expected = new File("c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void current() throws Exception {
		final URL input = new URL("file:.");
		final File actual = invoke(input);
		final File expected = new File(".");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentRelativeNestedFile() throws Exception {
		final URL input = new URL("file:./some/path/MyFile.ext");
		final File actual = invoke(input);
		final File expected = new File("./some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentSlash() throws Exception {
		final URL input = new URL("file:./");
		final File actual = invoke(input);
		final File expected = new File("./");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void empty() throws Exception {
		final URL input = new URL("file:");
		final File actual = invoke(input);
		final File expected = new File("");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void invalidDoubleSlash() throws Exception {
		final URL input = new URL("file://");
		final File actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void invalidScheme() throws Exception {
		final URL input = new URL("http:/myProject/myFolder");
		final File actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void multiRelativePath() throws Exception {
		final URL input = new URL("file:resource/../some/dir/../../file.ext");
		final File actual = invoke(input);
		final File expected = new File("resource/../some/dir/../../file.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void opaqueScheme() throws Exception {
		final URL input = new URL("mailto:test@example.com");
		final File actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void paramNull() throws Exception {
		final URL input = (URL) null;
		final File actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void parent() throws Exception {
		final URL input = new URL("file:..");
		final File actual = invoke(input);
		final File expected = new File("..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFile() throws Exception {
		final URL input = new URL("file:MyFile.ext");
		final File actual = invoke(input);
		final File expected = new File("MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final URL input = new URL("file:myProject///folder///deep/myFile.ext//");
		final File actual = invoke(input);
		final File expected = new File("myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	
	@Override
	@Test
	public void relativeFolderSlash() throws Exception {
		final URL input = new URL("file:myProject/myFolder/");
		final File actual = invoke(input);
		final File expected = new File("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final URL input = new URL("file:myProject///myFolder");
		final File actual = invoke(input);
		final File expected = new File("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFragmentQuery() throws Exception {
		final URL input = new URL("file:myProject///myFolder?query#fragment");
		final File actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void relativeNestedFile() throws Exception {
		final URL input = new URL("file:some/path/MyFile.ext");
		final File actual = invoke(input);
		final File expected = new File("some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePath() throws Exception {
		final URL input = new URL("file:resource/..////");
		final File actual = invoke(input);
		final File expected = new File("resource/..");
		assertEquals(expected, actual);
	}
	
	
	@Override
	@Test
	public void relativePseudoFragment() throws Exception {
		final URL input = new URL("file:myProject///myFolder%23query");
		final File actual = invoke(input);
		final File expected = new File("myProject/myFolder#query/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void root_win_win() throws Exception {
		final URL input = new URL("file:/");
		final File actual = invoke(input);
		final File expected = new File("/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void startRelativePath() throws Exception {
		final URL input = new URL("file:../resource/..////");
		final File actual = invoke(input);
		final File expected = new File("../resource/..////");
		assertEquals(expected, actual);
	}
	
	@Override
	protected Class<?> getSourceType() { return URL.class; }
	
	@Override
	protected Class<?> getTargetType() { return File.class; }
	
	protected abstract File invoke(URL input);
}
