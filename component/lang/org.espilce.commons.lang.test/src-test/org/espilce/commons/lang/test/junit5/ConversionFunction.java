package org.espilce.commons.lang.test.junit5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.platform.commons.JUnitException;

public class ConversionFunction {
	private final Method method;
	private final Method inverseMethod;
	private final boolean canReturnNull;
	private final Class<?> paramType;
	private final Class<?> returnType;
	private boolean canHandleBackslash;
	
	ConversionFunction(
			final Method method, final Method inverseMethod, final Class<?> paramType, final Class<?> returnType,
			final boolean canReturnNull, final boolean canHandleBackslash
	) {
		this.method = method;
		this.inverseMethod = inverseMethod;
		this.paramType = paramType;
		this.returnType = returnType;
		this.canReturnNull = canReturnNull;
		this.canHandleBackslash = canHandleBackslash;
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
	
	public Object applyInverse(final Object t) {
		try {
			return this.inverseMethod.invoke(null, t);
		} catch (final InvocationTargetException e) {
			if (e.getCause() instanceof RuntimeException) {
				throw (RuntimeException) e.getCause();
			}
			throw new JUnitException("cannot invoke " + this.inverseMethod, e);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new JUnitException("cannot invoke " + this.inverseMethod, e);
		}
	}
	
	@Override
	public String toString() {
		return this.method.getName();
	}
	
	public boolean canReturnNull() {
		return this.canReturnNull;
	}

	public boolean canHandleBackslash() {
		return this.canHandleBackslash;
	}
	
	public Class<?> getParamType() {
		return this.paramType;
	}

	public Class<?> getReturnType() {
		return this.returnType;
	}
}