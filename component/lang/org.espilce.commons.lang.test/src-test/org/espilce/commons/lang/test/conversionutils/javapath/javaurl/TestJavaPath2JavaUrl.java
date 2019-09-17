/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javapath.javaurl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.test.conversionutils.TestABase;
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.conversionutils.TestIBase;
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.junit.jupiter.api.Test;

public abstract class TestJavaPath2JavaUrl extends TestABase implements TestIBase, TestIAbsolute, TestIRelative {
	@Override
	@Test
	public void absoluteFile_win_win() throws Exception {
		final Path input = Paths.get("/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFileSlashesExcess_win_win() throws Exception {
		final Path input = Paths.get("//myProject///folder///deep/myFile.ext//");
		final URL actual = invoke(input);
		final URL expected = new URL("file://myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlash_win_win() throws Exception {
		final Path input = Paths.get("//myProject/myFolder/");
		final URL actual = invoke(input);
		final URL expected = new URL("file://myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlashesInbetween_win_win() throws Exception {
		final Path input = Paths.get("//myProject///myFolder");
		final URL actual = invoke(input);
		final URL expected = new URL("file://myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFragmentQuery_win_win() throws Exception {
		assertThrows(InvalidPathException.class, () -> Paths.get("/myProject/myFolder?query#fragment"));
	}
	
	@Override
	@Test
	public void absoluteNestedFile_win_win() throws Exception {
		final Path input = Paths.get("//some/input/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file://some/input/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePath_win_win() throws Exception {
		final Path input = Paths.get("//resource/..////");
		final URL actual = invoke(input);
		final URL expected = new URL("file://resource/../");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePseudoFragment_win_win() throws Exception {
		final Path input = Paths.get("//myProject///myFolder#query");
		final URL actual = invoke(input);
		final URL expected = new URL("file://myProject/myFolder%23query/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final Path input = Paths.get("c://some/path/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final Path input = Paths.get("c:/some/path/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final Path input = Paths.get("c:///some/path/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void current() throws Exception {
		final Path input = Paths.get(".");
		final URL actual = invoke(input);
		final URL expected = new URL("file:.");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentRelativeNestedFile() throws Exception {
		final Path input = Paths.get("./some/path/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:./some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentSlash() throws Exception {
		final Path input = Paths.get("./");
		final URL actual = invoke(input);
		final URL expected = new URL("file:.");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void empty() throws Exception {
		final Path input = Paths.get("");
		final URL actual = invoke(input);
		final URL expected = new URL("file:");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void multiRelativePath() throws Exception {
		final Path input = Paths.get("resource/../some/dir/../../file.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:resource/../some/dir/../../file.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void paramNull() throws Exception {
		final Path input = (Path) null;
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void parent() throws Exception {
		final Path input = Paths.get("..");
		final URL actual = invoke(input);
		final URL expected = new URL("file:..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFile() throws Exception {
		final Path input = Paths.get("MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final Path input = Paths.get("myProject///folder///deep/myFile.ext//");
		final URL actual = invoke(input);
		final URL expected = new URL("file:myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlash() throws Exception {
		final Path input = Paths.get("myProject/myFolder/");
		final URL actual = invoke(input);
		// trailing slash is swallowed by Paths.get()
		final URL expected = new URL("file:myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final Path input = Paths.get("myProject///myFolder");
		final URL actual = invoke(input);
		final URL expected = new URL("file:myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFragmentQuery() throws Exception {
		assertThrows(InvalidPathException.class, () -> Paths.get("myProject/myFolder?query#fragment"));
	}
	
	@Override
	@Test
	public void relativeNestedFile() throws Exception {
		final Path input = Paths.get("some/path/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("file:some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePath() throws Exception {
		final Path input = Paths.get("some/../where/");
		final URL actual = invoke(input);
		final URL expected = new URL("file:some/../where");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePseudoFragment() throws Exception {
		final Path input = Paths.get("myProject/myFolder#query");
		final URL actual = invoke(input);
		final URL expected = new URL("file:myProject/myFolder%23query");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void root_win_win() throws Exception {
		final Path input = Paths.get("/");
		final URL actual = invoke(input);
		final URL expected = new URL("file:/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void startRelativePath() throws Exception {
		final Path input = Paths.get("../resource/..");
		final URL actual = invoke(input);
		final URL expected = new URL("file:../resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	protected Class<?> getSourceType() { return URL.class; }
	
	@Override
	protected Class<?> getTargetType() { return Path.class; }
	
	protected abstract URL invoke(Path input);
}
