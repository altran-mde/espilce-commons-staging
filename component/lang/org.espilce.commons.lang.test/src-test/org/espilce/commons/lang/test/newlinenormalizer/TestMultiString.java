/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.newlinenormalizer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import org.espilce.commons.lang.NewlineNormalizer;
import org.junit.Test;

public class TestMultiString {
	@Test
	public void testNull() throws Exception {
		assertNull(NewlineNormalizer.LF.normalize((String[]) null));
	}

	@Test
	public void empty() throws Exception {
		assertArrayEquals(new String[0], NewlineNormalizer.LF.normalize(new String[] {}));
	}

	@Test
	public void onlyNull() throws Exception {
		assertArrayEquals(new String[] { null }, NewlineNormalizer.LF.normalize(new String[] { null }));
	}

	@Test
	public void noOpMixed() throws Exception {
		assertArrayEquals(new String[] { "hello", null },
				NewlineNormalizer.LF.normalize(new String[] { "hello", null }));
	}

	@Test
	public void single() throws Exception {
		assertArrayEquals(new String[] { "hello\nthere!\n" },
				NewlineNormalizer.LF.normalize(new String[] { "hello\rthere!\r\n" }));
	}

	@Test
	public void mixed() throws Exception {
		assertArrayEquals(new String[] { null, "hello\nthere!\n", null },
				NewlineNormalizer.LF.normalize(new String[] { null, "hello\rthere!\r\n", null }));
	}

	@Test
	public void multi() throws Exception {
		assertArrayEquals(new String[] { "hello\nthere!\n", "Some\nlonger\nstrange\ntext\n" },
				NewlineNormalizer.LF.normalize(new String[] { "hello\rthere!\r\n", "Some\nlonger\rstrange\r\ntext\n\r" }));
	}
}
