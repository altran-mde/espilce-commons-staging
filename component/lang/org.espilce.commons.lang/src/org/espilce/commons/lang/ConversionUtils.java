package org.espilce.commons.lang;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.espilce.commons.exception.UnconvertibleException;

/**
 * Utilities for converting {@linkplain java.net.URI Java URIs},
 * {@linkplain java.net.URL Java URLs}, {@linkplain java.io.File Java Files},
 * and {@linkplain java.nio.file.Path Java Paths}.
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
 * <p>
 * Most exceptions are swallowed (for the <code>toTargetType()</code> variants)
 * or wrapped (for the <code>asTargetType()</code> variants). However,
 * {@linkplain java.lang.Error Errors} and
 * {@linkplain java.lang.SecurityException SecurityExceptions} are <i>not</i>
 * suppressed.
 * </p>
 * 
 * @see org.espilce.commons.emf.UriUtils
 * @see org.espilce.commons.emf.resource.UriResourceUtils
 * @see org.espilce.commons.resource.ResourceUtils
 * 
 * @since 0.5
 */
public class ConversionUtils {
	private static final String CURRENT = ".";
	private static final String PARENT = "..";
	private static final String SEPARATOR_INVALID_AUTHORITY = "//";
	private static final String SEPARATOR_SCHEME = ":";
	private static final String SEPARATOR_URL_PATH = "/";
	private static final String SEPARATOR_WIN = "\\";
	private static final String SCHEME_FILE = "file";
	private static final String SCHEME_FILE_SEPARATOR = SCHEME_FILE + SEPARATOR_SCHEME;
	
	/**
	 * 
	 * @param javaUri
	 * @return
	 * @since 0.5
	 */
	public static @Nullable Path toJavaPath(final @Nullable URI javaUri) {
		if (javaUri == null) {
			return null;
		}
		
		try {
			final @NonNull URI javaUriNonNull = javaUri;
			return asJavaPath(javaUriNonNull);
		} catch (final UnconvertibleException e) {
			// fall-through
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param javaUri
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull Path asJavaPath(final @NonNull URI javaUri) throws UnconvertibleException {
		try {
			try {
				final URI adjustedJavaUri = getFixedInvalid(javaUri);
				if (adjustedJavaUri != null) {
					return Paths.get(adjustedJavaUri);
				}
				return Paths.get(javaUri);
			} catch (final IllegalArgumentException | URISyntaxException e) {
				if (hasQueryOrFragment(javaUri)) {
					// we cannot represent any of the conditions in a Path
					throw e;
				}
				try {
					String schemeSpecificPart = javaUri.getSchemeSpecificPart();
					if (javaUri.isAbsolute()) {
						return Paths.get(schemeSpecificPart);
					}
					
					if (!javaUri.isOpaque()) {
						// try to resolve relative to current path
						final Path currentPath = Paths.get("").toAbsolutePath();
						final URI currentUri = currentPath.toUri();
						URI resolved = null;
						try {
							resolved = currentUri.resolve(schemeSpecificPart);
						} catch (final IllegalArgumentException ex) {
							schemeSpecificPart = replaceSeparatorWinUrl(schemeSpecificPart);
							resolved = currentUri.resolve(schemeSpecificPart);
						}
						final Path resolvedPath = Paths.get(resolved);
						
						try {
							final Path result = currentPath.relativize(resolvedPath);
							if (schemeSpecificPart.startsWith(CURRENT) && !result.toString().startsWith(CURRENT)) {
								// retain explicit reference to current path
								return Paths.get(CURRENT, result.toString());
							}
							return result;
						} catch (final IllegalArgumentException ex) {
							// e.g. "Path types differ exception"
							return Paths.get(resolved.getSchemeSpecificPart());
						}
					}
				} catch (IllegalArgumentException | FileSystemNotFoundException ex) {
					throw ex;
				}
				throw e;
			}
		} catch (final IllegalArgumentException | FileSystemNotFoundException | URISyntaxException e) {
			throw new UnconvertibleException(javaUri, URI.class, Path.class, e);
		}
	}
	
	private static boolean hasQueryOrFragment(final URI javaUri) {
		return javaUri.getQuery() != null || javaUri.getFragment() != null;
	}
	
	/**
	 * 
	 * @param javaUrl
	 * @return
	 * @since 0.5
	 */
	public static @Nullable Path toJavaPath(final @Nullable URL javaUrl) {
		if (javaUrl == null) {
			return null;
		}
		
		if (isEmpty(javaUrl)) {
			return Paths.get("");
		}
		
		try {
			return toJavaPath(javaUrl.toURI());
		} catch (final URISyntaxException e) {
			// fall-through
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param javaUrl
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull Path asJavaPath(final @NonNull URL javaUrl) throws UnconvertibleException {
		if (isEmpty(javaUrl)) {
			return Paths.get("");
		}
		
		try {
			return asJavaPath(javaUrl.toURI());
		} catch (final URISyntaxException e) {
			throw new UnconvertibleException(javaUrl, URL.class, Path.class, e);
		} catch (final UnconvertibleException e) {
			throw new UnconvertibleException(javaUrl, URL.class, Path.class, e.getCause());
		}
	}
	
	/**
	 * 
	 * @param javaFile
	 * @return
	 * @since 0.5
	 */
	public static @Nullable Path toJavaPath(final @Nullable File javaFile) {
		if (javaFile == null) {
			return null;
		}
		
		try {
			return javaFile.toPath();
		} catch (final InvalidPathException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param javaFile
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull Path asJavaPath(final @NonNull File javaFile) throws UnconvertibleException {
		try {
			return javaFile.toPath();
		} catch (final InvalidPathException e) {
			throw new UnconvertibleException(javaFile, File.class, Path.class, e);
		}
	}
	
	/**
	 * 
	 * @param javaUri
	 * @return
	 * @since 0.5
	 */
	public static @Nullable File toJavaFile(final @Nullable URI javaUri) {
		final Path path = toJavaPath(javaUri);
		if (path == null) {
			return null;
		}
		
		return path.toFile();
	}
	
	/**
	 * 
	 * @param javaUri
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull File asJavaFile(final @NonNull URI javaUri) throws UnconvertibleException {
		try {
			return asJavaPath(javaUri).toFile();
		} catch (final UnconvertibleException e) {
			throw new UnconvertibleException(javaUri, URI.class, File.class, e.getCause());
		} catch (final UnsupportedOperationException e) {
			throw new UnconvertibleException(javaUri, URI.class, File.class, e);
		}
	}
	
	/**
	 * 
	 * @param javaUrl
	 * @return
	 * @since 0.5
	 */
	public static @Nullable File toJavaFile(final @Nullable URL javaUrl) {
		if (javaUrl == null) {
			return null;
		}
		
		if (isEmpty(javaUrl)) {
			return new File("");
		}
		
		try {
			return toJavaFile(javaUrl.toURI());
		} catch (final URISyntaxException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param javaUrl
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull File asJavaFile(final @NonNull URL javaUrl) throws UnconvertibleException {
		Objects.requireNonNull(javaUrl);
		
		if (isEmpty(javaUrl)) {
			return new File("");
		}
		try {
			return asJavaPath(javaUrl).toFile();
		} catch (final UnconvertibleException e) {
			throw new UnconvertibleException(javaUrl, URL.class, File.class, e.getCause());
		}
	}
	
	/**
	 * 
	 * @param javaPath
	 * @return
	 * @since 0.5
	 */
	public static @Nullable File toJavaFile(final @Nullable Path javaPath) {
		if (javaPath == null) {
			return null;
		}
		
		try {
			return javaPath.toFile();
		} catch (final UnsupportedOperationException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param javaPath
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull File asJavaFile(final @NonNull Path javaPath) throws UnconvertibleException {
		try {
			return javaPath.toFile();
		} catch (final UnsupportedOperationException e) {
			throw new UnconvertibleException(javaPath, Path.class, File.class, e);
		}
	}
	
	/**
	 * 
	 * @param javaFile
	 * @return
	 * @since 0.5
	 */
	public static @Nullable URI toJavaUri(final @Nullable File javaFile) {
		if (javaFile == null) {
			return null;
		}
		
		if (javaFile.isAbsolute()) {
			return javaFile.toURI();
		}
		
		try {
			final String adjustedSeparators = adjustFileSeparators(javaFile);
			return new URI(null, null, adjustedSeparators, null);
		} catch (final URISyntaxException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param javaFile
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull URI asJavaUri(final @NonNull File javaFile) throws UnconvertibleException {
		if (javaFile.isAbsolute()) {
			return javaFile.toURI();
		}
		
		try {
			final String adjustedSeparators = adjustFileSeparators(javaFile);
			return new URI(null, null, adjustedSeparators, null);
		} catch (final URISyntaxException e) {
			throw new UnconvertibleException(javaFile, File.class, URI.class, e);
		}
	}
	
	/**
	 * 
	 * @param javaUrl
	 * @return
	 * @since 0.5
	 */
	public static @Nullable URI toJavaUri(final @Nullable URL javaUrl) {
		if (javaUrl == null) {
			return null;
		}
		
		try {
			return javaUrl.toURI();
		} catch (final URISyntaxException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param javaUrl
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull URI asJavaUri(final @NonNull URL javaUrl) throws UnconvertibleException {
		try {
			return javaUrl.toURI();
		} catch (final URISyntaxException e) {
			throw new UnconvertibleException(javaUrl, URL.class, URI.class, e);
		}
	}
	
	/**
	 * 
	 * @param javaPath
	 * @return
	 * @since 0.5
	 */
	public static @Nullable URI toJavaUri(final @Nullable Path javaPath) {
		if (javaPath == null) {
			return null;
		}
		
		if (javaPath.isAbsolute()) {
			return javaPath.toUri();
		}
		
		try {
			final String adjustedSeparators = replaceSeparatorPathUrl(javaPath);
			return new URI(null, adjustedSeparators, null);
		} catch (final URISyntaxException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param javaPath
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull URI asJavaUri(final @NonNull Path javaPath) throws UnconvertibleException {
		if (javaPath.isAbsolute()) {
			return javaPath.toUri();
		}
		
		try {
			final String adjustedSeparators = replaceSeparatorPathUrl(javaPath);
			return new URI(null, adjustedSeparators, null);
		} catch (final URISyntaxException e) {
			throw new UnconvertibleException(javaPath, Path.class, URI.class, e);
		}
	}
	
	/**
	 * 
	 * @param javaUri
	 * @return
	 * @since 0.5
	 */
	public static @Nullable URL toJavaUrl(final @Nullable URI javaUri) {
		if (javaUri == null) {
			return null;
		}
		
		try {
			return javaUri.toURL();
		} catch (IllegalArgumentException | MalformedURLException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param javaUri
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull URL asJavaUrl(final @NonNull URI javaUri) throws UnconvertibleException {
		try {
			return javaUri.toURL();
		} catch (IllegalArgumentException | MalformedURLException e) {
			throw new UnconvertibleException(javaUri, URI.class, URL.class, e);
		}
	}
	
	/**
	 * 
	 * @param javaFile
	 * @return
	 * @since 0.5
	 */
	public static @Nullable URL toJavaUrl(final @Nullable File javaFile) {
		final URI uri = toJavaUri(javaFile);
		if (uri == null) {
			return null;
		}
		
		try {
			if (uri.isAbsolute()) {
				final URL result = uri.toURL();
				return result;
			}
			
			final URL result = new URL(SCHEME_FILE_SEPARATOR + uri.toASCIIString());
			return result;
		} catch (final MalformedURLException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param javaFile
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull URL asJavaUrl(final @NonNull File javaFile) throws UnconvertibleException {
		final URI uri;
		try {
			uri = asJavaUri(javaFile);
		} catch (final UnconvertibleException e) {
			throw new UnconvertibleException(javaFile, File.class, URL.class, e.getCause());
		}
		
		try {
			if (uri.isAbsolute()) {
				final URL result = uri.toURL();
				return result;
			}
			
			final URL result = new URL(SCHEME_FILE_SEPARATOR + uri.toASCIIString());
			return result;
		} catch (IllegalArgumentException | MalformedURLException e) {
			throw new UnconvertibleException(javaFile, File.class, URL.class, e);
		}
	}
	
	/**
	 * 
	 * @param javaPath
	 * @return
	 * @since 0.5
	 */
	public static @Nullable URL toJavaUrl(final @Nullable Path javaPath) {
		if (javaPath == null) {
			return null;
		}
		
		try {
			return asJavaUrl(javaPath);
		} catch (final UnconvertibleException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param javaPath
	 * @return
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull URL asJavaUrl(final @NonNull Path javaPath) throws UnconvertibleException {
		try {
			if (javaPath.isAbsolute()) {
				return javaPath.toUri().toURL();
			}
			
			final String adjustedSeparators = replaceSeparatorPathUrl(javaPath);
			if (StringUtils.isBlank(adjustedSeparators)) {
				return new URL(SCHEME_FILE_SEPARATOR);
			}
			
			return new URI(SCHEME_FILE, adjustedSeparators, null).toURL();
		} catch (IllegalArgumentException | MalformedURLException | URISyntaxException e) {
			throw new UnconvertibleException(javaPath, Path.class, URL.class, e);
		}
	}
	
	private static boolean isEmpty(final @Nullable URL javaUrl) {
		if (javaUrl == null) {
			return false;
		}
		
		return "".equals(javaUrl.getPath())
				&& hasFileScheme(javaUrl)
				&& javaUrl.getAuthority() == null
				&& javaUrl.getQuery() == null
				&& javaUrl.getRef() == null;
	}

	private static boolean hasFileScheme(final URL javaUrl) {
		final String protocol = javaUrl.getProtocol();
		return protocol != null && SCHEME_FILE.equalsIgnoreCase(protocol);
	}
	
	private static boolean hasFileScheme(final URI javaUri) {
		final String scheme = javaUri.getScheme();
		return scheme != null && SCHEME_FILE.equalsIgnoreCase(scheme);
	}
	
	private static @NonNull String adjustFileSeparators(final @NonNull File javaFile) {
		String adjustedSeparators = replaceSeparatorFileUrl(javaFile.getPath());
		if (adjustedSeparators.endsWith(":")) {
			adjustedSeparators += "/";
		}
		
		return adjustedSeparators;
	}
	
	private static String replaceSeparatorWinUrl(final String schemeSpecificPart) {
		return schemeSpecificPart.replace(SEPARATOR_WIN, SEPARATOR_URL_PATH);
	}
	
	private static String replaceSeparatorFileUrl(final String path) {
		return path.replace(File.separator, SEPARATOR_URL_PATH);
	}
	
	private static String replaceSeparatorPathUrl(final Path javaPath) {
		return javaPath.toString().replace(javaPath.getFileSystem().getSeparator(), SEPARATOR_URL_PATH);
	}
	
	/**
	 * Handling invalid <tt>file://c:/path/to/file.txt</tt> URIs.
	 * 
	 * @param javaUri
	 * @return
	 * @throws URISyntaxException
	 */
	private static @Nullable URI getFixedInvalid(final @NonNull URI javaUri) throws URISyntaxException {
		final String scheme = javaUri.getScheme();
		if (hasFileScheme(javaUri)) {
			final String schemeSpecificPart = javaUri.getSchemeSpecificPart();
			if (schemeSpecificPart != null && schemeSpecificPart.startsWith(SEPARATOR_INVALID_AUTHORITY)) {
				final String authority = javaUri.getAuthority();
				if (authority != null && authority.endsWith(SEPARATOR_SCHEME)) {
					final String path = SEPARATOR_URL_PATH + authority + javaUri.getRawPath();
					final URI adjustedJavaUri = new URI(
							scheme, javaUri.getRawUserInfo(), null, javaUri.getPort(), path, javaUri.getRawQuery(),
							javaUri.getRawFragment()
					);
					return adjustedJavaUri;
				}
			}
		}
		return null;
	}
	
	/**
	 * Handling invalid <tt>file://c:/path/to/file.txt</tt> URLs.
	 * 
	 * @param javaUrl
	 * @return
	 * @throws MalformedURLException
	 */
	private static @Nullable URL getFixedInvalid(final @NonNull URL javaUrl) throws MalformedURLException {
		if (hasFileScheme(javaUrl)) {
			final String authority = javaUrl.getAuthority();
			if (authority != null && authority.endsWith(SEPARATOR_SCHEME)) {
				final String file = SEPARATOR_URL_PATH + authority + javaUrl.getFile();
				final URL adjustedJavaUrl = new URL(javaUrl.getProtocol(), null, javaUrl.getPort(), file);
				return adjustedJavaUrl;
			}
		}
		return null;
	}
}
