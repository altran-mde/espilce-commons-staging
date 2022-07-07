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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.UriUtils;
import org.espilce.commons.emf.resource.UriResourceUtils;
import org.espilce.commons.emf.testsupport.resource.ATestWorkspace;
import org.junit.jupiter.api.Test;
import org.osgi.framework.FrameworkUtil;

/**
 * Test cases for invalid uses of {@link UriUtils#toIResource(URI)}.
 * 
 */
public class TestToIResourceNegative extends ATestWorkspace {
	
	@Test
	public void uriNull() throws Exception {
		assertNull(UriResourceUtils.toIResource(null));
	}
	
	@Test
	public void uriPlugin() throws Exception {
		final Class<? extends TestToIResourceNegative> self = this.getClass();
		final String pathName = FrameworkUtil.getBundle(self).getSymbolicName() + "/"
				+ self.getPackage().getName().replace('.', '/') + "/" + self.getSimpleName() + ".class";
		final URI uri = URI.createPlatformPluginURI(pathName, true);
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertNull(iResource);
	}
	
	@Test
	public void uriOther() throws Exception {
		final URI uri = URI.createURI("https://example.com/MyFile.ext");
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertNull(iResource);
	}
	
	@Test
	public void uriBroken() throws Exception {
		final URI uri = URI.createURI("fasfasdf");
		final IResource iResource = UriResourceUtils.toIResource(uri);
		
		assertNull(iResource);
	}
	
	@Test
	public void uriPlatformResourceBroken() throws Exception {
		final URI uri = URI.createURI("platform:/resource/...////");
		assertThrows(IllegalArgumentException.class, () -> UriResourceUtils.toIResource(uri));
	}
	
}
