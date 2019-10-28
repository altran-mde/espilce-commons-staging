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
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.espilce.commons.text.NewlineNormalizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNewlineNormalizer {
	@BeforeEach
	public void resetDefault() {
		NewlineNormalizer.setDefault(NewlineNormalizer.LF);
	}
	
	@Test
	public void prefixCustom() throws Exception {
		assertEquals("<br/>Some text", createCustom().normalize("\rSome text"));
	}
	
	@Test
	public void suffixCustom() throws Exception {
		assertEquals("Some text<br/>", createCustom().normalize("Some text\r"));
	}
	
	@Test
	public void prefixSuffixCustom() throws Exception {
		assertEquals("<br/>Some text<br/>", createCustom().normalize("\rSome text\r"));
	}
	
	@Test
	public void infixCustom() throws Exception {
		assertEquals("Some<br/>text", createCustom().normalize("Some\rtext"));
	}
	
	@Test
	public void multipleCustom() throws Exception {
		assertEquals(
				"Some<br/>longer<br/>strange<br/>text<br/>",
				createCustom().normalize("Some\rlonger\rstrange\rtext\r")
		);
	}
	
	private NewlineNormalizer createCustom() {
		return new NewlineNormalizer("<br/>");
	}
	
	@SuppressWarnings("null")
	@Test
	public void newNull() throws Exception {
		assertThrows(NullPointerException.class, () -> new NewlineNormalizer(null));
	}
	
	@Test
	public void mixedLF() throws Exception {
		assertEquals(
				"Some\nlonger\nstrange\ntext\n",
				NewlineNormalizer.LF.normalize("Some\nlonger\rstrange\r\ntext\n\r")
		);
	}
	
	@Test
	public void mixedUnix() throws Exception {
		assertEquals(
				"Some\nlonger\nstrange\ntext\n",
				NewlineNormalizer.UNIX.normalize("Some\nlonger\rstrange\r\ntext\n\r")
		);
	}
	
	@Test
	public void mixedCR() throws Exception {
		assertEquals(
				"Some\rlonger\rstrange\rtext\r",
				NewlineNormalizer.CR.normalize("Some\nlonger\rstrange\r\ntext\n\r")
		);
	}
	
	@Test
	public void mixedMac() throws Exception {
		assertEquals(
				"Some\rlonger\rstrange\rtext\r",
				NewlineNormalizer.MAC.normalize("Some\nlonger\rstrange\r\ntext\n\r")
		);
	}
	
	@Test
	public void mixedCRLF() throws Exception {
		assertEquals(
				"Some\r\nlonger\r\nstrange\r\ntext\r\n",
				NewlineNormalizer.CRLF.normalize("Some\nlonger\rstrange\r\ntext\n\r")
		);
	}
	
	@Test
	public void mixedWindows() throws Exception {
		assertEquals(
				"Some\r\nlonger\r\nstrange\r\ntext\r\n",
				NewlineNormalizer.WINDOWS.normalize("Some\nlonger\rstrange\r\ntext\n\r")
		);
	}
	
	@Test
	public void mixedLFCR() throws Exception {
		assertEquals(
				"Some\n\rlonger\n\rstrange\n\rtext\n\r",
				NewlineNormalizer.LFCR.normalize("Some\nlonger\rstrange\r\ntext\n\r")
		);
	}
	
	@Test
	public void mixedDefault() throws Exception {
		assertEquals(
				"Some\nlonger\nstrange\ntext\n",
				NewlineNormalizer.getDefault().normalize("Some\nlonger\rstrange\r\ntext\n\r")
		);
	}
	
	@Test
	public void mixedSetDefault() throws Exception {
		NewlineNormalizer.setDefault(NewlineNormalizer.WINDOWS);
		
		assertEquals(
				"Some\r\nlonger\r\nstrange\r\ntext\r\n",
				NewlineNormalizer.getDefault().normalize("Some\nlonger\rstrange\r\ntext\n\r")
		);
	}
	
	@SuppressWarnings("null")
	@Test
	public void setDefaultNull() throws Exception {
		assertThrows(NullPointerException.class, () -> NewlineNormalizer.setDefault(null));
	}
	
	
}
