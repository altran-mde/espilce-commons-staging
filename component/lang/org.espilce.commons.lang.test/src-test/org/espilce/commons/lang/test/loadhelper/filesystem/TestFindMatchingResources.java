package org.espilce.commons.lang.test.loadhelper.filesystem;

import java.io.File;

import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestFindMatchingResources;
import org.junit.BeforeClass;

public class TestFindMatchingResources extends ATestFindMatchingResources {
	@BeforeClass
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
