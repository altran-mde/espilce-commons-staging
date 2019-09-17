package org.espilce.commons.lang.test.junit5;

import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.util.Preconditions;
import org.junit.platform.commons.util.ReflectionUtils;

public abstract class AConversionArgumentsProvider implements ArgumentsProvider {
	protected static final String SEPARATOR = "/";
	
	private Class<?> conversionClass;
	private String methodName;
	private Class<?> paramType;
	private Class<?> returnType;
	
	protected void retrieveClassConfig(final ExtensionContext context) throws Exception {
		final Class<?> testClass = context.getTestClass()
				.orElseThrow(() -> new JUnitException("cannot get test class"));
		final ConversionConfig annotation = testClass.getAnnotation(ConversionConfig.class);
		this.conversionClass = annotation.conversionClass();
		this.methodName = annotation.methodName();
		this.paramType = annotation.paramType();
		this.returnType = annotation.returnType();
	}
	
	protected Class<?> getConversionClass() {
		return Preconditions.notNull(this.conversionClass, "No conversionClass found");
	}
	
	protected String getMethodName() { return Preconditions.notNull(this.methodName, "No methodName found"); }
	
	protected Class<?> getParamType() { return Preconditions.notNull(this.paramType, "No paramType found"); }
	
	protected Class<?> getReturnType() { return Preconditions.notNull(this.returnType, "No returnType found"); }
	
	protected Method getToMethod() {
		return ReflectionUtils.findMethod(getConversionClass(), "to" + getMethodName(), getParamType())
				.orElseThrow(() -> new JUnitException("Cannot find method"));
	}
	
	protected Method getAsMethod() {
		return ReflectionUtils.findMethod(getConversionClass(), "as" + getMethodName(), getParamType())
				.orElseThrow(() -> new JUnitException("Cannot find method"));
	}
	
	protected Stream<Function<Object, Object>> getMethods() {
		return Stream.<Function<Object, Object>> of(
				new ConversionFunction(getToMethod(), getParamType(), getReturnType(), true), new ConversionFunction(getAsMethod(), getParamType(), getReturnType(), false)
		);
	}
}
