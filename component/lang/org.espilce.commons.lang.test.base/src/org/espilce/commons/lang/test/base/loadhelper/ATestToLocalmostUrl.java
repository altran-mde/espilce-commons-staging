package org.espilce.commons.lang.test.base.loadhelper;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URL;

import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.junit.jupiter.api.Test;

public abstract class ATestToLocalmostUrl {
	@Test
	public void existingFile() throws Exception {
		checkToLocalmostUrl(someDir() + separator() + dir() + separator() + file());
	}
	
	@Test
	public void nonExistingFile() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkToLocalmostUrl(someDir() + separator() + dir() + separator() + noFile())
		);
	}
	
	@Test
	public void rootFile() throws Exception {
		checkToLocalmostUrl(file());
	}
	
	@Test
	public void rootNonFile() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkToLocalmostUrl(noFile())
		);
	}
	
	// TODO
	@Test
	public void rootFileStartSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkToLocalmostUrl(separator() + file())
		);
	}
	
	@Test
	public void rootNonFileStartSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkToLocalmostUrl(separator() + noFile())
		);
		
	}
	
	@Test
	public void existingFileStartSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkToLocalmostUrl(separator() + someDir() + separator() + dir() + separator() + file())
		);
	}
	
	@Test
	public void existingDir() throws Exception {
		checkToLocalmostUrl(someDir() + separator() + dir());
	}
	
	@Test
	public void nonExistingDir() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkToLocalmostUrl(someDir() + separator() + noDir())
		);
	}
	
	@Test
	public void existingDirEndSlash() throws Exception {
		checkToLocalmostUrl(someDir() + separator() + dir() + separator());
	}
	
	@Test
	public void nonExistingDirEndSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkToLocalmostUrl(someDir() + separator() + noDir() + separator())
		);
	}
	
	@Test
	public void existingDirStartSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkToLocalmostUrl(separator() + someDir() + separator() + dir())
		);
	}
	
	@Test
	public void existingDirStartEndSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkToLocalmostUrl(separator() + someDir() + separator() + dir() + separator())
		);
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
