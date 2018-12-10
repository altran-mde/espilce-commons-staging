package org.espilce.commons.generator.api.context;

import org.eclipse.jdt.annotation.NonNull;

/**
 * @noimplement
 * @since 2.9
 */
public interface IGeneratorContext {
	public @NonNull CancelIndicator getCancelIndicator();
}
