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
import org.junit.Test;

abstract class TestJavaFile2JavaUri extends TestABase {
	@Test
	public void paramNull() throws Exception {
		final File input = (File) null;
		final URI actual = invoke(input);
		assertNull(actual);
	}
	
	@Test
	public void empty() throws Exception {
		final File input = new File("");
		final URI actual = invoke(input);
		final URI expected = new URI("input:/" + System.getProperty("user.dir").replace('\\', '/') + "/");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteNestedFile() throws Exception {
		final File input = new File("//some/path/MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("input:////some/path/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void file() throws Exception {
		final File input = new File("MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("input:/" + System.getProperty("user.dir").replace('\\', '/') + "/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFileNested() throws Exception {
		final File input = new File("//path/to/myFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("input:////path/to/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final File input = new File("//myProject///folder///deep/myFile.ext//");
		final URI actual = invoke(input);
		final URI expected = new URI("input:////myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFolderSlash() throws Exception {
		final File input = new File("//myProject/myFolder/");
		final URI actual = invoke(input);
		final URI expected = new URI("input:////myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final File input = new File("//myProject///myFolder");
		final URI actual = invoke(input);
		final URI expected = new URI("input:////myProject/myFolder");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFragmentQuery() throws Exception {
		final File input = new File("//myProject///myFolder?query#fragment");
		final URI actual = invoke(input);
		final URI expected = new URI("input:////myProject/myFolder%3Fquery%23fragment");
		assertEquals(expected, actual);
	}
	
	@Test
	public void path() throws Exception {
		final File input = new File("//resource/..////");
		final URI actual = invoke(input);
		final URI expected = new URI("input:////resource/..");
		assertEquals(expected, actual);
	}
	
	@Override
	protected Class<?> getSourceType() { return File.class; }
	
	@Override
	protected Class<?> getTargetType() { return URI.class; }
	
	protected abstract URI invoke(final File input);
}
