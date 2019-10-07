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

import java.net.URL;
import java.nio.file.Path;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIJavaUrl;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.TestConversion;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = URL.class, returnType = Path.class)
public class TestJavaUrl2JavaPath_javaUrl extends ATestJavaUrl2JavaPath implements TestIJavaUrl {
	
	@Override
	@TestConversion(value = "file://")
	public void invalidDoubleSlash(final ConversionFunction fun, final String inputStr) throws Exception {
		assertConversionEquals(fun, inputStr, "");
	}
}
