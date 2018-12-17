package org.espilce.commons.resource.second.test.loadhelper.plugin;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestToLocalmostUrl;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.junit.Test;

public class TestToLocalmostUrl extends ATestToLocalmostUrl {
	@Override
	@Test
	public void rootFileStartSlash() throws Exception {
		checkToLocalmostUrl("/file.txt");
	}

	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	public void existingFileStartSlash() throws Exception {
		checkToLocalmostUrl("/some/dir/file.txt");
	}

	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	public void existingDirStartSlash() throws Exception {
		checkToLocalmostUrl("/some/dir");
	}
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	public void existingDirStartEndSlash() throws Exception {
		checkToLocalmostUrl("/some/dir/");
	}

	@Override
	protected ILoadHelper createLoadHelper() {
		return new WorkspacePluginLoadHelper();
	}

	@Override
	protected void assertUrl(final String relativePath, final URL localmostUrl) {
		assertEquals("bundleentry", localmostUrl.getProtocol());
	}
}
