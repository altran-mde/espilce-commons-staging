/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javapath.javauri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URI;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.test.conversionutils.TestABase;
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.conversionutils.TestIBase;
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.junit.Ignore;
import org.junit.Test;

public abstract class TestJavaPath2JavaUri extends TestABase implements TestIBase, TestIAbsolute, TestIRelative {
	@Override
	@Test
	public void absoluteFile() throws Exception {
		final Path input = Paths.get("//bla/MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("file://bla/MyFile.ext/");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFileNested() throws Exception {
		final Path input = Paths.get("//input/to/myFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("file://input/to/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final Path input = Paths.get("//myProject///folder///deep/myFile.ext//");
		final URI actual = invoke(input);
		final URI expected = new URI("file://myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlash() throws Exception {
		final Path input = Paths.get("//myProject/myFolder/");
		final URI actual = invoke(input);
		final URI expected = new URI("file://myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final Path input = Paths.get("//myProject///myFolder");
		final URI actual = invoke(input);
		final URI expected = new URI("file://myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test(expected = InvalidPathException.class)
	public void absoluteFragmentQuery() throws Exception {
		Paths.get("//myProject/myFolder?query#fragment");
	}
	
	@Override
	@Test
	public void absoluteNestedFile() throws Exception {
		final Path input = Paths.get("//some/input/MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("file://some/input/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePath() throws Exception {
		final Path input = Paths.get("//resource/..////");
		final URI actual = invoke(input);
		final URI expected = new URI("file://resource/../");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePseudoFragment() throws Exception {
		final Path input = Paths.get("//myProject///myFolder#query");
		final URI actual = invoke(input);
		final URI expected = new URI("file://myProject/myFolder%23query/");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final Path input = Paths.get("c://some/path/MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final Path url = Paths.get("c:/some/path/MyFile.ext");
		final URI actual = invoke(url);
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final Path url = Paths.get("c:///some/path/MyFile.ext");
		final URI actual = invoke(url);
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void current() throws Exception {
		final Path input = Paths.get(".");
		final URI actual = invoke(input);
		final URI expected = URI.create(".");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentRelativeNestedFile() throws Exception {
		final Path input = Paths.get("./some/path/MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = URI.create("./some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentSlash() throws Exception {
		final Path input = Paths.get("./");
		final URI actual = invoke(input);
		// trailing slash is swallowed by Paths.get()
		final URI expected = URI.create(".");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void empty() throws Exception {
		final Path input = Paths.get("");
		final URI actual = invoke(input);
		final URI expected = new URI("");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void multiRelativePath() throws Exception {
		final Path input = Paths.get("resource/../some/dir/../../file.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("resource/../some/dir/../../file.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void paramNull() throws Exception {
		final Path input = (Path) null;
		final URI actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void parent() throws Exception {
		final Path input = Paths.get("..");
		final URI actual = invoke(input);
		final URI expected = new URI("..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFile() throws Exception {
		final Path input = Paths.get("MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final Path input = Paths.get("myProject///folder///deep/myFile.ext//");
		final URI actual = invoke(input);
		final URI expected = new URI("myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlash() throws Exception {
		final Path input = Paths.get("myProject/myFolder/");
		final URI actual = invoke(input);
		final URI expected = new URI("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final Path input = Paths.get("myProject///myFolder");
		final URI actual = invoke(input);
		final URI expected = new URI("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test(expected = InvalidPathException.class)
	public void relativeFragmentQuery() throws Exception {
		Paths.get("myProject/myFolder?query#fragment");
	}
	
	@Override
	@Test
	public void relativeNestedFile() throws Exception {
		final Path input = Paths.get("some/path/MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePath() throws Exception {
		final Path input = Paths.get("resource/..////");
		final URI actual = invoke(input);
		final URI expected = new URI("resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePseudoFragment() throws Exception {
		final Path input = Paths.get("myProject///myFolder#query");
		final URI actual = invoke(input);
		final URI expected = new URI("myProject/myFolder%23query");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	@Ignore("Windows path mess")
	public void root() throws Exception {
		final Path input = Paths.get("/");
		final URI actual = invoke(input);
		final URI expected = new URI("file:////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void startRelativePath() throws Exception {
		final Path input = Paths.get("../resource/..////");
		final URI actual = invoke(input);
		final URI expected = URI.create("../resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	protected Class<?> getSourceType() { return Path.class; }
	
	@Override
	protected Class<?> getTargetType() { return URI.class; }
	
	protected abstract URI invoke(Path input);
}
