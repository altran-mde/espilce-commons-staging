/*******************************************************************************
 * Copyright (C) 2020 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.emf.registry;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class EspilceCommonsEmfRegistryBundleActivator implements BundleActivator {
	/**
	 * Symbolic name (a.k.a. plugin name) of the bundle.
	 * 
	 * @since 0.0
	 */
	public static final String SYMBOLIC_NAME = "org.espilce.commons.emf.registry";

	private static BundleContext bundleContext = null;
	
	@Override
	public void start(BundleContext context) throws Exception {
		bundleContext = context;
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		bundleContext = null;
	}
	
	public static BundleContext getBundleContext() {
		return bundleContext;
	}
	
	public static ILog getLog() {
		return bundleContext == null ? null : Platform.getLog(bundleContext.getBundle());
	}
}
