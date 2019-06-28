package org.espilce.commons.lang.third.test.loadhelper.mixed;

import static org.junit.Assert.assertTrue;

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
		final String str = localmostUrl.toString();
		assertTrue(str, str.contains("/target/") ^ str.contains("/classpathBase/"));
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
