/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javauri.javaurl;

import java.net.URI;
import java.net.URL;

import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUri2JavaUrl_As extends TestJavaUri2JavaUrl {
	
	@Override
	@Test(expected = NullPointerException.class)
	public void paramNull() throws Exception {
		super.paramNull();
	}
	
	@Override
	public void empty() throws Exception {
		expectUnconvertibleException();
		super.empty();
	}
	
	@Override
	public void inputBroken() throws Exception {
		expectUnconvertibleException();
		super.inputBroken();
	}
	
	@Override
	public void noSchema() throws Exception {
		expectUnconvertibleException();
		super.noSchema();
	}
	
	@Override
	public void currentNoScheme() throws Exception {
		expectUnconvertibleException();
		super.currentNoScheme();
	}
	
	@Override
	public void currentSlashNoScheme() throws Exception {
		expectUnconvertibleException();
		super.currentSlashNoScheme();
	}
	
	@Override
	public void absoluteNestedFileNoScheme() throws Exception {
		expectUnconvertibleException();
		super.absoluteNestedFileNoScheme();
	}
	
	@Override
	public void rootNoScheme() throws Exception {
		expectUnconvertibleException();
		super.rootNoScheme();
	}
	
	@Override
	public void startRelativePathNoScheme() throws Exception {
		expectUnconvertibleException();
		super.startRelativePathNoScheme();
	}
	
	@Override
	public void relativeNestedFileNoScheme() throws Exception {
		expectUnconvertibleException();
		super.relativeNestedFileNoScheme();
	}
	
	@Override
	public void currentRelativeNestedFileNoScheme() throws Exception {
		expectUnconvertibleException();
		super.currentRelativeNestedFileNoScheme();
	}
	
	@Override
	public void emptyNoScheme() throws Exception {
		expectUnconvertibleException();
		super.emptyNoScheme();
	}
	
	@Override
	protected URL invoke(final URI input) {
		return ConversionUtils.asJavaUrl(input);
	}
}
