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

public interface TestIAbsolute {
	
	void absoluteFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	void absoluteFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	
	void absoluteFileSlashesExcess_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception;
	
	void absoluteFileSlashesExcess_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception;
	
	
	void absoluteFolderSlash_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	void absoluteFolderSlash_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	
	void absoluteFolderSlashesInbetween_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception;
	
	void absoluteFolderSlashesInbetween_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception;
	
	
	void absoluteFragmentQuery_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	void absoluteFragmentQuery_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	
	void absoluteNestedFile_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	void absoluteNestedFile_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	
	void absolutePath_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	void absolutePath_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	
	void absolutePseudoFragment_win(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
	
	void absolutePseudoFragment_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception;
	
	
	void absoluteWindowsPathDoubleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception;
	
	void absoluteWindowsPathDoubleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception;
	
	
	void absoluteWindowsPathSingleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception;
	
	void absoluteWindowsPathSingleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception;
	
	
	void absoluteWindowsPathTripleSlash_win(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception;
	
	void absoluteWindowsPathTripleSlash_unix(
			final ConversionFunction fun, final String inputStr, final String expectedStr
	) throws Exception;
	
	
	void root_win(final ConversionFunction fun, final String inputStr, final String expectedStr) throws Exception;
	
	void root_unix(final ConversionFunction fun, final String inputStr, final String expectedStr)
			throws Exception;
}
