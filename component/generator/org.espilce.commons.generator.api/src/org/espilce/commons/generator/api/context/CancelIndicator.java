/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.generator.api.context;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public interface CancelIndicator {
	
	boolean isCanceled();

	public final static CancelIndicator NullImpl = new CancelIndicator() {
		@Override
		public boolean isCanceled() {
			return false;
		}
	};
}
