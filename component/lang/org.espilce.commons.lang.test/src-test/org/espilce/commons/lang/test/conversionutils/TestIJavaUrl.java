/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils;

import org.espilce.commons.lang.test.junit5.ConversionFunction;

public interface TestIJavaUrl {
	void invalidDoubleSlash(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void emptyScheme(final ConversionFunction fun, final String inputStr) throws Exception;
}
