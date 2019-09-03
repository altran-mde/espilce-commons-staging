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
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.test.conversionutils.TestABase;
import org.junit.Test;

public abstract class TestJavaPath2JavaUri extends TestABase {
	@Test
	public void absoluteFileNested() throws Exception {
		final Path input = Paths.get("//input/to/myFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("file://input/to/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFileSlashesExcess() throws Exception {
		final Path input = Paths.get("//myProject///folder///deep/myFile.ext//");
		final URI actual = invoke(input);
		final URI expected = new URI("file://myProject/folder/deep/myFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFolderSlash() throws Exception {
		final Path input = Paths.get("//myProject/myFolder/");
		final URI actual = invoke(input);
		final URI expected = new URI("file://myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFolderSlashesInbetween() throws Exception {
		final Path input = Paths.get("//myProject///myFolder");
		final URI actual = invoke(input);
		final URI expected = new URI("file://myProject/myFolder/");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteFragment() throws Exception {
		final Path input = Paths.get("//myProject///myFolder#query");
		final URI actual = invoke(input);
		final URI expected = new URI("file://myProject/myFolder%23query/");
		assertEquals(expected, actual);
	}
	
	@Test
	public void absoluteNestedFile() throws Exception {
		final Path input = Paths.get("//some/input/MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("file://some/input/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void empty() throws Exception {
		final Path input = Paths.get("");
		final URI actual = invoke(input);
		final URI expected = new URI("file:/" + System.getProperty("user.dir").replace('\\', '/') + "/");
		assertEquals(expected, actual);
	}
	
	@Test
	public void file() throws Exception {
		final Path input = Paths.get("MyFile.ext");
		final URI actual = invoke(input);
		final URI expected = new URI("file:/" + System.getProperty("user.dir").replace('\\', '/') + "/MyFile.ext");
		assertEquals(expected, actual);
	}
	
	@Test
	public void input() throws Exception {
		final Path input = Paths.get("//resource/..////");
		final URI actual = invoke(input);
		final URI expected = new URI("file://resource/../");
		assertEquals(expected, actual);
	}
	
	@Test
	public void paramNull() throws Exception {
		final Path input = (Path) null;
		final URI actual = invoke(input);
		assertNull(actual);
	}
	
	@Override
	protected Class<?> getSourceType() { return Path.class; }
	
	@Override
	protected Class<?> getTargetType() { return URI.class; }
	
	protected abstract URI invoke(Path input);
}
