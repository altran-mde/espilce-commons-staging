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

public interface TestIJavaUri {
	void absoluteNestedFileNoScheme_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	void absoluteNestedFileNoScheme_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	
	void rootNoScheme_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	void rootNoScheme_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	
	void rootScheme_win(final ConversionFunction fun, final String inputStr, final String expectedStr) throws Exception;
	
	void rootScheme_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	
	void currentNoScheme(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void currentRelativeNestedFileNoScheme(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void currentSlashNoScheme(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void emptyNoScheme(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void inputBroken(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void noScheme(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void relativeNestedFileNoScheme(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void startRelativePathNoScheme(final ConversionFunction fun, final String inputStr) throws Exception;
}
