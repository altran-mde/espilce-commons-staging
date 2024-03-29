package org.espilce.commons.resource.second.test.loadhelper.plugin;

import java.io.File;

import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestFindMatchingResources;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
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
		return new WorkspacePluginLoadHelper();
	}
	
}
