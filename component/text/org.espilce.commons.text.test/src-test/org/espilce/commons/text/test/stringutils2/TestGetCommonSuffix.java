/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.text.test.stringutils2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.espilce.commons.text.StringUtils2;
import org.junit.jupiter.api.Test;

public class TestGetCommonSuffix {
	@Test
	@SuppressWarnings("all")
	public void testNull() throws Exception {
		assertNull(StringUtils2.getCommonSuffix(null));
	}
	
	@Test
	public void empty() throws Exception {
		assertEquals("", StringUtils2.getCommonSuffix(new String[] {}));
	}
	
	@Test
	public void single() throws Exception {
		assertEquals("abc", StringUtils2.getCommonSuffix(new String[] { "abc" }));
	}
	
	@Test
	public void nullContent() throws Exception {
		assertEquals("", StringUtils2.getCommonSuffix(new String[] { null, null }));
	}
	
	@Test
	public void allEmpty() throws Exception {
		assertEquals("", StringUtils2.getCommonSuffix(new String[] { "", "" }));
	}
	
	@Test
	public void oneNull() throws Exception {
		assertEquals("", StringUtils2.getCommonSuffix(new String[] { "", null }));
	}
	
	@Test
	public void anyNull() throws Exception {
		assertEquals("", StringUtils2.getCommonSuffix(new String[] { "abc", null, null }));
	}
	
	@Test
	public void lastNotNull() throws Exception {
		assertEquals("", StringUtils2.getCommonSuffix(new String[] { null, null, "abc" }));
	}
	
	@Test
	public void firstEmpty() throws Exception {
		assertEquals("", StringUtils2.getCommonSuffix(new String[] { "", "abc" }));
	}
	
	@Test
	public void lastEmpty() throws Exception {
		assertEquals("", StringUtils2.getCommonSuffix(new String[] { "abc", "" }));
	}
	
	@Test
	public void allEqual() throws Exception {
		assertEquals("abc", StringUtils2.getCommonSuffix(new String[] { "abc", "abc" }));
	}
	
	@Test
	public void singleChar() throws Exception {
		assertEquals("c", StringUtils2.getCommonSuffix(new String[] { "abc", "c" }));
	}
	
	@Test
	public void twoChars() throws Exception {
		assertEquals("bc", StringUtils2.getCommonSuffix(new String[] { "bc", "zyxbc" }));
	}
	
	@Test
	public void twoCharsCommon() throws Exception {
		assertEquals("bc", StringUtils2.getCommonSuffix(new String[] { "abcbc", "zyxbc" }));
	}
	
	@Test
	public void noCommon() throws Exception {
		assertEquals("", StringUtils2.getCommonSuffix(new String[] { "abcde", "xyz" }));
	}
	
	@Test
	public void noCommonInverse() throws Exception {
		assertEquals("", StringUtils2.getCommonSuffix(new String[] { "xyz", "abcde" }));
	}
	
	@Test
	public void multiWord() throws Exception {
		assertEquals(" a am i", StringUtils2.getCommonSuffix(new String[] { "machine a am i", "robot a am i" }));
	}
}
