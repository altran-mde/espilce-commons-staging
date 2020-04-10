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

import org.eclipse.emf.ecore.EPackage;

public interface EPackageRegistryObserver {
	void ePackageRegistered(String nsURI, EPackage ePackage);

	void ePackageUnregistered(String nsURI, EPackage ePackage);
}
