package org.espilce.commons.lang.third.test.loadhelper.mixed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestGetContents;

public class TestGetContents_both extends ATestGetContents {
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new FilesystemClassloaderLoadHelper();
	}
	
	@Override
	protected void assertContents(final String relativePath, final String contents) {
		if (relativePath.contains(dir())) {
			assertEquals(contents, "file.txt in filesystem", relativePath);
		} else {
			assertEquals(contents, "file.txt in filesystem root", relativePath);
		}
	}
	
}
