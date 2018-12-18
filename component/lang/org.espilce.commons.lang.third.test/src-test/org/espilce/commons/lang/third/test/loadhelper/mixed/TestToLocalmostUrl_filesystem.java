package org.espilce.commons.lang.third.test.loadhelper.mixed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.net.URL;

import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.espilce.commons.lang.test.base.loadhelper.ATestToLocalmostUrl;

public class TestToLocalmostUrl_filesystem extends ATestToLocalmostUrl {

	@Override
	protected ILoadHelper createLoadHelper() {
		return new FilesystemClassloaderLoadHelper();
	}

	@Override
	protected void assertUrl(final String relativePath, final URL localmostUrl) {
		assertEquals("file", localmostUrl.getProtocol());
		final String str = localmostUrl.toString();
		assertFalse(str, str.contains("/target/") ^ str.contains("/classpathBase/"));
	}

	@Override
	protected String file() {
		return "fileFilesystem.txt";
	}
	
	@Override
	protected String dir() {
		return "dirFilesystem";
	}
}
