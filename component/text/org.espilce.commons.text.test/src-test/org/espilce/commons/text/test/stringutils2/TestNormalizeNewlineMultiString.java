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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.espilce.commons.text.NewlineNormalizer;
import org.espilce.commons.text.StringUtils2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNormalizeNewlineMultiString {
	@BeforeEach
	public void resetDefault() {
		NewlineNormalizer.setDefault(NewlineNormalizer.LF);
	}
	
	@Test
	@SuppressWarnings("all")
	public void testNull() throws Exception {
		assertNull(StringUtils2.normalizeNewlines(null));
	}
	
	@Test
	public void noOp() throws Exception {
		assertArrayEquals(new String[] { "Some text" }, StringUtils2.normalizeNewlines("Some text"));
	}
	
	@Test
	public void multiple() throws Exception {
		assertArrayEquals(
				new String[] { "Some\nlonger\nstrange\ntext\n" },
				StringUtils2.normalizeNewlines("Some\rlonger\rstrange\rtext\r")
		);
	}
	
	@Test
	public void mixed() throws Exception {
		assertArrayEquals(
				new String[] { null, "hello\nthere!\n", null },
				StringUtils2.normalizeNewlines(new String[] { null, "hello\rthere!\r\n", null })
		);
	}
	
	@Test
	public void multi() throws Exception {
		assertArrayEquals(
				new String[] { "hello\nthere!\n", "Some\nlonger\nstrange\ntext\n" },
				StringUtils2
						.normalizeNewlines(new String[] { "hello\rthere!\r\n", "Some\nlonger\rstrange\r\ntext\n\r" })
		);
	}
	
	@Test
	public void setDefault() throws Exception {
		NewlineNormalizer.setDefault(NewlineNormalizer.WINDOWS);
		
		assertArrayEquals(
				new String[] { "Some\r\nlonger\r\nstrange\r\ntext\r\n" },
				StringUtils2.normalizeNewlines("Some\nlonger\rstrange\r\ntext\n\r")
		);
	}
	
}
