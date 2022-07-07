/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.loadhelper.filesystem;

import java.io.File;

import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestFindMatchingResources;
import org.junit.jupiter.api.BeforeAll;

public class TestFindMatchingResources extends ATestFindMatchingResources {
	@BeforeAll
	public static void workaroundEmptyDirsInGit() throws Exception {
		new File(emptyDir()).mkdirs();
		new File(new File(manyNestedFilesDir()), emptyDir()).mkdirs();
		new File(new File(singleDirDir()), emptyDir()).mkdirs();
	}
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new FilesystemClassloaderLoadHelper();
	}
	
}
