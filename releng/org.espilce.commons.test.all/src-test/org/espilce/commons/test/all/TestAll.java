package org.espilce.commons.test.all;

import org.espilce.commons.test.all.emf.TestAllEmf;
import org.espilce.commons.test.all.emf.testsupport.TestAllEmfTestsupport;
import org.espilce.commons.test.all.lang.TestAllLang;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		TestAllEmf.class,
		TestAllEmfTestsupport.class,
		TestAllLang.class,
})
public class TestAll {
	
}
