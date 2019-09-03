/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javafile.javauri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.net.URI;

import org.espilce.commons.lang.test.conversionutils.TestABase;
import org.espilce.commons.lang.test.conversionutils.TestIAbsolute;
import org.espilce.commons.lang.test.conversionutils.TestIBase;
import org.espilce.commons.lang.test.conversionutils.TestIRelative;
import org.junit.Test;

public abstract class TestJavaFile2JavaUri extends TestABase implements TestIBase, TestIAbsolute, TestIRelative {
	
	@Override
	@Test
	public void absoluteFile() throws Exception {
		final File input = new File("//MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("file:////MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final File input = new File("//myProject///folder///deep/myFile.ext//");
		final URI actual = invoke(input);
		final URI expected = new URI("file:////myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlash() throws Exception {
		final File input = new File("//myProject/myFolder/");
		final URI actual = invoke(input);
		final URI expected = new URI("file:////myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final File input = new File("//myProject///myFolder");
		final URI actual = invoke(input);
		final URI expected = new URI("file:////myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteFragmentQuery() throws Exception {
		final File input = new File("//myProject/myFolder?query#fragment");
		final URI actual = invoke(input);
		final URI expected = new URI("file:////myProject/myFolder%3Fquery%23fragment");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteNestedFile() throws Exception {
		final File input = new File("//some/path/MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("file:////some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePath() throws Exception {
		final File input = new File("//resource/..////");
		final URI actual = invoke(input);
		// trailing slashes are swallowed by File()
		final URI expected = new URI("file:////resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absolutePseudoFragment() throws Exception {
		final File input = new File("//myProject/myFolder#query");
		final URI actual = invoke(input);
		final URI expected = new URI("file:////myProject/myFolder%23query");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathDoubleSlash() throws Exception {
		final File input = new File("//c:/some/path/MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathSingleSlash() throws Exception {
		final File url = new File("/c:/some/path/MyFile.ext");
		final URI actual = invoke(url);
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void absoluteWindowsPathTripleSlash() throws Exception {
		final File url = new File("///c:/some/path/MyFile.ext");
		final URI actual = invoke(url);
		final URI expected = new URI("file:/c:/some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void current() throws Exception {
		final File input = new File(".");
		final URI actual = invoke(input);
		final URI expected = URI.create(".");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentRelativeNestedFile() throws Exception {
		final File input = new File("./some/path/MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = URI.create("./some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void currentSlash() throws Exception {
		final File input = new File("./");
		final URI actual = invoke(input);
		// trailing slash is swallowed by File()
		final URI expected = URI.create(".");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void empty() throws Exception {
		final File input = new File("");
		final URI actual = invoke(input);
		final URI expected = URI.create("");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void multiRelativePath() throws Exception {
		final File input = new File("resource/../some/dir/../../file.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("resource/../some/dir/../../file.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void paramNull() throws Exception {
		final File input = (File) null;
		final URI actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	@Test
	public void parent() throws Exception {
		final File input = new File("..");
		final URI actual = invoke(input);
		final URI expected = new URI("..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFile() throws Exception {
		final File input = new File("MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFileSlashesExcess() throws Exception {
		final File input = new File("myProject///folder///deep/myFile.ext//");
		final URI actual = invoke(input);
		final URI expected = new URI("myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlash() throws Exception {
		final File input = new File("myProject/myFolder/");
		final URI actual = invoke(input);
		final URI expected = new URI("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFolderSlashesInbetween() throws Exception {
		final File input = new File("myProject///myFolder");
		final URI actual = invoke(input);
		final URI expected = new URI("myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeFragmentQuery() throws Exception {
		final File input = new File("myProject/myFolder?query#fragment");
		final URI actual = invoke(input);
		final URI expected = new URI("myProject/myFolder%3Fquery%23fragment");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativeNestedFile() throws Exception {
		final File input = new File("some/path/MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePath() throws Exception {
		final File input = new File("//resource/..////");
		final URI actual = invoke(input);
		final URI expected = new URI("file:////resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void relativePseudoFragment() throws Exception {
		final File input = new File("myProject///myFolder#query");
		final URI actual = invoke(input);
		final URI expected = new URI("myProject/myFolder%23query");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void root() throws Exception {
		final File input = new File("//");
		final URI actual = invoke(input);
		final URI expected = new URI("file:////");
		assertEquals(expected, actual);
	}
	
	@Override
	@Test
	public void startRelativePath() throws Exception {
		final File input = new File("../resource/..////");
		final URI actual = invoke(input);
		final URI expected = URI.create("../resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	protected Class<?> getSourceType() { return File.class; }
	
	@Override
	protected Class<?> getTargetType() { return URI.class; }
	
	protected abstract URI invoke(final File input);
}
