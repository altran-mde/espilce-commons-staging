package org.espilce.commons.lang.fifth.test.loadhelper.mixed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestToLocalmostUrl;

public class TestToLocalmostUrl_classpath extends ATestToLocalmostUrl {
	
	@Override
	protected ILoadHelper createLoadHelper() {
		return new FilesystemClassloaderLoadHelper();
	}
	
	@Override
	protected void assertUrl(final String relativePath, final URL localmostUrl) {
		assertEquals("jar", localmostUrl.getProtocol());
	}
	
	@Override
	protected String file() {
		return "fileClasspath.txt";
	}
	
	@Override
	protected String dir() {
		return "dirClasspath";
	}
}
