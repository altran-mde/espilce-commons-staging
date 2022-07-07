/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.junit5;

import java.util.stream.Stream;

import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.support.AnnotationConsumer;

/**
 * Creates all combinations of {@link #getMethods() methods} and different
 * {@link #mutateBackslash separators}, if set <i>and</i> we're running on
 * Windows.
 */
public class ConversionArgumentsProvider extends AConversionArgumentsProvider
		implements AnnotationConsumer<TestConversion>
{
	private String value;
	private boolean mutateBackslash;
	
	@Override
	public void accept(final TestConversion t) {
		final String tmp = t.value();
		
		if (tmp != TestConversion.DEFAULT) {
			this.value = tmp;
		} else {
			this.value = null;
		}
		
		this.mutateBackslash = t.backslash();
	}
	
	@Override
	public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
		retrieveClassConfig(context);
		
		final Stream<String> base;
		if (this.mutateBackslash && OS.WINDOWS.isCurrentOs()) {
			base = Stream.of(this.value, this.value.replace(SEPARATOR, ALT_SEPARATOR));
		} else {
			base = Stream.of(this.value);
		}
		
		return base
				.flatMap(v -> getMethods().map(f -> Arguments.of(f, v)));
	}
}
