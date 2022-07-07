package org.espilce.commons.test.all.lang;

import org.espilce.commons.text.test.stringutils2.TestGetCommonSuffix;
import org.espilce.commons.text.test.stringutils2.TestNormalizeNewlineMultiString;
import org.espilce.commons.text.test.stringutils2.TestNormalizeNewlineSingleString;
import org.espilce.commons.text.test.stringutils2.TestRemoveCommonPrefix;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		TestGetCommonSuffix.class,
		TestNormalizeNewlineMultiString.class,
		TestNormalizeNewlineSingleString.class,
		TestRemoveCommonPrefix.class,
})
public class TestAllLangStringUtils2 {
	
}
