package org.espilce.commons.emf;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.exception.UnconvertibleException;

/**
 * Utilities for converting {@linkplain org.eclipse.emf.common.util.URI EMF
 * URIs}, {@linkplain java.net.URI Java URIs}, and {@linkplain java.net.URL Java
 * URLs}.
 * 
 * <p>
 * Each conversion method exists in two variants:
 * </p>
 * 
 * <ul>
 * <li><code><b>to</b>TargetType(SourceType)</code> returns {@code null} if the
 * conversion is unsuccessful.</li>
 * <li><code><b>as</b>TargetType(SourceType)</code> throws a
 * {@link UnconvertibleException} if the conversion is unsuccessful.</li>
 * </ul>
 * 
 * @see org.espilce.commons.emf.resource.UriResourceUtils
 * 
 * @since 0.1
 *
 */
public class UriUtils {

	/**
	 * Converts an EMF URI to a Java URI, if possible.
	 * 
	 * @param emfUri
	 *            EMF URI to convert.
	 * @return Java URI equivalent to <code>emfUri</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.1
	 */
	public static java.net.@Nullable URI toJavaUri(final @NonNull URI emfUri) {
		try {
			return new java.net.URI(emfUri.toString());
		} catch (URISyntaxException e) {
			return null;
		}
	}

	/**
	 * Converts an EMF URI to a Java URI.
	 * 
	 * @param emfUri
	 *            EMF URI to convert.
	 * @return Java URI equivalent to <code>emfUri</code>.
	 * @throws UnconvertibleException
	 *             if conversion is unsuccessful.
	 * @since 0.1
	 */
	public static java.net.@NonNull URI asJavaUri(final @NonNull URI emfUri) throws UnconvertibleException {
		try {
			return new java.net.URI(emfUri.toString());
		} catch (URISyntaxException e) {
			throw new UnconvertibleException(emfUri, URI.class, java.net.URI.class, e);
		}
	}

	/**
	 * Converts an EMF URI to a Java URL, if possible.
	 * 
	 * @param emfUri
	 *            EMF URI to convert.
	 * @return Java URL equivalent to <code>emfUri</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @Nullable URL toJavaUrl(final @NonNull URI emfUri) {
		try {
			return new URL(emfUri.toString());
		} catch (MalformedURLException e) {
			return null;
		}
	}

	/**
	 * Converts an EMF URI to a Java URL.
	 * 
	 * @param emfUri
	 *            EMF URI to convert.
	 * @return Java URL equivalent to <code>emfUri</code>.
	 * @throws UnconvertibleException
	 *             if conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @NonNull URL asJavaUrl(final @NonNull URI emfUri) throws UnconvertibleException {
		try {
			return new URL(emfUri.toString());
		} catch (MalformedURLException e) {
			throw new UnconvertibleException(emfUri, URI.class, URL.class, e);
		}
	}

	/**
	 * Converts a Java URL to a EMF URI, if possible.
	 * 
	 * @param javaUrl
	 *            Java URL to convert.
	 * @return EMF URI equivalent to <code>javaUrl</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @Nullable URI toEmfUri(final @NonNull URL javaUrl) {
		return URI.createURI(javaUrl.toString());
	}

	/**
	 * Converts a Java URL to a EMF URI.
	 * 
	 * @param javaUrl
	 *            Java URL to convert.
	 * @return EMF URI equivalent to <code>javaUrl</code>.
	 * @throws UnconvertibleException
	 *             if conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @NonNull URI asEmfUri(final @NonNull URL javaUrl) throws UnconvertibleException {
		return URI.createURI(javaUrl.toString());
	}

	/**
	 * Converts a Java URI to a EMF URI, if possible.
	 * 
	 * @param javaUri
	 *            Java URI to convert.
	 * @return EMF URI equivalent to <code>javaUri</code>, or {@code null} if
	 *         conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @Nullable URI toEmfUri(final java.net.@NonNull URI javaUri) {
		return URI.createURI(javaUri.toString());
	}

	/**
	 * Converts a Java URI to a EMF URI.
	 * 
	 * @param javaUri
	 *            Java URI to convert.
	 * @return EMF URI equivalent to <code>javaUri</code>.
	 * @throws UnconvertibleException
	 *             if conversion is unsuccessful.
	 * @since 0.1
	 */
	public static @NonNull URI asEmfUri(final java.net.@NonNull URI javaUri) throws UnconvertibleException {
		return URI.createURI(javaUri.toString());
	}

	public static @Nullable URI toEmfUri(final @NonNull File file) {
		try {
			return URI.createFileURI(file.getAbsolutePath());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public static @NonNull URI asEmfUri(final @NonNull File file) throws UnconvertibleException {
		try {
			return URI.createFileURI(file.getAbsolutePath());
		} catch (IllegalArgumentException e) {
			throw new UnconvertibleException(file, File.class, URI.class, e);
		}
	}

	public static @Nullable URI toEmfUri(final @NonNull Path path) {
		try {
			return toEmfUri(path.toAbsolutePath().toUri());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}
