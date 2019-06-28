package org.espilce.commons.resource.third.test.loadhelper.mixed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.net.URL;

import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestToLocalmostUrl;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.junit.Test;

public class TestToLocalmostUrl_plugin extends ATestToLocalmostUrl {
	@Override
	@Test
	public void rootFileStartSlash() throws Exception {
		super.rootFileStartSlash();
	}
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	public void existingFileStartSlash() throws Exception {
		super.existingFileStartSlash();
	}
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	public void existingDirStartSlash() throws Exception {
		super.existingDirStartSlash();
	}
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	public void existingDirStartEndSlash() throws Exception {
		super.existingDirStartEndSlash();
	}
	
	@Override
	protected String file() {
		return "filePlugin.txt";
	}
	
	@Override
	protected String dir() {
		return "dirPlugin";
	}
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new WorkspacePluginLoadHelper();
	}
	
	@Override
	protected void assertUrl(final String relativePath, final URL localmostUrl) {
		assertEquals("bundleentry", localmostUrl.getProtocol());
		final String str = localmostUrl.toString();
		assertFalse(str, str.contains("/testWorkspace/"));
	}
}
