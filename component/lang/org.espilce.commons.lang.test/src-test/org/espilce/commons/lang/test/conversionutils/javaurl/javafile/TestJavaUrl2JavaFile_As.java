/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javaurl.javafile;

import java.io.File;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUrl2JavaFile_As extends TestJavaUrl2JavaFile {
	@Override
	@Test(expected = NullPointerException.class)
	public void paramNull() throws Exception {
		super.paramNull();
	}
	
	@Override
	public void invalidDoubleSlash() throws Exception {
		expectUnconvertibleException();
		super.invalidDoubleSlash();
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
	protected File invoke(final URL input) {
		return ConversionUtils.asJavaFile(input);
	}
}
