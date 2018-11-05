package org.espilce.commons.resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;

public class ContentTypeUtils {
	protected ContentTypeUtils() {

	}

	public static IContentType findContentType(final IFile file) {
		try {
			final IContentDescription contentDescription = file.getContentDescription();
			if (contentDescription != null) {
				return contentDescription.getContentType();
			} else {
				return null;
			}
		} catch (final CoreException e) {
			throw new RuntimeException(e);
		}
	}

	public static IContentType findContentType(final File file) {
		return findContentType(file.getName());
	}

	public static IContentType findContentType(final String fileName) {
		return Arrays.asList(Platform.getContentTypeManager().getAllContentTypes()).stream()
				.filter(ct -> ct.isAssociatedWith(fileName)).findAny().orElse(null);
	}

	/**
	 * @return A list containing all file extensions registered for the given
	 *         content type id.
	 */
	public static List<String> getFileExtensions(final String contentTypeIdentifier) {
		final IContentType contentType = toContentType(contentTypeIdentifier);
		return contentType == null ? new ArrayList<>() : getFileExtensions(contentType);
	}

	/**
	 * @return A list containing all file extensions registered for the given
	 *         content type
	 */
	public static List<String> getFileExtensions(final IContentType contentType) {
		return Arrays.asList(contentType.getFileSpecs(IContentType.FILE_EXTENSION_SPEC));
	}

	public static IContentType toContentType(final String contentTypeIdentifier) {
		return Platform.getContentTypeManager().getContentType(contentTypeIdentifier);
	}
}
