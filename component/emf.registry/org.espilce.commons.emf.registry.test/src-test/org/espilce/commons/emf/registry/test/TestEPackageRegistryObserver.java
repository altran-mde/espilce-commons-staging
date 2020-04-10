/*******************************************************************************
 * Copyright (C) 2020 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.registry.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.espilce.commons.emf.registry.EPackageRegistryObserver;
import org.junit.jupiter.api.Test;

public class TestEPackageRegistryObserver implements EPackageRegistryObserver {
	private static final Logger LOGGER = Logger.getLogger(TestEPackageRegistryObserver.class.getName());
	private static List<String> registeredPackages = new LinkedList<>();
	private static List<String> unregisteredPackages = new LinkedList<>();

	@Override
	public void ePackageRegistered(String nsURI, EPackage ePackage) {
		registeredPackages.add(nsURI);
	}

	@Override
	public void ePackageUnregistered(String nsURI, EPackage ePackage) {
		unregisteredPackages.add(nsURI);
	}

	@Test
	public void testEPackageRegistration() throws Exception {
		if (Platform.isRunning()) {
			assertTrue(registeredPackages.contains(EcorePackage.eINSTANCE.getNsURI()));
		} else {
			// TODO: How to test extension points without platform?
			LOGGER.warning("testEPackageRegistration disabled: Eclipse platform not running");
		}
	}

	@Test
	public void testEPackageUnregistration() throws Exception {
		if (Platform.isRunning()) {
			unregisteredPackages.clear();
			EPackage.Registry.INSTANCE.remove(EcorePackage.eINSTANCE.getNsURI());
			assertTrue(unregisteredPackages.contains(EcorePackage.eNS_URI));
		} else {
			// TODO: How to test extension points without platform?
			LOGGER.warning("testEPackageUnregistration disabled: Eclipse platform not running");
		}
	}
}
