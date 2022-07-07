/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri.javaurl;

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertIllegalConversion;
import static org.espilce.commons.lang.test.junit5.AssertConversion.assertNullResult;

import java.net.URI;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIBase;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.TestConversion;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URI.class, returnType = URL.class)
public class TestJavaUri2JavaUrl_base extends ATestJavaUri2JavaUrl implements TestIBase {
	
	@Override
	@TestConversion(value = "", backslash = false)
	public void empty(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = new URI(null, inputStr, null);
		assertIllegalConversion(fun, input);
	}
	
	@Override
	@TestConversion(/* null */ backslash = false)
	public void paramNull(final ConversionFunction fun, final String inputStr) throws Exception {
		final URI input = null;
		assertNullResult(fun, input);
	}
}
