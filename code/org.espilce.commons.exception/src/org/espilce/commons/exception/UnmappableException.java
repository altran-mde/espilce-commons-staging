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

	public UnmappableException(final @Nullable Object source, final @NonNull Class<?> sourceType,
			final @NonNull Class<?> targetType) {
		super(compileMessage(source, sourceType, targetType));

		this.source = source;
		this.sourceType = sourceType;
		this.targetType = targetType;
	}

	public UnmappableException(final @Nullable Object source, final @NonNull Class<?> sourceType,
			final @NonNull Class<?> targetType, final @Nullable Throwable cause) {
		super(compileMessage(source, sourceType, targetType), cause);

		this.source = source;
		this.sourceType = sourceType;
		this.targetType = targetType;
	}

	private static @NonNull String compileMessage(@Nullable Object source, @NonNull Class<?> sourceType,
			@NonNull Class<?> targetType) {
		return "Cannot find [" + targetType + "] for " + sourceType;
	}

	public @Nullable Object getSource() {
		return source;
	}

	public @NonNull Class<?> getSourceType() {
		return sourceType;
	}

	public @NonNull Class<?> getTargetType() {
		return targetType;
	}
}
