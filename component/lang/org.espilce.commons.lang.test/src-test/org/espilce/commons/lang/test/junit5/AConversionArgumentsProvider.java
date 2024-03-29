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

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.util.Preconditions;
import org.junit.platform.commons.util.ReflectionUtils;

/**
 * Builds method calls from {@link #conversionClass}, <code>as | to</code>,
 * {@link #returnType}, and {@link #paramType}.
 */
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
	
	protected Stream<ConversionFunction> getMethods() {
		return Stream.<ConversionFunction> of(
				new ConversionFunction(getToMethod(), getParamType(), getReturnType(), true),
				new ConversionFunction(getAsMethod(), getParamType(), getReturnType(), false)
		);
	}
}
