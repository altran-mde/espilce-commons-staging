package org.espilce.commons.resource.second.test.loadhelper.plugin;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestGetContents;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.junit.jupiter.api.Test;

public class TestGetContents extends ATestGetContents {
	@Override
	@Test
	public void rootFileStartSlash() throws Exception {
		super.rootFileStartSlash();
	}
	
	@Override
	@Test
	public void existingFileStartSlash() throws Exception {
		super.existingFileStartSlash();
	}
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new WorkspacePluginLoadHelper();
	}
	
	@Override
	protected void assertContents(final String relativePath, final String contents) {
		if (relativePath.contains(dir())) {
			assertEquals(contents, "file.txt in plugin", relativePath);
		} else {
			assertEquals(contents, "file.txt in plugin root", relativePath);
		}
	}
	
}
