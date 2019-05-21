package org.espilce.commons.test.all.resource;

import org.espilce.commons.test.all.resource.emf.resource.TestAllResourceEmfResource;
import org.espilce.commons.test.all.resource.resource.TestAllResourceResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		TestAllResourceResource.class,
		TestAllResourceEmfResource.class,
})
public class TestAllResource {
	
}
