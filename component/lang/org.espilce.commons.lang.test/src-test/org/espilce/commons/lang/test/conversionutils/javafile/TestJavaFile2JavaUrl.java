/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javafile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.net.URL;

import org.espilce.commons.lang.test.conversionutils.TestABase;
import org.junit.Test;

public abstract class TestJavaFile2JavaUrl extends TestABase {
	@Test
	public void paramNull() throws Exception {
		final File input = (File) null;
		final URL actual = invoke(input);
		assertNull(actual);
	}
	
	@Test
	public void empty() throws Exception {
		final File input = new File("");
		final URL actual = invoke(input);
		final URL expected = new URL("input:/" + System.getProperty("user.dir") + "/");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteNestedFile() throws Exception {
		final File input = new File("//some/path/MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("input:////some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void input() throws Exception {
		final File input = new File("MyFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("input:/" + System.getProperty("user.dir") + "/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFileNested() throws Exception {
		final File input = new File("//path/to/myFile.ext");
		final URL actual = invoke(input);
		final URL expected = new URL("input:////path/to/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final File input = new File("//myProject///folder///deep/myFile.ext//");
		final URL actual = invoke(input);
		final URL expected = new URL("input:////myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFolderSlash() throws Exception {
		final File input = new File("//myProject/myFolder/");
		final URL actual = invoke(input);
		final URL expected = new URL("input:////myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final File input = new File("//myProject///myFolder");
		final URL actual = invoke(input);
		final URL expected = new URL("input:////myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFragmentQuery() throws Exception {
		final File input = new File("//myProject///myFolder?query#fragment");
		final URL actual = invoke(input);
		final URL expected = new URL("input:////myProject/myFolder%3Fquery%23fragment");
		assertEquals(expected, actual);
	}
	
	@Test
	public void path() throws Exception {
		final File input = new File("//resource/..////");
		final URL actual = invoke(input);
		final URL expected = new URL("input:////resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	protected Class<?> getSourceType() { return File.class; }
	
	@Override
	protected Class<?> getTargetType() { return URL.class; }
	
	protected abstract URL invoke(final File input);
}
