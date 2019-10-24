/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.text.dependency.pckg.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.espilce.commons.text.NewlineNormalizer;
import org.espilce.commons.text.StringUtils2;
import org.junit.jupiter.api.Test;

public class TestTextDependency {
	@Test
	public void newlineNormalizer() throws Exception {
		assertNotNull(new NewlineNormalizer("hello").normalize("this\nis"));
	}
	
	@Test
	public void stringUtils2() throws Exception {
		assertNotNull(StringUtils2.getCommonSuffix("a", "aa"));
	}
}
