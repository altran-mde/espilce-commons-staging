/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javafile.javapath;

import static org.espilce.commons.lang.test.junit5.AssertConversion.assertNullResult;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.test.conversionutils.TestIBase;
import org.espilce.commons.lang.test.junit5.ConversionConfig;
import org.espilce.commons.lang.test.junit5.ConversionFunction;
import org.espilce.commons.lang.test.junit5.TestConversion;

@ConversionConfig(conversionClass = ConversionUtils.class, paramType = File.class, returnType = Path.class)
public class TestJavaFile2JavaPath_base extends ATestJavaFile2JavaPath implements TestIBase {

	@Override
	@TestConversion(value = "", backslash = false)
	public void empty(final ConversionFunction fun, final String inputStr) throws Exception {
		final Path expected = Paths.get(inputStr);
		assertConversionEquals(fun, inputStr, expected);
	}
	
	@Override
	@TestConversion(/* null */ backslash = false)
	public void paramNull(final ConversionFunction fun, final String inputStr) throws Exception {
		final File input = (File) null;
		assertNullResult(fun, input);
	}
}
