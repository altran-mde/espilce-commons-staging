package org.espilce.commons.test.all.emf;

import org.espilce.commons.emf.test.uriutils.TestEmfUri2JavaUri_As;
import org.espilce.commons.emf.test.uriutils.TestEmfUri2JavaUri_To;
import org.espilce.commons.emf.test.uriutils.TestEmfUri2JavaUrl_As;
import org.espilce.commons.emf.test.uriutils.TestEmfUri2JavaUrl_To;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		TestEmfUri2JavaUri_As.class,
		TestEmfUri2JavaUri_To.class,
		TestEmfUri2JavaUrl_As.class,
		TestEmfUri2JavaUrl_To.class,
})
public class TestAllEmfUriUtils {
	
}
