package org.espilce.commons.resource.second.test.loadhelper.plugin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestToLocalmostUrl;
import org.espilce.commons.resource.loadhelper.WorkspacePluginLoadHelper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestToLocalmostUrl extends ATestToLocalmostUrl {
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
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	@Disabled
	public void existingDirStartSlash() throws Exception {
		super.existingDirStartSlash();
	}
	
	// @Test(expected = IllegalArgumentException.class) TODO
	@Override
	@Test
	@Disabled
	public void existingDirStartEndSlash() throws Exception {
		super.existingDirStartEndSlash();
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
