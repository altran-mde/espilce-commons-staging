package org.espilce.commons.emf.dependency.pckg.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.espilce.commons.emf.UriUtils;
import org.espilce.commons.exception.UnconvertibleException;
import org.junit.jupiter.api.Test;

public class TestEmfDependency {
	@Test
	public void uriUtils() throws Exception {
		try {
			assertNotNull(UriUtils.asEmfUri(new File("/")));
		} catch (final UnconvertibleException e) {
			assertNotNull(e);
		}
	}
	
}
