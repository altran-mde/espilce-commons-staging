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

public interface TestIScheme {
	
	void emptyWithScheme(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void fragment(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void fragmentQuery(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void inputNoSlashes(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void otherSchema(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void query(final ConversionFunction fun, final String inputStr) throws Exception;
	
	void relativeUri(final ConversionFunction fun, final String inputStr) throws Exception;
}
