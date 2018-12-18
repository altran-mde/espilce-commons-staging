package org.espilce.commons.resource.second.test.loadhelper.plugin;

import static org.junit.Assert.assertEquals;

import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestGetContents;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.junit.Test;

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
			assertEquals(relativePath, "file.txt in plugin", contents);
		} else {
			assertEquals(relativePath, "file.txt in plugin root", contents);
		}
	}

}
