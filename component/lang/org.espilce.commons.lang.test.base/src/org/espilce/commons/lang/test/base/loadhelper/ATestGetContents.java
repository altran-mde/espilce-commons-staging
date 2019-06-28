package org.espilce.commons.lang.test.base.loadhelper;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.junit.Ignore;
import org.junit.Test;

public abstract class ATestGetContents {
	@Test
	public void existingFile() throws Exception {
		checkGetContents(someDir() + separator() + dir() + separator() + file());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nonExistingFile() throws Exception {
		checkGetContents(someDir() + separator() + dir() + separator() + noFile());
	}
	
	@Test
	public void rootFile() throws Exception {
		checkGetContents(file());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void rootNonFile() throws Exception {
		checkGetContents(noFile());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void rootFileStartSlash() throws Exception {
		checkGetContents(separator() + file());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void rootNonFileStartSlash() throws Exception {
		checkGetContents(separator() + noFile());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void existingFileStartSlash() throws Exception {
		checkGetContents(separator() + someDir() + separator() + dir() + separator() + file());
	}
	
	@Test(expected = FileNotFoundException.class)
	@Ignore("inconsistent behavior")
	public void existingDir() throws Exception {
		checkGetContents(someDir() + separator() + dir());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nonExistingDir() throws Exception {
		checkGetContents(someDir() + separator() + noDir());
	}
	
	@Test(expected = FileNotFoundException.class)
	@Ignore("inconsistent behavior")
	public void existingDirEndSlash() throws Exception {
		checkGetContents(someDir() + separator() + dir() + separator());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nonExistingDirEndSlash() throws Exception {
		checkGetContents(someDir() + separator() + noDir() + separator());
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Ignore("inconsistent behavior")
	public void existingDirStartSlash() throws Exception {
		checkGetContents(separator() + someDir() + separator() + dir());
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Ignore("inconsistent behavior")
	public void existingDirStartEndSlash() throws Exception {
		checkGetContents(separator() + someDir() + separator() + dir() + separator());
	}
	
	protected void checkGetContents(final String relativePath) throws Exception {
		final InputStream contents = createLoadHelper().getContents(getClass(), relativePath);
		try {
			assertContents(relativePath, IOUtils.toString(contents));
		} finally {
			IOUtils.closeQuietly(contents);
		}
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
	
	protected abstract void assertContents(String relativePath, String contents);
}
