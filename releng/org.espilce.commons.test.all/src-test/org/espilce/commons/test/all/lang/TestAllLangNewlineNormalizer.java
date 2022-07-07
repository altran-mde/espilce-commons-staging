package org.espilce.commons.test.all.lang;

import org.espilce.commons.text.test.newlinenormalizer.TestMultiString;
import org.espilce.commons.text.test.newlinenormalizer.TestNewlineNormalizer;
import org.espilce.commons.text.test.newlinenormalizer.TestSingleString;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		TestMultiString.class,
		TestNewlineNormalizer.class,
		TestSingleString.class,
})
public class TestAllLangNewlineNormalizer {
	
}
