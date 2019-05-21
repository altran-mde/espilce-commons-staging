package org.espilce.commons.test.all.ui;

import org.espilce.commons.test.all.TestAll;
import org.espilce.commons.test.all.resource.TestAllResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		TestAll.class,
		TestAllResource.class,
})
public class TestAllUi {
	
}
