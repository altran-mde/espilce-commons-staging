package org.espilce.commons.test.all.lang;

import org.espilce.commons.lang.test.conversionutils.TestJavaFile2JavaUri_As;
import org.espilce.commons.lang.test.conversionutils.TestJavaFile2JavaUri_To;
import org.espilce.commons.lang.test.conversionutils.TestJavaFile2JavaUrl_As;
import org.espilce.commons.lang.test.conversionutils.TestJavaFile2JavaUrl_To;
import org.espilce.commons.lang.test.conversionutils.TestJavaPath2JavaUri_As;
import org.espilce.commons.lang.test.conversionutils.TestJavaPath2JavaUri_To;
import org.espilce.commons.lang.test.conversionutils.TestJavaPath2JavaUrl_As;
import org.espilce.commons.lang.test.conversionutils.TestJavaPath2JavaUrl_To;
import org.espilce.commons.lang.test.conversionutils.TestJavaUri2JavaFile_As;
import org.espilce.commons.lang.test.conversionutils.TestJavaUri2JavaPath_As;
import org.espilce.commons.lang.test.conversionutils.TestJavaUri2JavaUrl_As;
import org.espilce.commons.lang.test.conversionutils.TestJavaUri2JavaUrl_To;
import org.espilce.commons.lang.test.conversionutils.TestJavaUrl2JavaFile_As;
import org.espilce.commons.lang.test.conversionutils.TestJavaUrl2JavaFile_To;
import org.espilce.commons.lang.test.conversionutils.TestJavaUrl2JavaUri_As;
import org.espilce.commons.lang.test.conversionutils.TestJavaUrl2JavaUri_To;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestJavaFile2JavaUri_As.class,
	TestJavaFile2JavaUri_To.class,
	TestJavaFile2JavaUrl_As.class,
	TestJavaFile2JavaUrl_To.class,
	TestJavaPath2JavaUri_As.class,
	TestJavaPath2JavaUri_To.class,
	TestJavaPath2JavaUrl_As.class,
	TestJavaPath2JavaUrl_To.class,
	TestJavaUri2JavaFile_As.class,
	TestJavaUri2JavaPath_As.class,
	TestJavaUri2JavaUrl_As.class,
	TestJavaUri2JavaUrl_To.class,
	TestJavaUrl2JavaFile_As.class,
	TestJavaUrl2JavaFile_To.class,
	TestJavaUrl2JavaUri_As.class,
	TestJavaUrl2JavaUri_To.class,
})
public class TestAllLangConversionUtils {
	
}
