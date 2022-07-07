package org.espilce.commons.lang.dependency.bundle.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.junit.jupiter.api.Test;

public class TestLangDependency {
	@Test
	public void conversionUtils() throws Exception {
		try {
			assertNotNull(ConversionUtils.asJavaPath(URI.create("/")));
		} catch (final UnconvertibleException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void filesystemClassloaderLoadHelper() throws Exception {
		assertNotNull(new FilesystemClassloaderLoadHelper().findMatchingResources(this.getClass(), "."));
	}
}
