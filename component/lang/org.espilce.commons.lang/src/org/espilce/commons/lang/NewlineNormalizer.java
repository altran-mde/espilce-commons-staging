/*******************************************************************************
 * Copyright (C) 2018 Altran Netherlands B.V.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Replaces all newline variants with one single variant.
 *
 * <p>
 * The following strings are recognized as newline:
 * <p>
 *
 * <ul>
 * <li><i>carriage_return line_feed</i>, i.e. {@code "\r\n"}</li>
 * <li><i>line_feed carriage_return</i>, i.e. {@code "\n\r"}</li>
 * <li><i>carriage_return</i>, i.e. {@code "\r"}</li>
 * <li><i>line_feed</i>, i.e. {@code "\n"}</li>
 * </ul>
 *
 * @since 0.4
 */
public class NewlineNormalizer {
	/**
	 * Normalizes all newline variants to <i>carriage_return line_feed</i>, i.e.
	 * {@code "\r\n"}.
	 *
	 * @since 0.4
	 */
	public static final NewlineNormalizer CRLF = new NewlineNormalizer("\r\n");

	/**
	 * Normalizes all newline variants to the Windows platform default.
	 *
	 * @since 0.4
	 */
	public static final NewlineNormalizer WINDOWS = CRLF;

	/**
	 * Normalizes all newline variants to <i>line_feed carriage_return</i>, i.e.
	 * {@code "\n\r"}.
	 *
	 * @since 0.4
	 */
	public static final NewlineNormalizer LFCR = new NewlineNormalizer("\n\r");

	/**
	 * Normalizes all newline variants to <i>carriage_return</i>, i.e.
	 * {@code "\r"}.
	 *
	 * @since 0.4
	 */
	public static final NewlineNormalizer CR = new NewlineNormalizer("\r");

	/**
	 * Normalizes all newline variants to the old Mac OS 9.x platform default.
	 *
	 * @since 0.4
	 */
	public static final NewlineNormalizer MAC = CR;

	/**
	 * Normalizes all newline variants to <i>line_feed</i>, i.e. {@code "\n"}.
	 *
	 * @since 0.4
	 */
	public static final NewlineNormalizer LF = new NewlineNormalizer("\n");

	/**
	 * Normalizes all newline variants to the Unix platform default.
	 *
	 * @since 0.4
	 */
	public static final NewlineNormalizer UNIX = LF;

	/**
	 * Normalizes all newline variants to the running platform default.
	 *
	 * @since 0.4
	 */
	public static final NewlineNormalizer RUNNING_PLATFORM = new NewlineNormalizer(System.lineSeparator());

	private static final String[] NEWLINE_VARIANTS = { "\r\n", "\n\r", "\r", "\n" };

	private static NewlineNormalizer defaultNormalizer = LF;

	/**
	 * Returns the default normalizer.
	 *
	 * <p>
	 * Initialized to {@link #LF}.
	 * </p>
	 *
	 * @return The default normalizer.
	 *
	 * @see #setDefault(NewlineNormalizer)
	 */
	public static @NonNull NewlineNormalizer getDefault() {
		return defaultNormalizer;
	}

	/**
	 * Sets the default normalizer.
	 *
	 * @param newDefaultNormalizer
	 *            The new default normalizer.
	 * @see #getDefault()
	 */
	public static void setDefault(final @NonNull NewlineNormalizer newDefaultNormalizer) {
		defaultNormalizer = Objects.requireNonNull(newDefaultNormalizer);
	}

	private final String[] newlineReplacements;

	/**
	 * Creates a new normalizer using <code>replacement</code> as normalized
	 * newline.
	 *
	 * @param replacement
	 *            String to use as normalized newline.
	 */
	public NewlineNormalizer(final @NonNull String replacement) {
		this.newlineReplacements = new String[] { Objects.requireNonNull(replacement), replacement, replacement,
				replacement };
	}

	/**
	 * Replaces all newline variants within <code>str</code> with this
	 * instance's <i>replacement</i>.
	 *
	 * <pre>
	 * (assuming NewlineNormalizer.LF)
	 *
	 * normalize(null)                                = null
	 * normalize("")                                  = ""
	 * normalize("  ")                                = "  "
	 * normalize("Some text")                         = "Some text"
	 * normalize("Some text\n")                       = "Some text\n"
	 * normalize("Some\ntext")                        = "Some\ntext"
	 * normalize("Some\rtext")                        = "Some\ntext"
	 * normalize("Some\r\ntext")                      = "Some\ntext"
	 * normalize("Some\n\rtext")                      = "Some\ntext"
	 * normalize("Some\nlonger\rstrange\r\ntext\n\r") = "Some\nlonger\nstrange\ntext\n"
	 * </pre>
	 *
	 * @param str
	 *            String in witch all newline variants will be replaced.
	 *
	 * @return The input with all newline variants replaced; {@code null} if
	 *         input is {@code null}.
	 */
	public @Nullable String normalize(final @Nullable String str) {
		return StringUtils.replaceEach(str, NEWLINE_VARIANTS, this.newlineReplacements);
	}

	/**
	 * Replaces all newline variants within an array of Strings with this
	 * instance's <i>replacement</i>.
	 *
	 * <p>
	 * Keeps order and {@code null} entries in the array.
	 * </p>
	 *
	 * <pre>
	 * (assuming NewlineNormalizer.LF)
	 *
	 * normalize((String []) null)                                 = null
	 * normalize(new String[] { })                                 = { }
	 * normalize(new String[] { null })                            = { null }
	 * normalize(new String[] { "hello", null })                   = { "hello", null }
	 * normalize(new String[] { null, "hello\rthere!\r\n", null }) = { null, "hello\nthere!\n", null }
	 * </pre>
	 *
	 * @param strs
	 *            Array of Strings in witch all newline variants will be
	 *            replaced.
	 *
	 * @return <code>strs</code> with all newline variants replaced;
	 *         {@code null} if the array is {@code null}.
	 */
	public @Nullable String[] normalize(final @Nullable String... strs) {
		if (strs == null) {
			return null;
		}

		if (strs.length == 0) {
			return strs;
		}

		final String[] result = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			result[i] = normalize(strs[i]);
		}

		return result;
	}
}
