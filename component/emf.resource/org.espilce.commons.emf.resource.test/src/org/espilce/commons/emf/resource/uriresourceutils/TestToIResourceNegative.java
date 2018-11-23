package org.espilce.commons.emf.resource.uriresourceutils;

import static org.junit.Assert.assertNull;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.espilce.commons.emf.UriUtils;
import org.espilce.commons.emf.resource.UriResourceUtils;
import org.junit.Test;
import org.osgi.framework.FrameworkUtil;

/**
 * Test cases for invalid uses of {@link UriUtils#toIResource(URI)}.
 * 
 */
public class TestToIResourceNegative extends ATestWorkspace {

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
