package org.espilce.commons.emf.registry.test;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EPackage;
import org.espilce.commons.emf.registry.EPackageRegistryObserver;
import org.espilce.commons.emf.registry.EspilceCommonsEmfRegistryBundleActivator;

public class LoggingEPackageRegistryObserver implements EPackageRegistryObserver {
	@Override
	public void ePackageRegistered(String nsURI, EPackage ePackage) {
		EspilceCommonsEmfRegistryBundleActivator.getLog().log(
				new Status(IStatus.INFO, "org.espilce.commons.emf.registry.test", "Registered EPackage: " + nsURI));
	}

	@Override
	public void ePackageUnregistered(String nsURI, EPackage ePackage) {
		EspilceCommonsEmfRegistryBundleActivator.getLog().log(
				new Status(IStatus.INFO, "org.espilce.commons.emf.registry.test", "Unregistered EPackage: " + nsURI));
	}
}
