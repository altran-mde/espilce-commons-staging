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

public interface TestIJavaUri {
	void absoluteNestedFileNoScheme() throws Exception;
	
	void currentNoScheme() throws Exception;
	
	void currentRelativeNestedFileNoScheme() throws Exception;
	
	void currentSlashNoScheme() throws Exception;
	
	void emptyNoScheme() throws Exception;
	
	void inputBroken() throws Exception;
	
	void noSchema() throws Exception;
	
	void relativeNestedFileNoScheme() throws Exception;
	
	void rootNoScheme() throws Exception;
	
	void rootScheme() throws Exception;
	
	void startRelativePathNoScheme() throws Exception;
}
