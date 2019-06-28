package org.espilce.commons.lang.fifth.test.loadhelper.mixed;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestToLocalmostUrl;

public class TestToLocalmostUrl_both extends ATestToLocalmostUrl {
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new FilesystemClassloaderLoadHelper();
	}
	
	@Override
	protected void assertUrl(final String relativePath, final URL localmostUrl) {
		assertEquals("file", localmostUrl.getProtocol());
	}
	
}