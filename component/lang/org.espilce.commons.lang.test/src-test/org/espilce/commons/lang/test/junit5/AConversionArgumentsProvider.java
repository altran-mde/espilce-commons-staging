package org.espilce.commons.lang.test.junit5;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.util.Preconditions;
import org.junit.platform.commons.util.ReflectionUtils;

public abstract class AConversionArgumentsProvider implements ArgumentsProvider {
	private static final String JAVA_FILE = "java.io.File";
	private static final String JAVA_PATH = "java.nio.file.Path";
	private static final String JAVA_URL = "java.net.URL";
	private static final String JAVA_URI = "java.net.URI";
	
	private static final String EMF_URI = "org.eclipse.emf.common.util.URI";
	
	private static final String IRESOURCE = "org.eclipse.core.resources.IResource";
	private static final String IPATH = "org.eclipse.core.runtime.IPath";

	protected static final String SEPARATOR = "/";
	protected static final String ALT_SEPARATOR = "\\";
	
	protected static final Map<String, String> METHOD_NAMES = new LinkedHashMap<>();
	static {
		METHOD_NAMES.put(JAVA_FILE, "JavaFile");
		METHOD_NAMES.put(JAVA_PATH, "JavaPath");
		METHOD_NAMES.put(JAVA_URI, "JavaUri");
		METHOD_NAMES.put(JAVA_URL, "JavaUrl");
		
		METHOD_NAMES.put(EMF_URI, "EmfUri");
		
		METHOD_NAMES.put(IRESOURCE, "IResource");
		METHOD_NAMES.put(IPATH, "IPath");
	}
	
	private Class<?> conversionClass;
	private Class<?> paramType;
	private Class<?> returnType;
	
	protected void retrieveClassConfig(final ExtensionContext context) throws Exception {
		final Class<?> testClass = context.getTestClass()
				.orElseThrow(() -> new JUnitException("cannot get test class"));
		final ConversionConfig annotation = testClass.getAnnotation(ConversionConfig.class);
		this.conversionClass = annotation.conversionClass();
		this.paramType = annotation.paramType();
		this.returnType = annotation.returnType();
	}
	
	protected Class<?> getConversionClass() {
		return Preconditions.notNull(this.conversionClass, "No conversionClass found");
	}
	
	protected String getMethodName() {
		return Preconditions.notNull(METHOD_NAMES.get(getReturnType().getName()), "No methodName found");
	}
	
	protected String getInverseMethodName() {
		return Preconditions.notNull(METHOD_NAMES.get(getParamType().getName()), "No inverseMethodName found");
	}
	
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
	
	protected Method getInverseToMethod() {
		return ReflectionUtils.findMethod(getConversionClass(), "to" + getInverseMethodName(), getReturnType())
				.orElseThrow(() -> new JUnitException("Cannot find method"));
	}
	
	protected Method getInverseAsMethod() {
		return ReflectionUtils.findMethod(getConversionClass(), "as" + getInverseMethodName(), getReturnType())
				.orElseThrow(() -> new JUnitException("Cannot find method"));
	}
	
	protected Stream<ConversionFunction> getMethods() {
		return Stream.<ConversionFunction> of(
				new ConversionFunction(
						getToMethod(), getInverseToMethod(), getParamType(), getReturnType(), true,
						canHandleBackslash(getParamType())
				),
				new ConversionFunction(
						getAsMethod(), getInverseAsMethod(), getParamType(), getReturnType(), false,
						canHandleBackslash(getParamType())
				)
		);
	}
	
	protected boolean canHandleBackslash(final Class<?> type) {
		switch (type.getName()) {
			case JAVA_FILE:
				return true;
			case JAVA_PATH:
				return true;
			case JAVA_URI:
				return false;
			case JAVA_URL:
				return false;
			
			case EMF_URI:
				return false;
			
			case IRESOURCE:
				return true;
			case IPATH:
				return true;
			default:
				throw new JUnitException("cannot handle " + type.getName());
		}
	}
}
