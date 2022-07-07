/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javaurl.javapath;

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;

import java.net.URL;
import java.nio.file.Path;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIParamsInvalid;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.TestConversion;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URL.class, returnType = Path.class)
public class TestJavaUrl2JavaPath_paramsInvalid extends ATestJavaUrl2JavaPath implements TestIParamsInvalid {
	
	@Override
	@TestConversion(value = "http:/myProject/myFolder", backslash = false)
	public void invalidScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL input = new URL(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(value = "mailto:test@example.com", backslash = false)
	public void opaqueScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URL input = new URL(inputStr);
		assertIllegalConversion(fun, input);
	}
}
