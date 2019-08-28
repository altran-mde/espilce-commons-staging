/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri;

import java.net.URI;
import java.nio.file.Path;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUri2JavaPath_As extends TestJavaUri2JavaPath {
	@Override
	@Test(expected = NullPointerException.class)
	public void paramNull() throws Exception {
		super.paramNull();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void absoluteFragmentQuery() throws Exception {
		super.absoluteFragmentQuery();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void relativeFragmentQuery() throws Exception {
		super.relativeFragmentQuery();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void invalidScheme() throws Exception {
		super.invalidScheme();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void opaqueScheme() throws Exception {
		super.opaqueScheme();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void otherSchema() throws Exception {
		super.otherSchema();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void fragment() throws Exception {
		super.fragment();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void fragmentQuery() throws Exception {
		super.fragmentQuery();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void inputBroken() throws Exception {
		super.inputBroken();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void query() throws Exception {
		super.query();
	}
	
	@Override
	protected Path invoke(final URI input) {
		return ConversionUtils.asJavaPath(input);
	}
}
