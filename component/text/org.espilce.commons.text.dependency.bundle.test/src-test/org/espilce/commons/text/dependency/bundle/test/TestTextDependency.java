package org.espilce.commons.text.dependency.bundle.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.espilce.commons.text.NewlineNormalizer;
import org.espilce.commons.text.StringUtils2;
import org.junit.jupiter.api.Test;

public class TestTextDependency {
	@Test
	public void newlineNormalizer() throws Exception {
		assertNotNull(new NewlineNormalizer("hello").normalize("this\nis"));
	}
	
	@Test
	public void stringUtils2() throws Exception {
		assertNotNull(StringUtils2.getCommonSuffix("a", "aa"));
	}
}
