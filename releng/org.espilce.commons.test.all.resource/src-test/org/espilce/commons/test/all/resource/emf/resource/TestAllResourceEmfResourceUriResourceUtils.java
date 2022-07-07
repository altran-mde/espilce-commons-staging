package org.espilce.commons.test.all.resource.emf.resource;

import org.espilce.commons.emf.resource.uriresourceutils.TestToIResourceNegative;
import org.espilce.commons.emf.resource.uriresourceutils.TestToIResourcePositive;
import org.espilce.commons.emf.resource.uriresourceutils.TestToIResourcePositiveSimple;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestToIResourceNegative.class,
	TestToIResourcePositive.class,
	TestToIResourcePositiveSimple.class,
})
public class TestAllResourceEmfResourceUriResourceUtils {
	
}
