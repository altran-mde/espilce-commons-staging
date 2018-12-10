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