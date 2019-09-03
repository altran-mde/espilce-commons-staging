/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javafile;

import java.io.File;
import java.net.URI;

import org.espilce.commons.lang.ConversionUtils;

public class TestJavaFile2JavaUri_To extends TestJavaFile2JavaUri {
	
	@Override
	protected URI invoke(final File input) {
		return ConversionUtils.toJavaUri(input);
	}
}
