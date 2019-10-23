package org.espilce.commons.lang;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;

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
	private static final String URI_ENCODING = "UTF-8";
	private static final int UNDEFINED_PORT = -1;
	private static final String ESCAPED_COLON = "%3A";
	private static final String EMPTY_STRING = "";
	private static final String SEPARATOR_INVALID_AUTHORITY = "//";
	private static final String SEPARATOR_SCHEME = ":";
	private static final String SEPARATOR_URL_PATH = "/";
	private static final String SCHEME_FILE = "file";
	private static final String SCHEME_FILE_SEPARATOR = SCHEME_FILE + SEPARATOR_SCHEME;
	
	
	/**
	 * Converts a {@linkplain java.net.URI Java URI} to a
	 * {@linkplain java.nio.file.Path Java Path}.
	 * 
	 * <pre>
	 * toJavaPath(new URI(""))                                        = Paths.get("")
	 * toJavaPath(new URI("."))                                       = Paths.get(".")
	 * toJavaPath(new URI("MyFile.ext"))                              = Paths.get("MyFile.ext")
	 * toJavaPath(new URI("some/path/MyFile.ext"))                    = Paths.get("some/path/MyFile.ext")
	 * toJavaPath(new URI("/some/path/MyFile.ext"))                   = Paths.get("/some/path/MyFile.ext")
	 * toJavaPath(new URI("../some/path/."))                          = Paths.get("../some/path/.")
	 * toJavaPath(new URI("file:."))                                  = Paths.get(".")
	 * toJavaPath(new URI("file:../some/path/."))                     = Paths.get("../some/path/.")
	 * toJavaPath(new URI("file:/some/path/MyFile.ext"))              = Paths.get("/some/path/MyFile.ext")
	 * toJavaPath(new URI("file://some/path/MyFile.ext"))             = Paths.get("//some/path/MyFile.ext")
	 * toJavaPath(new URI("file:///some/path/MyFile.ext"))            = Paths.get("/some/path/MyFile.ext")
	 * toJavaPath(new URI("file:////some/path/MyFile.ext"))           = Paths.get("//some/path/MyFile.ext")
	 * toJavaPath(new URI("file:/some/path/"))                        = Paths.get("/some/path")
	 * toJavaPath(new URI("file:/some/path"))                         = Paths.get("/some/path")
	 * toJavaPath(new URI("file:/some//////path"))                    = Paths.get("/some/path")
	 * toJavaPath(new URI("file:/../some/path/."))                    = Paths.get("/../some/path/.")
	 * toJavaPath(new URI("file:/myProject/myFolder%23query"))        = Paths.get("/myProject/myFolder#query")
	 * toJavaPath(new URI("file:/myProject/myFolder?query#fragment")) = null
	 * toJavaPath(new URI("c:/some/path/MyFile.ext"))                 = null
	 * toJavaPath(new URI("http://example.com"))                      = null
	 * toJavaPath((URI) null)                                         = null
	 * 
	 * toJavaPath(new URI("file:/c:/some/path/MyFile.ext"))           = (win)  Paths.get("c:/some/path/MyFile.ext")
	 *                                                                  (unix) Paths.get("/c:/some/path/MyFile.ext")
	 * </pre>
	 * 
	 * @param javaUri
	 *            URI to convert.
	 * @return <code>javaUri</code> converted to Java Path; {@code null} if
	 *         <code>javaUri</code> is {@code null} or cannot be converted to a
	 *         Java Path.
	 * @since 0.5
	 */
	public static @Nullable Path toJavaPath(final @Nullable URI javaUri) {
		if (javaUri == null) {
			return null;
		}
		
		try {
			return asJavaPath(javaUri);
		} catch (final UnconvertibleException e) {
			return null;
		}
	}
	
	/**
	 * Converts a {@linkplain java.net.URL Java URL} to a
	 * {@linkplain java.nio.file.Path Java Path}.
	 * 
	 * <pre>
	 * toJavaPath(new URL("file:."))                                  = Paths.get(".")
	 * toJavaPath(new URL("file:../some/path/."))                     = Paths.get("../some/path/.")
	 * toJavaPath(new URL("file:/some/path/MyFile.ext"))              = Paths.get("/some/path/MyFile.ext")
	 * toJavaPath(new URL("file://some/path/MyFile.ext"))             = Paths.get("//some/path/MyFile.ext")
	 * toJavaPath(new URL("file:///some/path/MyFile.ext"))            = Paths.get("/some/path/MyFile.ext")
	 * toJavaPath(new URL("file:////some/path/MyFile.ext"))           = Paths.get("//some/path/MyFile.ext")
	 * toJavaPath(new URL("file:/some/path/"))                        = Paths.get("/some/path")
	 * toJavaPath(new URL("file:/some/path"))                         = Paths.get("/some/path")
	 * toJavaPath(new URL("file:/some//////path"))                    = Paths.get("/some/path")
	 * toJavaPath(new URL("file:/../some/path/."))                    = Paths.get("/../some/path/.")
	 * toJavaPath(new URL("file:\\some\\path\\MyFile.ext"))           = Paths.get("\\some\\path\\MyFile.ext")
	 * toJavaPath(new URL("file:/myProject/myFolder%23query"))        = Paths.get("/myProject/myFolder#query")
	 * toJavaPath(new URL("file:/myProject/myFolder?query#fragment")) = null
	 * toJavaPath(new URL("http://example.com"))                      = null
	 * toJavaPath((URL) null)                                         = null
	 * 
	 * toJavaPath(new URL("file:/c:/some/path/MyFile.ext"))           = (win)  Paths.get("c:/some/path/MyFile.ext")
	 *                                                                  (unix) Paths.get("/c:/some/path/MyFile.ext")
	 * </pre>
	 * 
	 * @param javaUrl
	 *            URL to convert.
	 * @return <code>javaUrl</code> converted to Java Path; {@code null} if
	 *         <code>javaUrl</code> is {@code null} or cannot be converted to a
	 *         Java Path.
	 * @since 0.5
	 */
	public static @Nullable Path toJavaPath(final @Nullable URL javaUrl) {
		if (javaUrl == null) {
			return null;
		}
		
		try {
			return asJavaPath(javaUrl);
		} catch (final UnconvertibleException e) {
			return null;
		}
	}
	
	/**
	 * Converts a {@linkplain java.io.File Java File} to a
	 * {@linkplain java.nio.file.Path Java Path}.
	 * 
	 * <pre>
	 * toJavaPath(new File(""))                                   = Paths.get("")
	 * toJavaPath(new File("."))                                  = Paths.get(".")
	 * toJavaPath(new File("MyFile.ext"))                         = Paths.get("MyFile.ext")
	 * toJavaPath(new File("some/path/MyFile.ext"))               = Paths.get("some/path/MyFile.ext")
	 * toJavaPath(new File("../some/path/."))                     = Paths.get("../some/path/.")
	 * toJavaPath(new File("/some/path/MyFile.ext"))              = Paths.get("/some/path/MyFile.ext")
	 * toJavaPath(new File("//some/path/MyFile.ext"))             = Paths.get("//some/path/MyFile.ext")
	 * toJavaPath(new File("///some/path/MyFile.ext"))            = Paths.get("/some/path/MyFile.ext")
	 * toJavaPath(new File("////some/path/MyFile.ext"))           = Paths.get("//some/path/MyFile.ext")
	 * toJavaPath(new File("/some/path/"))                        = Paths.get("/some/path")
	 * toJavaPath(new File("/some/path"))                         = Paths.get("/some/path")
	 * toJavaPath(new File("/some//////path"))                    = Paths.get("/some/path")
	 * toJavaPath(new File("/../some/path/."))                    = Paths.get("/../some/path/.")
	 * toJavaPath(new File("/myProject/myFolder%23query"))        = Paths.get("/myProject/myFolder%23query")
	 * toJavaPath(new File("c:/some/path/MyFile.ext"))            = Paths.get("c:/some/path/MyFile.ext")
	 * toJavaPath(new File("..\\some\\path\\."))                  = Paths.get("..\\some\\path\\.")
	 * toJavaPath(new File("\\some\\path\\MyFile.ext"))           = Paths.get("\\some\\path\\MyFile.ext")
	 * toJavaPath(new File("c:\\some\\path\\MyFile.ext"))         = Paths.get("c:\\some\\path\\MyFile.ext")
	 * toJavaPath((File) null)                                    = null
	 * 
	 * toJavaPath(new File("/myProject/myFolder?query#fragment")) = (win)  null
	 *                                                              (unix) Paths.get("/myProject/myFolder?query#fragment")
	 * toJavaPath(new File("/c:/some/path/MyFile.ext"))           = (win)  Paths.get("c:/some/path/MyFile.ext")
	 *                                                              (unix) Paths.get("/c:/some/path/MyFile.ext")
	 * </pre>
	 * 
	 * @param javaFile
	 *            File to convert.
	 * @return <code>javaFile</code> converted to Java Path; {@code null} if
	 *         <code>javaFile</code> is {@code null} or cannot be converted to a
	 *         Java Path.
	 * @since 0.5
	 */
	public static @Nullable Path toJavaPath(final @Nullable File javaFile) {
		if (javaFile == null) {
			return null;
		}
		
		try {
			return asJavaPath(javaFile);
		} catch (final UnconvertibleException e) {
			return null;
		}
	}
	
	/**
	 * Converts a {@linkplain java.net.URI Java URI} to a
	 * {@linkplain java.io.File Java File}.
	 * 
	 * <pre>
	 * toJavaFile(new URI(""))                                        = new File("")
	 * toJavaFile(new URI("."))                                       = new File(".")
	 * toJavaFile(new URI("MyFile.ext"))                              = new File("MyFile.ext")
	 * toJavaFile(new URI("some/path/MyFile.ext"))                    = new File("some/path/MyFile.ext")
	 * toJavaFile(new URI("/some/path/MyFile.ext"))                   = new File("/some/path/MyFile.ext")
	 * toJavaFile(new URI("../some/path/."))                          = new File("../some/path/.")
	 * toJavaFile(new URI("file:."))                                  = new File(".")
	 * toJavaFile(new URI("file:../some/path/."))                     = new File("../some/path/.")
	 * toJavaFile(new URI("file:/some/path/MyFile.ext"))              = new File("/some/path/MyFile.ext")
	 * toJavaFile(new URI("file://some/path/MyFile.ext"))             = new File("//some/path/MyFile.ext")
	 * toJavaFile(new URI("file:///some/path/MyFile.ext"))            = new File("/some/path/MyFile.ext")
	 * toJavaFile(new URI("file:////some/path/MyFile.ext"))           = new File("//some/path/MyFile.ext")
	 * toJavaFile(new URI("file:/some/path/"))                        = new File("/some/path")
	 * toJavaFile(new URI("file:/some/path"))                         = new File("/some/path")
	 * toJavaFile(new URI("file:/some//////path"))                    = new File("/some/path")
	 * toJavaFile(new URI("file:/../some/path/."))                    = new File("/../some/path/.")
	 * toJavaFile(new URI("file:/myProject/myFolder%23query"))        = new File("/myProject/myFolder#query")
	 * toJavaFile(new URI("file:/myProject/myFolder?query#fragment")) = null
	 * toJavaFile(new URI("c:/some/path/MyFile.ext"))                 = null
	 * toJavaFile(new URI("http://example.com"))                      = null
	 * toJavaFile((URI) null)                                         = null
	 * 
	 * toJavaFile(new URI("file:/c:/some/path/MyFile.ext"))           = (win)  new File("c:/some/path/MyFile.ext")
	 *                                                                  (unix) new File("/c:/some/path/MyFile.ext")
	 * </pre>
	 * 
	 * @param javaUri
	 *            URI to convert.
	 * @return <code>javaUri</code> converted to Java File; {@code null} if
	 *         <code>javaUri</code> is {@code null} or cannot be converted to a
	 *         Java File.
	 * @since 0.5
	 */
	public static @Nullable File toJavaFile(final @Nullable URI javaUri) {
		if (javaUri == null) {
			return null;
		}
		
		try {
			return asJavaFile(javaUri);
		} catch (final UnconvertibleException e) {
			return null;
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
		
		try {
			return asJavaFile(javaUrl);
		} catch (final UnconvertibleException e) {
			return null;
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
			return asJavaFile(javaPath);
		} catch (final UnconvertibleException e) {
			return null;
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
		
		try {
			return asJavaUri(javaFile);
		} catch (final UnconvertibleException e) {
			return null;
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
			return asJavaUri(javaUrl);
		} catch (final UnconvertibleException e) {
			return null;
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
		
		try {
			return asJavaUri(javaPath);
		} catch (final UnconvertibleException e) {
			return null;
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
			return asJavaUrl(javaUri);
		} catch (final UnconvertibleException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param javaFile
	 * @return
	 * @since 0.5
	 */
	public static @Nullable URL toJavaUrl(final @Nullable File javaFile) {
		if (javaFile == null) {
			return null;
		}
		
		try {
			return asJavaUrl(javaFile);
		} catch (final UnconvertibleException e) {
			return null;
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
	 * Converts a {@linkplain java.net.URI Java URI} to a
	 * {@linkplain java.io.File Java File}.
	 * 
	 * <pre>
	 * asJavaFile(new URI(""))                                        = new File("")
	 * asJavaFile(new URI("."))                                       = new File(".")
	 * asJavaFile(new URI("MyFile.ext"))                              = new File("MyFile.ext")
	 * asJavaFile(new URI("some/path/MyFile.ext"))                    = new File("some/path/MyFile.ext")
	 * asJavaFile(new URI("/some/path/MyFile.ext"))                   = new File("/some/path/MyFile.ext")
	 * asJavaFile(new URI("../some/path/."))                          = new File("../some/path/.")
	 * asJavaFile(new URI("file:."))                                  = new File(".")
	 * asJavaFile(new URI("file:../some/path/."))                     = new File("../some/path/.")
	 * asJavaFile(new URI("file:/some/path/MyFile.ext"))              = new File("/some/path/MyFile.ext")
	 * asJavaFile(new URI("file://some/path/MyFile.ext"))             = new File("//some/path/MyFile.ext")
	 * asJavaFile(new URI("file:///some/path/MyFile.ext"))            = new File("/some/path/MyFile.ext")
	 * asJavaFile(new URI("file:////some/path/MyFile.ext"))           = new File("//some/path/MyFile.ext")
	 * asJavaFile(new URI("file:/some/path/"))                        = new File("/some/path")
	 * asJavaFile(new URI("file:/some/path"))                         = new File("/some/path")
	 * asJavaFile(new URI("file:/some//////path"))                    = new File("/some/path")
	 * asJavaFile(new URI("file:/../some/path/."))                    = new File("/../some/path/.")
	 * asJavaFile(new URI("file:/myProject/myFolder%23query"))        = new File("/myProject/myFolder#query")
	 * asJavaFile(new URI("file:/myProject/myFolder?query#fragment")) = UnconvertibleException
	 * asJavaFile(new URI("c:/some/path/MyFile.ext"))                 = UnconvertibleException
	 * asJavaFile(new URI("http://example.com"))                      = UnconvertibleException
	 * asJavaFile((URI) null)                                         = NullPointerException
	 * 
	 * asJavaFile(new URI("file:/c:/some/path/MyFile.ext"))           = (win)  new File("c:/some/path/MyFile.ext")
	 *                                                                  (unix) new File("/c:/some/path/MyFile.ext")
	 * </pre>
	 * 
	 * @param javaUri
	 *            URI to convert.
	 * @return <code>javaUri</code> converted to Java File.
	 * @throws UnconvertibleException
	 *             If <code>javaUri</code> cannot be converted to a Java File.
	 * @throws NullPointerExcpetion
	 *             If <code>javaUri</code> is {@code null}.
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
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull File asJavaFile(final @NonNull URL javaUrl) throws UnconvertibleException {
		Objects.requireNonNull(javaUrl);
		
		if (isEmpty(javaUrl)) {
			return new File(EMPTY_STRING);
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
	 * @throws UnconvertibleException
	 * @since 0.5
	 */
	public static @NonNull URI asJavaUri(final @NonNull File javaFile) throws UnconvertibleException {
		if (javaFile.isAbsolute()) {
			return javaFile.toURI();
		}
		
		try {
			final String adjustedSeparators = adjustFileSeparators(javaFile);
			return asJavaUriColonSafe(adjustedSeparators);
		} catch (final URISyntaxException e) {
			throw new UnconvertibleException(javaFile, File.class, URI.class, e);
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
			return asJavaUriColonSafe(adjustedSeparators);
		} catch (final URISyntaxException e) {
			throw new UnconvertibleException(javaPath, Path.class, URI.class, e);
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
				return uri.toURL();
			}
			
			return new URL(SCHEME_FILE_SEPARATOR + uri.toASCIIString());
		} catch (IllegalArgumentException | MalformedURLException e) {
			throw new UnconvertibleException(javaFile, File.class, URL.class, e);
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
			
			if (StringUtils.isBlank(javaPath.toString())) {
				return new URL(SCHEME_FILE_SEPARATOR);
			}
			
			final URI uri = asJavaUri(javaPath);
			return new URL(SCHEME_FILE_SEPARATOR + uri.toASCIIString());
		} catch (final UnconvertibleException e) {
			throw new UnconvertibleException(javaPath, Path.class, URL.class, e.getCause());
		} catch (IllegalArgumentException | MalformedURLException e) {
			throw new UnconvertibleException(javaPath, Path.class, URL.class, e);
		}
	}
	
	/**
	 * Converts a {@linkplain java.net.URL Java URL} to a
	 * {@linkplain java.nio.file.Path Java Path}.
	 * 
	 * <pre>
	 * asJavaPath(new URL("file:."))                                  = Paths.get(".")
	 * asJavaPath(new URL("file:../some/path/."))                     = Paths.get("../some/path/.")
	 * asJavaPath(new URL("file:/some/path/MyFile.ext"))              = Paths.get("/some/path/MyFile.ext")
	 * asJavaPath(new URL("file://some/path/MyFile.ext"))             = Paths.get("//some/path/MyFile.ext")
	 * asJavaPath(new URL("file:///some/path/MyFile.ext"))            = Paths.get("/some/path/MyFile.ext")
	 * asJavaPath(new URL("file:////some/path/MyFile.ext"))           = Paths.get("//some/path/MyFile.ext")
	 * asJavaPath(new URL("file:/some/path/"))                        = Paths.get("/some/path")
	 * asJavaPath(new URL("file:/some/path"))                         = Paths.get("/some/path")
	 * asJavaPath(new URL("file:/some//////path"))                    = Paths.get("/some/path")
	 * asJavaPath(new URL("file:/../some/path/."))                    = Paths.get("/../some/path/.")
	 * toJavaPath(new URL("file:\\some\\path\\MyFile.ext"))           = Paths.get("\\some\\path\\MyFile.ext")
	 * asJavaPath(new URL("file:/myProject/myFolder%23query"))        = Paths.get("/myProject/myFolder#query")
	 * asJavaPath(new URL("file:/myProject/myFolder?query#fragment")) = UnconvertibleException
	 * asJavaPath(new URL("http://example.com"))                      = UnconvertibleException
	 * asJavaPath((URL) null)                                         = NullPointerException
	 * 
	 * asJavaPath(new URL("file:/c:/some/path/MyFile.ext"))           = (win)  Paths.get("c:/some/path/MyFile.ext")
	 *                                                                  (unix) Paths.get("/c:/some/path/MyFile.ext")
	 * </pre>
	 * 
	 * @param javaUrl
	 *            URL to convert.
	 * @return <code>javaUrl</code> converted to Java Path.
	 * @throws UnconvertibleException
	 *             If <code>javaUrl</code> cannot be converted to a Java Path.
	 * @throws NullPointerExcpetion
	 *             If <code>javaUrl</code> is {@code null}.
	 * @since 0.5
	 */
	public static @NonNull Path asJavaPath(final @NonNull URL javaUrl) throws UnconvertibleException {
		if (isEmpty(javaUrl)) {
			return Paths.get(EMPTY_STRING);
		}
		
		try {
			final URI uri = asJavaUri(javaUrl);
			return asJavaPath(uri);
		} catch (final UnconvertibleException e) {
			throw new UnconvertibleException(javaUrl, URL.class, Path.class, e.getCause());
		}
	}
	
	/**
	 * Converts a {@linkplain java.io.File Java File} to a
	 * {@linkplain java.nio.file.Path Java Path}.
	 * 
	 * <pre>
	 * asJavaPath(new File(""))                                   = Paths.get("")
	 * asJavaPath(new File("."))                                  = Paths.get(".")
	 * asJavaPath(new File("MyFile.ext"))                         = Paths.get("MyFile.ext")
	 * asJavaPath(new File("some/path/MyFile.ext"))               = Paths.get("some/path/MyFile.ext")
	 * asJavaPath(new File("../some/path/."))                     = Paths.get("../some/path/.")
	 * asJavaPath(new File("/some/path/MyFile.ext"))              = Paths.get("/some/path/MyFile.ext")
	 * asJavaPath(new File("//some/path/MyFile.ext"))             = Paths.get("//some/path/MyFile.ext")
	 * asJavaPath(new File("///some/path/MyFile.ext"))            = Paths.get("/some/path/MyFile.ext")
	 * asJavaPath(new File("////some/path/MyFile.ext"))           = Paths.get("//some/path/MyFile.ext")
	 * asJavaPath(new File("/some/path/"))                        = Paths.get("/some/path")
	 * asJavaPath(new File("/some/path"))                         = Paths.get("/some/path")
	 * asJavaPath(new File("/some//////path"))                    = Paths.get("/some/path")
	 * asJavaPath(new File("/../some/path/."))                    = Paths.get("/../some/path/.")
	 * asJavaPath(new File("/myProject/myFolder%23query"))        = Paths.get("/myProject/myFolder%23query")
	 * asJavaPath(new File("c:/some/path/MyFile.ext"))            = Paths.get("c:/some/path/MyFile.ext")
	 * asJavaPath(new File("..\\some\\path\\."))                  = Paths.get("..\\some\\path\\.")
	 * asJavaPath(new File("\\some\\path\\MyFile.ext"))           = Paths.get("\\some\\path\\MyFile.ext")
	 * asJavaPath(new File("c:\\some\\path\\MyFile.ext"))         = Paths.get("c:\\some\\path\\MyFile.ext")
	 * asJavaPath((File) null)                                    = NullPointerException
	 * 
	 * asJavaPath(new File("/myProject/myFolder?query#fragment")) = (win)  UnconvertibleException
	 *                                                              (unix) Paths.get("/myProject/myFolder?query#fragment")
	 * asJavaPath(new File("/c:/some/path/MyFile.ext"))           = (win)  Paths.get("c:/some/path/MyFile.ext")
	 *                                                              (unix) Paths.get("/c:/some/path/MyFile.ext")
	 * </pre>
	 * 
	 * @param javaFile
	 *            File to convert.
	 * @return <code>javaFile</code> converted to Java Path; {@code null} if
	 *         <code>javaFile</code> is {@code null} or cannot be converted to a
	 *         Java Path.
	 * @throws UnconvertibleException
	 *             If <code>javaFile</code> cannot be converted to a Java Path.
	 * @throws NullPointerExcpetion
	 *             If <code>javaFile</code> is {@code null}.
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
	 * Converts a {@linkplain java.net.URI Java URI} to a
	 * {@linkplain java.nio.file.Path Java Path}.
	 * 
	 * <pre>
	 * asJavaPath(new URI(""))                                        = Paths.get("")
	 * asJavaPath(new URI("."))                                       = Paths.get(".")
	 * asJavaPath(new URI("MyFile.ext"))                              = Paths.get("MyFile.ext")
	 * asJavaPath(new URI("some/path/MyFile.ext"))                    = Paths.get("some/path/MyFile.ext")
	 * asJavaPath(new URI("/some/path/MyFile.ext"))                   = Paths.get("/some/path/MyFile.ext")
	 * asJavaPath(new URI("../some/path/."))                          = Paths.get("../some/path/.")
	 * asJavaPath(new URI("file:."))                                  = Paths.get(".")
	 * asJavaPath(new URI("file:../some/path/."))                     = Paths.get("../some/path/.")
	 * asJavaPath(new URI("file:/some/path/MyFile.ext"))              = Paths.get("/some/path/MyFile.ext")
	 * asJavaPath(new URI("file://some/path/MyFile.ext"))             = Paths.get("//some/path/MyFile.ext")
	 * asJavaPath(new URI("file:///some/path/MyFile.ext"))            = Paths.get("/some/path/MyFile.ext")
	 * asJavaPath(new URI("file:////some/path/MyFile.ext"))           = Paths.get("//some/path/MyFile.ext")
	 * asJavaPath(new URI("file:/some/path/"))                        = Paths.get("/some/path")
	 * asJavaPath(new URI("file:/some/path"))                         = Paths.get("/some/path")
	 * asJavaPath(new URI("file:/some//////path"))                    = Paths.get("/some/path")
	 * asJavaPath(new URI("file:/../some/path/."))                    = Paths.get("/../some/path/.")
	 * asJavaPath(new URI("file:/myProject/myFolder%23query"))        = Paths.get("/myProject/myFolder#query")
	 * asJavaPath(new URI("file:/myProject/myFolder?query#fragment")) = UnconvertibleException
	 * asJavaPath(new URI("c:/some/path/MyFile.ext"))                 = UnconvertibleException
	 * asJavaPath(new URI("http://example.com"))                      = UnconvertibleException
	 * asJavaPath((URI) null)                                         = NullPointerException
	 * 
	 * asJavaPath(new URI("file:/c:/some/path/MyFile.ext"))           = (win)  Paths.get("c:/some/path/MyFile.ext")
	 *                                                                  (unix) Paths.get("/c:/some/path/MyFile.ext")
	 * </pre>
	 * 
	 * @param javaUri
	 *            URI to convert.
	 * @return <code>javaUri</code> converted to Java Path.
	 * @throws UnconvertibleException
	 *             If <code>javaUri</code> cannot be converted to a Java Path.
	 * @throws NullPointerExcpetion
	 *             If <code>javaUri</code> is {@code null}.
	 * @since 0.5
	 */
	public static @NonNull Path asJavaPath(final @NonNull URI javaUri) throws UnconvertibleException {
		if (hasScheme(javaUri) && !hasFileScheme(javaUri)) {
			throw new UnconvertibleException(javaUri, URI.class, Path.class);
		}
		
		try {
			try {
				final URI adjustedJavaUri = getFixedInvalid(javaUri);
				
				Path result;
				if (adjustedJavaUri != null) {
					result = Paths.get(adjustedJavaUri);
				} else {
					result = Paths.get(javaUri);
				}
				
				return stripLeadingInvalidAuthority(result);
			} catch (final IllegalArgumentException | FileSystemNotFoundException | URISyntaxException e) {
				if (hasQueryOrFragment(javaUri)) {
					// we cannot represent any of the conditions in a Path
					throw e;
				}
				try {
					final String schemeSpecificPart = javaUri.getSchemeSpecificPart();
					return Paths.get(schemeSpecificPart);
				} catch (final FileSystemNotFoundException ex) {
					throw ex;
				}
			}
		} catch (final IllegalArgumentException | FileSystemNotFoundException | URISyntaxException e) {
			throw new UnconvertibleException(javaUri, URI.class, Path.class, e);
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
			try {
				final String scheme = decodeUrlPart(javaUrl.getProtocol());
				final String userInfo = decodeUrlPart(javaUrl.getUserInfo());
				final String host = StringUtils.isNotEmpty(javaUrl.getHost()) ? decodeUrlPart(javaUrl.getHost()) : null;
				final int port = javaUrl.getPort();
				final String path = decodeUrlPart(javaUrl.getPath());
				final String query = decodeUrlPart(javaUrl.getQuery());
				final String fragment = decodeUrlPart(javaUrl.getRef());
				
				try {
					return new URI(scheme, userInfo, host, port, path, query, fragment);
				} catch (final URISyntaxException e1) {
					try {
						final URI uri = new URI(null, userInfo, host, port, path, query, fragment);
						if (!hasScheme(uri)) {
							return uri;
						}
					} catch (final URISyntaxException e2) {
						// fall-through
					}
					
					try {
						return asJavaUriColonSafe(userInfo, host, port, path, query, fragment);
					} catch (final URISyntaxException e3) {
						throw new UnconvertibleException(javaUrl, URL.class, URI.class, e);
					}
				}
			} catch (final UnsupportedEncodingException e4) {
				throw new UnconvertibleException(javaUrl, URL.class, URI.class, e);
			}
		}
	}
	
	private static URI asJavaUriColonSafe(
			final String userInfo,
			final String host,
			final int port,
			final String path,
			final String query,
			final String fragment
	) throws URISyntaxException {
		try {
			final URI result = new URI(null, userInfo, host, port, path, query, fragment);
			if (!hasScheme(result)) {
				return result;
			} else {
				final String escapedSchemeSeparator = replaceSchemeSeparator(result.toASCIIString(), ESCAPED_COLON);
				return new URI(escapedSchemeSeparator);
			}
		} catch (final URISyntaxException e) {
			final String uniqueMarker = createUniqueMarker(path);
			final String uniqueMarkerSchemeSeparator = replaceSchemeSeparator(path, uniqueMarker);
			final URI tmpUri = new URI(null, null, uniqueMarkerSchemeSeparator, null);
			final URI escapedUri = new URI(tmpUri.toASCIIString().replace(uniqueMarker, ESCAPED_COLON));
			return escapedUri;
		}
	}
	
	private static URI asJavaUriColonSafe(final String adjustedSeparators) throws URISyntaxException {
		return asJavaUriColonSafe(null, null, UNDEFINED_PORT, adjustedSeparators, null, null);
	}
	
	private static Path stripLeadingInvalidAuthority(Path result) {
		while (result.toString().startsWith(SEPARATOR_INVALID_AUTHORITY)) {
			result = Paths.get(result.toString().substring(1));
		}
		
		return result;
	}
	
	private static String replaceSchemeSeparator(final String uriAscii, final String replacement) {
		final int indexFirstSlash = uriAscii.indexOf(SEPARATOR_URL_PATH);
		
		final String result;
		if (indexFirstSlash > -1) {
			result = uriAscii.substring(0, indexFirstSlash).replace(SEPARATOR_SCHEME, replacement)
					+ uriAscii.substring(indexFirstSlash);
		} else {
			result = uriAscii.replace(SEPARATOR_SCHEME, replacement);
		}
		return result;
	}
	
	private static String createUniqueMarker(final String asciiString) {
		final StringBuilder tmp = new StringBuilder("__tmp__");
		final Random random = new Random();
		while (asciiString.contains(tmp)) {
			tmp.append(random.nextInt());
			tmp.append("__");
		}
		return tmp.toString();
	}
	
	private static boolean hasScheme(final URI javaUri) {
		return javaUri.getScheme() != null;
	}
	
	private static boolean hasScheme(final URL javaUrl) {
		return javaUrl.getProtocol() != null;
	}
	
	private static boolean hasQueryOrFragment(final URI javaUri) {
		return javaUri.getQuery() != null || javaUri.getFragment() != null;
	}
	
	private static @Nullable String decodeUrlPart(@Nullable final String str) throws UnsupportedEncodingException {
		if (str == null) {
			return null;
		}
		
		return URLDecoder.decode(str, URI_ENCODING);
	}
	
	private static boolean isEmpty(final @Nullable URL javaUrl) {
		if (javaUrl == null) {
			return false;
		}
		
		return EMPTY_STRING.equals(javaUrl.getPath())
				&& hasFileScheme(javaUrl)
				&& javaUrl.getAuthority() == null
				&& javaUrl.getQuery() == null
				&& javaUrl.getRef() == null;
	}
	
	private static boolean hasFileScheme(final URL javaUrl) {
		return hasScheme(javaUrl) && SCHEME_FILE.equalsIgnoreCase(javaUrl.getProtocol());
	}
	
	private static boolean hasFileScheme(final URI javaUri) {
		return hasScheme(javaUri) && SCHEME_FILE.equalsIgnoreCase(javaUri.getScheme());
	}
	
	private static @NonNull String adjustFileSeparators(final @NonNull File javaFile) {
		String adjustedSeparators = replaceSeparatorFileUrl(javaFile.getPath());
		if (adjustedSeparators.endsWith(SEPARATOR_SCHEME)) {
			adjustedSeparators += SEPARATOR_URL_PATH;
		}
		
		return adjustedSeparators;
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
}
