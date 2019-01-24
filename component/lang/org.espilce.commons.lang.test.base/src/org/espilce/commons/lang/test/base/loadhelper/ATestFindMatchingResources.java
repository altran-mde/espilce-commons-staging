package org.espilce.commons.lang.test.base.loadhelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.List;

import org.espilce.commons.lang.loadhelper.ILoadHelper;
import org.junit.Test;

public abstract class ATestFindMatchingResources {
	
	@Test
	public void emptyExistingDir() throws Exception {
		final List<URL> matchingResources = findMatchingResources(emptyDir());
		assertSize(matchingResources, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nonExistingDir() throws Exception {
		findMatchingResources(noDir());
	}
	
	@Test
	public void existingFile() throws Exception {
		final List<URL> matchingResources = findMatchingResources(
				someDir() + separator() + dir() + separator() + file1()
		);
		assertSize(matchingResources, 0);
	}
	
	@Test
	public void oneDirectFile() throws Exception {
		final List<URL> matchingResources = findMatchingResources(singleFileDir());
		assertSize(matchingResources, 1);
		
		assertUrl(matchingResources, singleFileDir(), file1());
	}
	
	@Test
	public void oneNestedFile() throws Exception {
		final List<URL> matchingResources = findMatchingResources(singleFileNestedDir());
		assertSize(matchingResources, 3);
		
		assertUrl(matchingResources, singleFileNestedDir(), dir(), "");
		assertUrl(matchingResources, singleFileNestedDir(), dir(), dir(), "");
		assertUrl(matchingResources, singleFileNestedDir(), dir(), dir(), file1());
	}
	
	@Test
	public void oneDirectDir() throws Exception {
		final List<URL> matchingResources = findMatchingResources(singleDirDir());
		assertSize(matchingResources, 1);
		
		assertUrl(matchingResources, singleDirDir(), emptyDir(), "");
	}
	
	@Test
	public void manyDirectFiles() throws Exception {
		final List<URL> matchingResources = findMatchingResources(manyDirectFilesDir());
		assertNotNull(matchingResources);
		assertEquals(matchingResources.toString(), 3, matchingResources.size());
		
		assertUrl(matchingResources, file1());
		assertUrl(matchingResources, file2());
		assertUrl(matchingResources, file3());
	}
	
	@Test
	public void manyNestedFiles() throws Exception {
		final List<URL> matchingResources = findMatchingResources(manyNestedFilesDir());
		assertNotNull(matchingResources);
		assertEquals(matchingResources.toString(), 17, matchingResources.size());
		
		// 1
		assertUrl(matchingResources, manyNestedFilesDir(), emptyDir(), "");
		// 2
		assertUrl(matchingResources, manyNestedFilesDir(), file3());
		// 3
		assertUrl(matchingResources, manyNestedFilesDir(), manyDirectFilesDir(), "");
		// 4
		assertUrl(matchingResources, manyNestedFilesDir(), manyDirectFilesDir(), file1());
		// 5
		assertUrl(matchingResources, manyNestedFilesDir(), manyDirectFilesDir(), file2());
		// 6
		assertUrl(matchingResources, manyNestedFilesDir(), manyNestedFilesDir(), "");
		// 7
		assertUrl(matchingResources, manyNestedFilesDir(), manyNestedFilesDir(), file1());
		// 8
		assertUrl(matchingResources, manyNestedFilesDir(), manyNestedFilesDir(), file2());
		// 9
		assertUrl(matchingResources, manyNestedFilesDir(), manyNestedFilesDir(), singleFileNestedDir(), "");
		// 10
		assertUrl(matchingResources, manyNestedFilesDir(), manyNestedFilesDir(), singleFileNestedDir(), dir(), "");
		// 11
		assertUrl(matchingResources, manyNestedFilesDir(), manyNestedFilesDir(), singleFileNestedDir(), dir(), file1());
		// 12
		assertUrl(matchingResources, manyNestedFilesDir(), singleFileDir(), "");
		// 13
		assertUrl(matchingResources, manyNestedFilesDir(), singleFileDir(), file1());
		// 14
		assertUrl(matchingResources, manyNestedFilesDir(), someDir(), "");
		// 15
		assertUrl(matchingResources, manyNestedFilesDir(), someDir(), dir(), "");
		// 16
		assertUrl(matchingResources, manyNestedFilesDir(), someDir(), dir(), dir(), "");
		// 17
		assertUrl(matchingResources, manyNestedFilesDir(), someDir(), dir(), dir(), file2());
	}
	
	protected static String manyNestedFilesDir() {
		return "manyNestedFilesDir";
	}
	
	protected static String manyDirectFilesDir() {
		return "manyDirectFilesDir";
	}
	
	protected static String singleFileNestedDir() {
		return "singleFileNestedDir";
	}
	
	protected static String singleFileDir() {
		return "singleFileDir";
	}
	
	protected static String singleDirDir() {
		return "singleDirDir";
	}
	
	protected static String emptyDir() {
		return "emptyDir";
	}
	
	protected static String someDir() {
		return "some";
	}
	
	protected static String separator() {
		return "/";
	}
	
	protected static String dir() {
		return "dir";
	}
	
	protected static String file1() {
		return "file.txt";
	}
	
	protected static String file2() {
		return "file.othertxt";
	}
	
	protected static String file3() {
		return "somefilename.ext";
	}
	
	protected static String noDir() {
		return "nodir";
	}
	
	protected void assertUrl(final List<URL> matchingResources, final String... suffixes) {
		final String suffix = String.join("/", suffixes);
		assertEquals(suffix, 1, matchingResources.stream().filter(u -> u.getFile().endsWith(suffix)).count());
	}
	
	protected void assertSize(final List<URL> matchingResources, final int expected) {
		assertNotNull(matchingResources);
		assertEquals(matchingResources.toString(), expected, matchingResources.size());
	}
	
	protected List<URL> findMatchingResources(final String parentRelativePath) {
		return createLoadHelper().findMatchingResources(getClass(), parentRelativePath);
	}
	
	protected abstract ILoadHelper createLoadHelper();
}
