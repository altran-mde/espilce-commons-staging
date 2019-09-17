package org.espilce.commons.lang.test.junit5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import org.junit.platform.commons.JUnitException;

public class ConversionFunction implements Function<Object, Object> {
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
	
	@Override
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