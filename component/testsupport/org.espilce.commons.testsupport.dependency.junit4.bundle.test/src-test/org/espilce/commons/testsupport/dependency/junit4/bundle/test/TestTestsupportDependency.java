package org.espilce.commons.testsupport.dependency.junit4.bundle.test;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.espilce.commons.testsupport.Assert2;
import org.espilce.commons.testsupport.AssertFile;
import org.espilce.commons.testsupport.AssertInputStream;
import org.junit.Test;

public class TestTestsupportDependency {
	@Test
	public void assert2() throws Exception {
		Assert2.assertEqualsNormalizedNewline("a\naa", "a\raa");
	}
	
	@Test
	public void assertFile() throws Exception {
		AssertFile.assertDoesntExist(new File("NonExistentFile.ext"));
	}
	
	@Test
	public void assertInputStream() throws Exception {
		AssertInputStream.assertContentEquals(new ByteArrayInputStream("aaa".getBytes()),
				new ByteArrayInputStream("aaa".getBytes()));
	}
}
