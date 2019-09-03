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

public interface TestIParamsInvalid {
	
	void absoluteFragmentQuery() throws Exception;
	
	void relativeFragmentQuery() throws Exception;
	
	void invalidScheme() throws Exception;
	
	void opaqueScheme() throws Exception;
}
