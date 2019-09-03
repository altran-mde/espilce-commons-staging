/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.conversionutils.javafile.javapath;

import java.io.File;
import java.nio.file.Path;

import org.espilce.commons.lang.test.conversionutils.TestABase;

public abstract class TestJavaFile2JavaPath extends TestABase {
	@Override
	protected Class<?> getSourceType() { return File.class; }
	
	@Override
	protected Class<?> getTargetType() { return Path.class; }
	
	protected abstract Path invoke(final File input);
}
