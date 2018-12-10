package org.espilce.commons.generator.api.fsa;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public interface IFileSystemAccessExtension4 {
	public @NonNull List<@NonNull String> findMatchingFiles(final @NonNull String parentPath);

	public @NonNull List<@NonNull String> findMatchingFiles(final @NonNull String parentPath,
			final @NonNull String outputConfigurationName);
}
