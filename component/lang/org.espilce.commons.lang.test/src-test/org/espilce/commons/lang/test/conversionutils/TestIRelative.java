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

public interface TestIRelative {
	
	void current(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void currentRelativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void currentSlash(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void multiRelativePath(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void parent(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void relativeFile(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void relativeFileSlashesExcess(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void relativeFolderSlash(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void relativeFolderSlashesInbetween(final ConversionFunction fun, final String inputStr) throws Exception;
	
	
	void relativeFragmentQuery_win(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void relativeFragmentQuery_unix(final ConversionFunction fun, final String inputStr) throws Exception;
	
	
	void relativeNestedFile(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void relativePath(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void relativePseudoFragment(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void startRelativePath(final ConversionFunction fun, final String inputStr) throws Exception;
	
}
