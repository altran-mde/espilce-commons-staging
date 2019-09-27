package org.espilce.commons.resource.third.test.loadhelper.mixed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestGetContents;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestGetContents_plugin extends ATestGetContents {
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new WorkspacePluginLoadHelper();
	}
	
	@Override
	protected void assertContents(final String relativePath, final String contents) {
		if (relativePath.contains(dir())) {
			assertEquals(contents, "filePlugin.txt in plugin", relativePath);
		} else {
			assertEquals(contents, "filePlugin.txt in plugin root", relativePath);
		}
	}
	
	@Override
	@Test
	@Disabled
	public void rootFileStartSlash() throws Exception {
		super.rootFileStartSlash();
	}
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	@Disabled
	public void existingFileStartSlash() throws Exception {
		super.existingFileStartSlash();
	}
	
	@Override
	protected String file() {
		return "filePlugin.txt";
	}
	
	@Override
	protected String dir() {
		return "dirPlugin";
	}
}
