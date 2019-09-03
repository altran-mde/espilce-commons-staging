/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri.javapath;

import java.net.URI;
import java.nio.file.Path;

import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUri2JavaPath_As extends TestJavaUri2JavaPath {
	@Override
	@Test(expected = NullPointerException.class)
	public void paramNull() throws Exception {
		super.paramNull();
	}
	
	@Override
	public void absoluteFragmentQuery() throws Exception {
		expectUnconvertibleException();
		super.absoluteFragmentQuery();
	}
	
	@Override
	public void relativeFragmentQuery() throws Exception {
		expectUnconvertibleException();
		super.relativeFragmentQuery();
	}
	
	@Override
	public void invalidScheme() throws Exception {
		expectUnconvertibleException();
		super.invalidScheme();
	}
	
	@Override
	public void opaqueScheme() throws Exception {
		expectUnconvertibleException();
		super.opaqueScheme();
	}
	
	@Override
	public void otherSchema() throws Exception {
		expectUnconvertibleException();
		super.otherSchema();
	}
	
	@Override
	public void fragment() throws Exception {
		expectUnconvertibleException();
		super.fragment();
	}
	
	@Override
	public void fragmentQuery() throws Exception {
		expectUnconvertibleException();
		super.fragmentQuery();
	}
	
	@Override
	public void inputBroken() throws Exception {
		expectUnconvertibleException();
		super.inputBroken();
	}
	
	@Override
	public void query() throws Exception {
		expectUnconvertibleException();
		super.query();
	}
	
	@Override
	protected Path invoke(final URI input) {
		return ConversionUtils.asJavaPath(input);
	}
}
