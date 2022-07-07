package org.espilce.commons.lang.fifth.test.loadhelper.mixed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestGetContents;

public class TestGetContents_filesystem extends ATestGetContents {
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new FilesystemClassloaderLoadHelper();
	}
	
	@Override
	protected void assertContents(final String relativePath, final String contents) {
		if (relativePath.contains(dir())) {
			assertEquals(contents, "fileFilesystem.txt in filesystem", relativePath);
		} else {
			assertEquals(contents, "fileFilesystem.txt in filesystem root", relativePath);
		}
	}
	
	@Override
	protected String file() {
		return "fileFilesystem.txt";
	}
	
	@Override
	protected String dir() {
		return "dirFilesystem";
	}
}
