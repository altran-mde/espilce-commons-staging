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

public interface TestIRelative {
	
	void current() throws Exception;
	
	void currentRelativeNestedFile() throws Exception;
	
	void currentSlash() throws Exception;
	
	void multiRelativePath() throws Exception;
	
	void parent() throws Exception;
	
	void relativeFile() throws Exception;
	
	void relativeFileSlashesExcess() throws Exception;
	
	void relativeFolderSlash() throws Exception;
	
	void relativeFolderSlashesInbetween() throws Exception;
	
	void relativeFragmentQuery() throws Exception;
	
	void relativeNestedFile() throws Exception;
	
	void relativePath() throws Exception;
	
	void relativePseudoFragment() throws Exception;
	
	void startRelativePath() throws Exception;
	
}
