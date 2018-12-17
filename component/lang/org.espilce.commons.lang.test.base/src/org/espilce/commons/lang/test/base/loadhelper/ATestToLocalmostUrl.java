package org.espilce.commons.lang.test.base.loadhelper;

import java.net.URL;

import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.junit.Test;

public abstract class ATestToLocalmostUrl {
	@Test
	public void existingFile() throws Exception {
		checkToLocalmostUrl("some/dir/file.txt");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nonExistingFile() throws Exception {
		checkToLocalmostUrl("some/dir/noFile.txt");
	}

	@Test
	public void rootFile() throws Exception {
		checkToLocalmostUrl("file.txt");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void rootNonFile() throws Exception {
		checkToLocalmostUrl("noFile.txt");
	}
	
	// @Test TODO
	@Test(expected = IllegalArgumentException.class)
	public void rootFileStartSlash() throws Exception {
		checkToLocalmostUrl("/file.txt");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void rootNonFileStartSlash() throws Exception {
		checkToLocalmostUrl("/noFile.txt");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void existingFileStartSlash() throws Exception {
		checkToLocalmostUrl("/some/dir/file.txt");
	}
	
	@Test
	public void existingDir() throws Exception {
		checkToLocalmostUrl("some/dir");
	}

	@Test(expected = IllegalArgumentException.class)
	public void nonExistingDir() throws Exception {
		checkToLocalmostUrl("some/nodir");
	}

	@Test
	public void existingDirEndSlash() throws Exception {
		checkToLocalmostUrl("some/dir/");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nonExistingDirEndSlash() throws Exception {
		checkToLocalmostUrl("some/nodir/");
	}

	@Test(expected = IllegalArgumentException.class)
	public void existingDirStartSlash() throws Exception {
		checkToLocalmostUrl("/some/dir");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void existingDirStartEndSlash() throws Exception {
		checkToLocalmostUrl("/some/dir/");
	}

	protected void checkToLocalmostUrl(final String relativePath) throws Exception {
		assertUrl(relativePath, createLoadHelper().toLocalmostUrl(getClass(), relativePath));
	}
	
	protected abstract ILoadHelper createLoadHelper();
	
	protected abstract void assertUrl(String relativePath, URL localmostUrl);
}
