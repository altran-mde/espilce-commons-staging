/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.junit5;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.espilce.commons.exception.UnconvertibleException;

public class AssertConversion {
	public static void assertNullResult(final ConversionFunction fun, final Object input) {
		final String msg = "expected null: " + fun.toString() + "(" + input + ")";
		
		if (fun.canReturnNull()) {
			assertNull(fun.apply(input), msg);
		} else {
			assertThrows(NullPointerException.class, () -> fun.apply(input), msg);
		}
	}
	
	public static void assertIllegalConversion(final ConversionFunction fun, final Object input) {
		if (fun.canReturnNull()) {
			final String msg = "expected null (impossible conversion): " + fun.toString() + "(" + input + ")";
			assertNull(fun.apply(input), msg);
		} else {
			final UnconvertibleException ex = assertThrows(UnconvertibleException.class, () -> fun.apply(input));
			
			assertThat(ex, hasProperty("sourceType", equalTo(fun.getParamType())));
			assertThat(ex, hasProperty("targetType", equalTo(fun.getReturnType())));
		}
	}
}
