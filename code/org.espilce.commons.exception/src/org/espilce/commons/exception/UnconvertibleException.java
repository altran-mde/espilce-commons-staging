package org.espilce.commons.exception;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class UnconvertibleException extends IllegalArgumentException {
	private static final long serialVersionUID = 7300997479447122304L;

	private final @Nullable Object source;
	private final @NonNull Class<?> sourceType;
	private final @NonNull Class<?> targetType;

	public UnconvertibleException(final @Nullable Object source, final @NonNull Class<?> sourceType,
			final @NonNull Class<?> targetType) {
		super(compileMessage(source, sourceType, targetType));

		this.source = source;
		this.sourceType = sourceType;
		this.targetType = targetType;
	}

	public UnconvertibleException(final @Nullable Object source, final @NonNull Class<?> sourceType,
			final @NonNull Class<?> targetType, final @Nullable Throwable cause) {
		super(compileMessage(source, sourceType, targetType), cause);

		this.source = source;
		this.sourceType = sourceType;
		this.targetType = targetType;
	}

	private static @NonNull String compileMessage(@Nullable Object source, @NonNull Class<?> sourceType,
			@NonNull Class<?> targetType) {
		return "Cannot convert [" + sourceType + "] to " + targetType;
	}
}
