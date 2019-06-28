/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.exception;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Thrown to indicate that the {@linkplain #getSource() source} of
 * {@linkplain #getSourceType() type} cannot be mapped to the desired
 * {@linkplain #getTargetType() target type}.
 * 
 * @since 0.2
 */
public class UnmappableException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 8792469773257203504L;
	
	private final @Nullable Object source;
	private final @NonNull Class<?> sourceType;
	private final @NonNull Class<?> targetType;
	
	public UnmappableException(
			final @Nullable Object source, final @NonNull Class<?> sourceType,
			final @NonNull Class<?> targetType
	) {
		super(compileMessage(source, sourceType, targetType));
		
		this.source = source;
		this.sourceType = sourceType;
		this.targetType = targetType;
	}
	
	public UnmappableException(
			final @Nullable Object source, final @NonNull Class<?> sourceType,
			final @NonNull Class<?> targetType, final @Nullable Throwable cause
	) {
		super(compileMessage(source, sourceType, targetType), cause);
		
		this.source = source;
		this.sourceType = sourceType;
		this.targetType = targetType;
	}
	
	private static @NonNull String compileMessage(
			@Nullable final Object source, @NonNull final Class<?> sourceType,
			@NonNull final Class<?> targetType
	) {
		return "Cannot find [" + targetType + "] for " + sourceType;
	}
	
	public @Nullable Object getSource() { return this.source; }
	
	public @NonNull Class<?> getSourceType() { return this.sourceType; }
	
	public @NonNull Class<?> getTargetType() { return this.targetType; }
}
