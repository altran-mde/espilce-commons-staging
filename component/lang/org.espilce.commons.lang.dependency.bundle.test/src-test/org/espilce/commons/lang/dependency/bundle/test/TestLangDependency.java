package org.espilce.commons.lang.dependency.bundle.test;

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import org.espilce.commons.exception.UnconvertibleException;
import org.espilce.commons.lang.ConversionUtils;
import org.espilce.commons.lang.NewlineNormalizer;
import org.espilce.commons.lang.StringUtils2;
import org.espilce.commons.lang.loadhelper.FilesystemClassloaderLoadHelper;
import org.junit.Test;

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
	public void newlineNormalizer() throws Exception {
		assertNotNull(new NewlineNormalizer("hello").normalize("this\nis"));
	}
	
	@Test
	public void stringUtils2() throws Exception {
		assertNotNull(StringUtils2.getCommonSuffix("a", "aa"));
	}
	
	@Test
	public void filesystemClassloaderLoadHelper() throws Exception {
		assertNotNull(new FilesystemClassloaderLoadHelper().findMatchingResources(this.getClass(), "."));
	}
}
