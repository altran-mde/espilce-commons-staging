/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.text.test.newlinenormalizer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.espilce.commons.text.NewlineNormalizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSingleString {
	@BeforeEach
	public void resetDefault() {
		NewlineNormalizer.setDefault(NewlineNormalizer.LF);
	}
	
	@Test
	public void testNull() throws Exception {
		assertNull(NewlineNormalizer.LF.normalize((String) null));
	}
	
	@Test
	public void empty() throws Exception {
		assertEquals("", NewlineNormalizer.LF.normalize(""));
	}
	
	@Test
	public void space() throws Exception {
		assertEquals("  ", NewlineNormalizer.LF.normalize("  "));
	}
	
	@Test
	public void noOp() throws Exception {
		assertEquals("Some text", NewlineNormalizer.LF.normalize("Some text"));
	}
	
	@Test
	public void noOpPrefix() throws Exception {
		assertEquals("\nSome text", NewlineNormalizer.LF.normalize("\nSome text"));
	}
	
	@Test
	public void noOpSuffix() throws Exception {
		assertEquals("Some text\n", NewlineNormalizer.LF.normalize("Some text\n"));
	}
	
	@Test
	public void noOpPrefixSuffix() throws Exception {
		assertEquals("\nSome text\n", NewlineNormalizer.LF.normalize("\nSome text\n"));
	}
	
	@Test
	public void noOpInfix() throws Exception {
		assertEquals("Some\ntext", NewlineNormalizer.LF.normalize("Some\ntext"));
	}
	
	@Test
	public void prefixR() throws Exception {
		assertEquals("\nSome text", NewlineNormalizer.LF.normalize("\rSome text"));
	}
	
	@Test
	public void suffixR() throws Exception {
		assertEquals("Some text\n", NewlineNormalizer.LF.normalize("Some text\r"));
	}
	
	@Test
	public void prefixSuffixR() throws Exception {
		assertEquals("\nSome text\n", NewlineNormalizer.LF.normalize("\rSome text\r"));
	}
	
	@Test
	public void infixR() throws Exception {
		assertEquals("Some\ntext", NewlineNormalizer.LF.normalize("Some\rtext"));
	}
	
	@Test
	public void multipleR() throws Exception {
		assertEquals("Some\nlonger\nstrange\ntext\n", NewlineNormalizer.LF.normalize("Some\rlonger\rstrange\rtext\r"));
	}
	
	@Test
	public void prefixN() throws Exception {
		assertEquals("\r\nSome text", NewlineNormalizer.CRLF.normalize("\nSome text"));
	}
	
	@Test
	public void suffixN() throws Exception {
		assertEquals("Some text\r\n", NewlineNormalizer.CRLF.normalize("Some text\n"));
	}
	
	@Test
	public void prefixSuffixN() throws Exception {
		assertEquals("\r\nSome text\r\n", NewlineNormalizer.CRLF.normalize("\nSome text\n"));
	}
	
	@Test
	public void infixN() throws Exception {
		assertEquals("Some\r\ntext", NewlineNormalizer.CRLF.normalize("Some\ntext"));
	}
	
	@Test
	public void multipleN() throws Exception {
		assertEquals(
				"Some\r\nlonger\r\nstrange\r\ntext\r\n",
				NewlineNormalizer.CRLF.normalize("Some\nlonger\nstrange\ntext\n")
		);
	}
	
	@Test
	public void prefixNR() throws Exception {
		assertEquals("\nSome text", NewlineNormalizer.LF.normalize("\n\rSome text"));
	}
	
	@Test
	public void suffixNR() throws Exception {
		assertEquals("Some text\n", NewlineNormalizer.LF.normalize("Some text\n\r"));
	}
	
	@Test
	public void prefixSuffixNR() throws Exception {
		assertEquals("\nSome text\n", NewlineNormalizer.LF.normalize("\n\rSome text\n\r"));
	}
	
	@Test
	public void infixNR() throws Exception {
		assertEquals("Some\ntext", NewlineNormalizer.LF.normalize("Some\n\rtext"));
	}
	
	@Test
	public void multipleNR() throws Exception {
		assertEquals(
				"Some\nlonger\nstrange\ntext\n",
				NewlineNormalizer.LF.normalize("Some\n\rlonger\n\rstrange\n\rtext\n\r")
		);
	}
	
	@Test
	public void prefixRN() throws Exception {
		assertEquals("\nSome text", NewlineNormalizer.LF.normalize("\r\nSome text"));
	}
	
	@Test
	public void suffixRN() throws Exception {
		assertEquals("Some text\n", NewlineNormalizer.LF.normalize("Some text\r\n"));
	}
	
	@Test
	public void prefixSuffixRN() throws Exception {
		assertEquals("\nSome text\n", NewlineNormalizer.LF.normalize("\r\nSome text\r\n"));
	}
	
	@Test
	public void infixRN() throws Exception {
		assertEquals("Some\ntext", NewlineNormalizer.LF.normalize("Some\r\ntext"));
	}
	
	@Test
	public void multipleRN() throws Exception {
		assertEquals(
				"Some\nlonger\nstrange\ntext\n",
				NewlineNormalizer.LF.normalize("Some\r\nlonger\r\nstrange\r\ntext\r\n")
		);
	}
}
