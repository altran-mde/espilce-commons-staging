/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.espilce.commons.exception.UnconvertibleException;
import org.junit.jupiter.api.function.Executable;

public abstract class TestABase {
	
	protected void expectUnconvertibleException(final Executable executable) {
		final UnconvertibleException ex = assertThrows(UnconvertibleException.class, executable);
		
		assertThat(ex, hasProperty("sourceType", equalTo(getSourceType())));
		assertThat(ex, hasProperty("targetType", equalTo(getTargetType())));
	}
	
	protected Class<?> getSourceType() {
		throw new UnsupportedOperationException();
	}
	
	protected Class<?> getTargetType() {
		throw new UnsupportedOperationException();
	}
}
