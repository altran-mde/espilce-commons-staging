/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.resource.uriresourceutils;

import static org.junit.Assert.assertNull;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.UriUtils;
import org.espilce.commons.emf.resource.UriResourceUtils;
import org.espilce.commons.emf.testsupport.resource.ATestWorkspace;
import org.junit.Test;
import org.osgi.framework.FrameworkUtil;

/**
 * Test cases for invalid uses of {@link UriUtils#toIResource(URI)}.
 * 
 */
public class TestToIResourceNegative extends ATestWorkspace {

	@SuppressWarnings("null")
	@Test(expected = NullPointerException.class)
	public void uriNull() throws Exception {
		UriResourceUtils.toIResource(null);
	}

	@Test
	public void uriPlugin() throws Exception {
		Class<? extends TestToIResourceNegative> self = this.getClass();
		String pathName = FrameworkUtil.getBundle(self).getSymbolicName() + "/"
				+ self.getPackage().getName().replace('.', '/') + "/" + self.getSimpleName() + ".class";
		URI uri = URI.createPlatformPluginURI(pathName, true);
		IResource iResource = UriResourceUtils.toIResource(uri);

		assertNull(iResource);
	}

	@Test
	public void uriOther() throws Exception {
		URI uri = URI.createURI("https://example.com/MyFile.ext");
		IResource iResource = UriResourceUtils.toIResource(uri);

		assertNull(iResource);
	}

	@Test
	public void uriBroken() throws Exception {
		URI uri = URI.createURI("fasfasdf");
		IResource iResource = UriResourceUtils.toIResource(uri);

		assertNull(iResource);
	}

	@Test(expected = IllegalArgumentException.class)
	public void uriPlatformResourceBroken() throws Exception {
		URI uri = URI.createURI("platform:/resource/...////");
		UriResourceUtils.toIResource(uri);
	}

}
