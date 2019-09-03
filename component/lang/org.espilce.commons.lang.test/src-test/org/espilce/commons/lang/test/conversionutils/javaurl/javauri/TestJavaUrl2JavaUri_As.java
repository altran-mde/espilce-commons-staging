/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javaurl.javauri;

import java.net.URI;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUrl2JavaUri_As extends TestJavaUrl2JavaUri {
	@Override
	@Test(expected = NullPointerException.class)
	public void paramNull() throws Exception {
		super.paramNull();
	}
	
	@Override
	public void empty() throws Exception {
		expectUnconvertibleException();
		super.empty();
	}
	
	@Override
	protected URI invoke(final URL input) {
		return ConversionUtils.asJavaUri(input);
	}
}
