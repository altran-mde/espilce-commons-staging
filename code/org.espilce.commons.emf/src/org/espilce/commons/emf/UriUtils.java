package org.espilce.commons.emf;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.exception.UnconvertibleException;

/**
 * Utilities for handling {@linkplain org.eclipse.emf.common.util.URI Ecore
 * URIs}.
 * 
 * @since 0.1
 *
 */
public class UriUtils {

	public static java.net.@Nullable URI toJavaUri(final @NonNull URI ecoreUri) {
		try {
			return new java.net.URI(ecoreUri.toString());
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public static java.net.@NonNull URI asJavaUri(final @NonNull URI ecoreUri) throws UnconvertibleException {
		try {
			return new java.net.URI(ecoreUri.toString());
		} catch (URISyntaxException e) {
			throw new UnconvertibleException(ecoreUri, URI.class, java.net.URI.class, e);
		}
	}

	public static @Nullable URI toEcoreUri(final java.net.@NonNull URI javaUri) {
		return URI.createURI(javaUri.toString());
	}

	public static @NonNull URI asEcoreUri(final java.net.@NonNull URI javaUri) throws UnconvertibleException {
		return URI.createURI(javaUri.toString());
	}

	public static @Nullable URL toJavaUrl(final @NonNull URI ecoreUri) {
		try {
			return new URL(ecoreUri.toString());
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public static @NonNull URL asJavaUrl(final @NonNull URI ecoreUri) throws UnconvertibleException {
		try {
			return new URL(ecoreUri.toString());
		} catch (MalformedURLException e) {
			throw new UnconvertibleException(ecoreUri, URI.class, URL.class, e);
		}
	}

	public static @Nullable URI toEcoreUri(final @NonNull URL javaUrl) {
		return URI.createURI(javaUrl.toString());
	}

	public static @NonNull URI asEcoreUri(final @NonNull URL javaUrl) throws UnconvertibleException {
		return URI.createURI(javaUrl.toString());
	}

}
