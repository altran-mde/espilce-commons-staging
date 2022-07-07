/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri.javafile;

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;

import java.io.File;
import java.net.URI;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIParamsInvalid;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.TestConversion;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URI.class, returnType = File.class)
public class TestJavaUri2JavaFile_paramsInvalid extends ATestJavaUri2JavaFile implements TestIParamsInvalid {
	
	@Override
	@TestConversion(value = "http:/myProject/myFolder", backslash = false)
	public void invalidScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(value = "mailto:test@example.com", backslash = false)
	public void opaqueScheme(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(inputStr);
		assertIllegalConversion(fun, input);
	}
}
