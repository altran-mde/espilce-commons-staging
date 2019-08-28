/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javapath;

import java.net.URL;
import java.nio.file.Path;

import org.espilce.commons.lang.ConversionUtils;

public class TestJavaPath2JavaUrl_To extends TestJavaPath2JavaUrl {
	@Override
	protected URL invoke(final Path input) {
		return ConversionUtils.toJavaUrl(input);
	}
}
