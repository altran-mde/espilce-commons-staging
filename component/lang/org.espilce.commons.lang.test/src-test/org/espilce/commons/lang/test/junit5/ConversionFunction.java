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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.platform.commons.JUnitException;

public class ConversionFunction {
	private final Method method;
	private final boolean canReturnNull;
	private final Class<?> paramType;
	private final Class<?> returnType;
	
	ConversionFunction(
			final Method method, final Class<?> paramType, final Class<?> returnType, final boolean canReturnNull
	) {
		this.method = method;
		this.paramType = paramType;
		this.returnType = returnType;
		this.canReturnNull = canReturnNull;
	}
	
	public Object apply(final Object t) {
		try {
			return this.method.invoke(null, t);
		} catch (final InvocationTargetException e) {
			if (e.getCause() instanceof RuntimeException) {
				throw (RuntimeException) e.getCause();
			}
			throw new JUnitException("cannot invoke " + this.method, e);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new JUnitException("cannot invoke " + this.method, e);
		}
	}
	
	@Override
	public String toString() {
		return this.method.getName();
	}
	
	public boolean canReturnNull() {
		return this.canReturnNull;
	}
	
	public Class<?> getParamType() {
		return this.paramType;
	}

	public Class<?> getReturnType() {
		return this.returnType;
	}
}
