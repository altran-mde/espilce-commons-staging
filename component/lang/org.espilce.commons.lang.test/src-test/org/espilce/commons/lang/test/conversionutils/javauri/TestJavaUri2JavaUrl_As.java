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
import java.net.URL;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.junit.Test;

public class TestJavaUri2JavaUrl_As extends TestJavaUri2JavaUrl {
	@Override
	@Test(expected = NullPointerException.class)
	public void paramNull() throws Exception {
		super.paramNull();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void empty() throws Exception {
		super.empty();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void inputBroken() throws Exception {
		super.inputBroken();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void noSchema() throws Exception {
		super.noSchema();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void currentNoScheme() throws Exception {
		super.currentNoScheme();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void currentSlashNoScheme() throws Exception {
		super.currentSlashNoScheme();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void absoluteNestedFileNoScheme() throws Exception {
		super.absoluteNestedFileNoScheme();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void rootNoScheme() throws Exception {
		super.rootNoScheme();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void startRelativePathNoScheme() throws Exception {
		super.startRelativePathNoScheme();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void relativeNestedFileNoScheme() throws Exception {
		super.relativeNestedFileNoScheme();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void currentRelativeNestedFileNoScheme() throws Exception {
		super.currentRelativeNestedFileNoScheme();
	}
	
	@Override
	@Test(expected = UnconvertibleException.class)
	public void emptyNoScheme() throws Exception {
		super.emptyNoScheme();
	}
	
	@Override
	protected URL invoke(final URI input) {
		return ConversionUtils.asJavaUrl(input);
	}
}
