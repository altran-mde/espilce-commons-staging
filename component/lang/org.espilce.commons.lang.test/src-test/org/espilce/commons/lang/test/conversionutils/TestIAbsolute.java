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

public interface TestIAbsolute {
	
	void absoluteFile() throws Exception;
	
	void absoluteFileSlashesExcess() throws Exception;
	
	void absoluteFolderSlash() throws Exception;
	
	void absoluteFolderSlashesInbetween() throws Exception;
	
	void absoluteFragmentQuery() throws Exception;
	
	void absoluteNestedFile() throws Exception;
	
	void absolutePath() throws Exception;
	
	void absolutePseudoFragment() throws Exception;
	
	void absoluteWindowsPathDoubleSlash() throws Exception;
	
	void absoluteWindowsPathSingleSlash() throws Exception;
	
	void absoluteWindowsPathTripleSlash() throws Exception;
	
	void root() throws Exception;
}
