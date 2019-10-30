package org.espilce.commons.assertion.junit5.dependency.pckg.test;

import org.espilce.commons.assertion.Assertion;
import org.junit.jupiter.api.Test;

public class TestAssertionJunit5Dependency {
	@Test
	public void testAssert() throws Exception {
		Assertion.assertTrue(true);
	}
}
