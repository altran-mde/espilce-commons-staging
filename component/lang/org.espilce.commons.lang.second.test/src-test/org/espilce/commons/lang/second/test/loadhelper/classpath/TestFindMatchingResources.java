package org.espilce.commons.lang.second.test.loadhelper.classpath;

import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestFindMatchingResources;
import org.junit.jupiter.api.Disabled;

@Disabled
public class TestFindMatchingResources extends ATestFindMatchingResources {
	@Override
	protected ILoadHelper createLoadHelper() {
		return new FilesystemClassloaderLoadHelper();
	}
	
}
