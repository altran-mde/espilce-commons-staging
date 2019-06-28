package org.espilce.commons.emf.resource.dependency.pckg.test;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.resource.UriResourceUtils;
import org.espilce.commons.exception.UnconvertibleException;
import org.junit.Test;

public class TestEmfResourceDependency {
	@Test
	public void uriResourceUtils() throws Exception {
		try {
			assertNotNull(UriResourceUtils.asIPath(URI.createFileURI(new File(".").getAbsolutePath())));
		} catch (final UnconvertibleException e) {
			assertNotNull(e);
		}
	}
}
