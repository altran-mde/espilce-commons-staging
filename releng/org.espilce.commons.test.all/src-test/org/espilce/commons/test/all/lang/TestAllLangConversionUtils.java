package org.espilce.commons.test.all.lang;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
		org.espilce.commons.lang.test.conversionutils.javafile.javapath.ATestJavaFile2JavaPath.class,
		org.espilce.commons.lang.test.conversionutils.javafile.javauri.TestJavaFile2JavaUri_absolute.class
})
public class TestAllLangConversionUtils {
	// FIXME: Does not work atm.
}
