/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.generator.api.outputconfig;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public interface ISourceMapping {
	
	public @NonNull String getSourceFolder();
	
	public @Nullable String getOutputDirectory();
	
	void setOutputDirectory(final @Nullable String outputDirectory);
	
	boolean isIgnore();
	
	void setIgnore(boolean ignore);
	
}