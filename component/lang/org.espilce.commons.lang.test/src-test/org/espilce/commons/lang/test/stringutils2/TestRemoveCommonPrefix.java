/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.stringutils2;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import org.espilce.commons.lang.StringUtils2;
import org.junit.Test;

public class TestRemoveCommonPrefix {
	@Test
	@SuppressWarnings("all")
	public void testNull() throws Exception {
		assertNull(StringUtils2.removeCommonPrefix(null));
	}
	
	@Test
	public void empty() throws Exception {
		assertArrayEquals(new String[0], StringUtils2.removeCommonPrefix(new String[] {}));
	}
	
	@Test
	public void single() throws Exception {
		assertArrayEquals(new String[] { "abc" }, StringUtils2.removeCommonPrefix(new String[] { "abc" }));
	}
	
	@Test
	public void nullContent() throws Exception {
		assertArrayEquals(new String[] { null, null }, StringUtils2.removeCommonPrefix(new String[] { null, null }));
	}
	
	@Test
	public void allEmpty() throws Exception {
		assertArrayEquals(new String[] { "", "" }, StringUtils2.removeCommonPrefix(new String[] { "", "" }));
	}
	
	@Test
	public void allButOneEmpty() throws Exception {
		assertArrayEquals(
				new String[] { "", "abc", "" },
				StringUtils2.removeCommonPrefix(new String[] { "", "abc", "" })
		);
	}
	
	@Test
	public void oneNull() throws Exception {
		assertArrayEquals(new String[] { "", null }, StringUtils2.removeCommonPrefix(new String[] { "", null }));
	}
	
	@Test
	public void anyNull() throws Exception {
		assertArrayEquals(
				new String[] { "abc", null, null },
				StringUtils2.removeCommonPrefix(new String[] { "abc", null, null })
		);
	}
	
	@Test
	public void lastNotNull() throws Exception {
		assertArrayEquals(
				new String[] { null, null, "abc" },
				StringUtils2.removeCommonPrefix(new String[] { null, null, "abc" })
		);
	}
	
	@Test
	public void firstEmpty() throws Exception {
		assertArrayEquals(new String[] { "", "abc" }, StringUtils2.removeCommonPrefix(new String[] { "", "abc" }));
	}
	
	@Test
	public void lastEmpty() throws Exception {
		assertArrayEquals(new String[] { "abc", "" }, StringUtils2.removeCommonPrefix(new String[] { "abc", "" }));
	}
	
	@Test
	public void allEqual() throws Exception {
		assertArrayEquals(new String[] { "", "" }, StringUtils2.removeCommonPrefix(new String[] { "abc", "abc" }));
	}
	
	@Test
	public void singleChar() throws Exception {
		assertArrayEquals(new String[] { "bc", "" }, StringUtils2.removeCommonPrefix(new String[] { "abc", "a" }));
	}
	
	@Test
	public void twoChars() throws Exception {
		assertArrayEquals(new String[] { "", "xyz" }, StringUtils2.removeCommonPrefix(new String[] { "ab", "abxyz" }));
	}
	
	@Test
	public void twoCharsCommon() throws Exception {
		assertArrayEquals(
				new String[] { "cde", "xyz" },
				StringUtils2.removeCommonPrefix(new String[] { "abcde", "abxyz" })
		);
	}
	
	@Test
	public void noCommon() throws Exception {
		assertArrayEquals(
				new String[] { "abcde", "xyz" },
				StringUtils2.removeCommonPrefix(new String[] { "abcde", "xyz" })
		);
	}
	
	@Test
	public void noCommonInverse() throws Exception {
		assertArrayEquals(
				new String[] { "xyz", "abcde" },
				StringUtils2.removeCommonPrefix(new String[] { "xyz", "abcde" })
		);
	}
	
	@Test
	public void multiWord() throws Exception {
		assertArrayEquals(
				new String[] { "machine", "robot" },
				StringUtils2.removeCommonPrefix(new String[] { "i am a machine", "i am a robot" })
		);
	}
	
}
