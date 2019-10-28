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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.espilce.commons.lang.test.junit5.ConversionFunction;

public abstract class ATestJavaFile2JavaPath {
	
	protected void assertConversionEquals(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) {
		final Path expected = Paths.get(expectedStr);
		assertConversionEquals(fun, inputStr, expected);
	}
	
	protected void assertConversionEquals(final ConversionFunction fun, final String inputStr, final Path expected) {
		final File input = new File(inputStr);
		final Object actual = fun.apply(input);
		assertEquals(expected, actual);
	}
}
