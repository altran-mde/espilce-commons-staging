package org.espilce.commons.lang.test.base.loadhelper;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public abstract class ATestGetContents {
	@Test
	public void existingFile() throws Exception {
		checkGetContents(someDir() + separator() + dir() + separator() + file());
	}
	
	@Test
	public void nonExistingFile() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkGetContents(someDir() + separator() + dir() + separator() + noFile())
		);
	}
	
	@Test
	public void rootFile() throws Exception {
		checkGetContents(file());
	}
	
	@Test
	public void rootNonFile() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkGetContents(noFile())
		);
	}
	
	@Test
	public void rootFileStartSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkGetContents(separator() + file())
		);
	}
	
	@Test
	public void rootNonFileStartSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkGetContents(separator() + noFile())
		);
	}
	
	@Test
	public void existingFileStartSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkGetContents(separator() + someDir() + separator() + dir() + separator() + file())
		);
	}
	
	@Test
	@Disabled("inconsistent behavior")
	public void existingDir() throws Exception {
		assertThrows(
				FileNotFoundException.class,
				() -> checkGetContents(someDir() + separator() + dir())
		);
	}
	
	@Test
	public void nonExistingDir() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkGetContents(someDir() + separator() + noDir())
		);
	}
	
	@Test
	@Disabled("inconsistent behavior")
	public void existingDirEndSlash() throws Exception {
		assertThrows(
				FileNotFoundException.class,
				() -> checkGetContents(someDir() + separator() + dir() + separator())
		);
	}
	
	@Test
	public void nonExistingDirEndSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkGetContents(someDir() + separator() + noDir() + separator())
		);
	}
	
	@Test
	@Disabled("inconsistent behavior")
	public void existingDirStartSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkGetContents(separator() + someDir() + separator() + dir())
		);
	}
	
	@Test
	@Disabled("inconsistent behavior")
	public void existingDirStartEndSlash() throws Exception {
		assertThrows(
				IllegalArgumentException.class,
				() -> checkGetContents(separator() + someDir() + separator() + dir() + separator())
		);
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
