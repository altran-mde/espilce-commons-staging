package org.espilce.commons.lang.second.test.loadhelper.classpath;

import java.io.File;

import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestFindMatchingResources;
import org.junit.BeforeClass;
import org.junit.Ignore;

@Ignore
public class TestFindMatchingResources extends ATestFindMatchingResources {
	@BeforeClass
	public static void workaroundEmptyDirsInGit() throws Exception {
		new File(new File("classpathBase"), emptyDir()).mkdirs();
		new File(new File(new File("classpathBase"), manyNestedFilesDir()), emptyDir()).mkdirs();
		new File(new File(new File("classpathBase"), singleDirDir()), emptyDir()).mkdirs();
	}

	@Override
	protected ILoadHelper createLoadHelper() {
		return new FilesystemClassloaderLoadHelper();
	}

}
