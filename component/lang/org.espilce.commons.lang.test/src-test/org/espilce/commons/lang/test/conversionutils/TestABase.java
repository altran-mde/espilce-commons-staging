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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

import org.espilce.commons.exception.UnconvertibleException;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public abstract class TestABase {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	protected void expectUnconvertibleException() {
		this.exception.expect(UnconvertibleException.class);
		this.exception.expect(hasProperty("sourceType", equalTo(getSourceType())));
		this.exception.expect(hasProperty("targetType", equalTo(getTargetType())));
	}
	
	protected Class<?> getSourceType() {
		throw new UnsupportedOperationException();
	}
	
	protected Class<?> getTargetType() {
		throw new UnsupportedOperationException();
	}
}
