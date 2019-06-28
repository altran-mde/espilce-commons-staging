package org.espilce.commons.lang.test.base.loadhelper;

import java.net.URL;

import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.junit.Test;

public abstract class ATestToLocalmostUrl {
	@Test
	public void existingFile() throws Exception {
		checkToLocalmostUrl(someDir() + separator() + dir() + separator() + file());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nonExistingFile() throws Exception {
		checkToLocalmostUrl(someDir() + separator() + dir() + separator() + noFile());
	}
	
	@Test
	public void rootFile() throws Exception {
		checkToLocalmostUrl(file());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void rootNonFile() throws Exception {
		checkToLocalmostUrl(noFile());
	}
	
	// @Test TODO
	@Test(expected = IllegalArgumentException.class)
	public void rootFileStartSlash() throws Exception {
		checkToLocalmostUrl(separator() + file());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void rootNonFileStartSlash() throws Exception {
		checkToLocalmostUrl(separator() + noFile());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void existingFileStartSlash() throws Exception {
		checkToLocalmostUrl(separator() + someDir() + separator() + dir() + separator() + file());
	}
	
	@Test
	public void existingDir() throws Exception {
		checkToLocalmostUrl(someDir() + separator() + dir());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nonExistingDir() throws Exception {
		checkToLocalmostUrl(someDir() + separator() + noDir());
	}
	
	@Test
	public void existingDirEndSlash() throws Exception {
		checkToLocalmostUrl(someDir() + separator() + dir() + separator());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nonExistingDirEndSlash() throws Exception {
		checkToLocalmostUrl(someDir() + separator() + noDir() + separator());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void existingDirStartSlash() throws Exception {
		checkToLocalmostUrl(separator() + someDir() + separator() + dir());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void existingDirStartEndSlash() throws Exception {
		checkToLocalmostUrl(separator() + someDir() + separator() + dir() + separator());
	}
	
	protected void checkToLocalmostUrl(final String relativePath) throws Exception {
		assertUrl(relativePath, createLoadHelper().toLocalmostUrl(getClass(), relativePath));
	}
	
	protected String someDir() {
		return "some";
	}
	
	protected String separator() {
		return "/";
	}
	
	protected String dir() {
		return "dir";
	}
	
	protected String file() {
		return "file.txt";
	}
	
	protected String noFile() {
		return "noFile.txt";
	}
	
	protected String noDir() {
		return "nodir";
	}
	
	protected abstract ILoadHelper createLoadHelper();
	
	protected abstract void assertUrl(String relativePath, URL localmostUrl);
}
