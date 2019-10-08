package org.espilce.commons.lang.fourth.test.loadhelper.classpath;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestToLocalmostUrl;

public class TestToLocalmostUrl extends ATestToLocalmostUrl {
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new FilesystemClassloaderLoadHelper();
	}
	
	@Override
	protected void assertUrl(final String relativePath, final URL localmostUrl) {
		assertEquals("jar", localmostUrl.getProtocol());
	}
	
}
