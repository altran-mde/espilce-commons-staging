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

import org.espilce.commons.text.NewlineNormalizer;
import org.espilce.commons.text.StringUtils2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNormalizeNewlineSingleString {
	@BeforeEach
	public void resetDefault() {
		NewlineNormalizer.setDefault(NewlineNormalizer.LF);
	}
	
	@Test
	public void testNull() throws Exception {
		assertNull(StringUtils2.normalizeNewline(null));
	}
	
	@Test
	public void noOp() throws Exception {
		assertEquals("Some text", StringUtils2.normalizeNewline("Some text"));
	}
	
	@Test
	public void multiple() throws Exception {
		assertEquals("Some\nlonger\nstrange\ntext\n", StringUtils2.normalizeNewline("Some\rlonger\rstrange\rtext\r"));
	}
	
	@Test
	public void setDefault() throws Exception {
		NewlineNormalizer.setDefault(NewlineNormalizer.WINDOWS);
		
		assertEquals(
				"Some\r\nlonger\r\nstrange\r\ntext\r\n",
				StringUtils2.normalizeNewline("Some\nlonger\rstrange\r\ntext\n\r")
		);
	}
	
}
