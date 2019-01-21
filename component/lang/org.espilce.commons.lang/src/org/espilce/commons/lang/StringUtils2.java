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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Operations on Strings that are not found in
 * {@linkplain org.apache.commons.lang3.StringUtils Apache StringUtils}.
 *
 * @since 0.3
 */
public class StringUtils2 {

	/**
	 * Replaces all newline variants with the single variant defined in
	 * {@link NewlineNormalizer#getDefault()}.
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
	 * @since 0.3
	 */
	public static @Nullable String normalizeNewline(final @Nullable String str) {
		return NewlineNormalizer.getDefault().normalize(str);
	}

	/**
	 * Replaces all newline variants within an array of Strings with the single
	 * variant defined in {@link NewlineNormalizer#getDefault()}.
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
	 * @since 0.3
	 */
	public static @Nullable String[] normalizeNewlines(final @Nullable String... strs) {
		return NewlineNormalizer.getDefault().normalize(strs);
	}

	/**
	 * Compares all Strings in an array and returns the last sequence of
	 * characters that is common to all of them.
	 *
	 * <p>
	 * For example, {@code getCommonSuffix(new String[] {"machine a am i",
	 * "robot a am i"})} -> {@code " a am i"}
	 * </p>
	 *
	 * <p>
	 * Keeps order and {@code null} entries in the array.
	 * </p>
	 *
	 * <pre>
	 * 	StringUtils2.getCommonSuffix(null)                                            = ""
	 * 	StringUtils2.getCommonSuffix(new String[] {})                                 = ""
	 * 	StringUtils2.getCommonSuffix(new String[] {"abc"})                            = "abc"
	 * 	StringUtils2.getCommonSuffix(new String[] {null, null})                       = ""
	 * 	StringUtils2.getCommonSuffix(new String[] {"", ""})                           = ""
	 * 	StringUtils2.getCommonSuffix(new String[] {"", null})                         = ""
	 * 	StringUtils2.getCommonSuffix(new String[] {"abc", null, null})                = ""
	 * 	StringUtils2.getCommonSuffix(new String[] {null, null, "abc"})                = ""
	 * 	StringUtils2.getCommonSuffix(new String[] {"", "abc"})                        = ""
	 * 	StringUtils2.getCommonSuffix(new String[] {"abc", ""})                        = ""
	 * 	StringUtils2.getCommonSuffix(new String[] {"abc", "abc"})                     = "abc"
	 * 	StringUtils2.getCommonSuffix(new String[] {"abc", "c"})                       = "c"
	 * 	StringUtils2.getCommonSuffix(new String[] {"ab", "xyzab"})                    = "ab"
	 * 	StringUtils2.getCommonSuffix(new String[] {"abcde", "xyzde"})                 = "de"
	 * 	StringUtils2.getCommonSuffix(new String[] {"abcde", "xyz"})                   = ""
	 * 	StringUtils2.getCommonSuffix(new String[] {"xyz", "abcde"})                   = ""
	 * 	StringUtils2.getCommonSuffix(new String[] {"machine a am i", "robot a am i"}) = " a am i"
	 * </pre>
	 *
	 * @param strs
	 *            Array of String objects, entries may be {@code null}.
	 * @return The last sequence of characters that are common to all Strings in
	 *         the array; empty String if the array is {@code null}, the
	 *         elements are all {@code null} or if there is no common suffix.
	 * @since 0.3
	 */
	public static @Nullable String getCommonSuffix(final @Nullable String... strs) {
		if (strs == null) {
			return null;
		}

		final String[] reversed = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			reversed[i] = StringUtils.reverse(strs[i]);
		}

		final String reversedCommonPrefix = StringUtils.getCommonPrefix(reversed);
		return StringUtils.reverse(reversedCommonPrefix);
	}

	/**
	 * Removes the common prefix of all Strings in an array.
	 *
	 * <p>
	 * Common prefix is defined at
	 * {@link StringUtils#getCommonPrefix(String...)}.
	 * </p>
	 *
	 * <p>
	 * Keeps order and {@code null} entries in the array.
	 * </p>
	 *
	 * <p>
	 * No-op for arrays with zero or one element.
	 * </p>
	 *
	 * <pre>
	 * 	StringUtils2.removeCommonPrefix(null)                                            = ""
	 * 	StringUtils2.removeCommonPrefix(new String[] {})                                 = {}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"abc"})                            = {"abc"}
	 * 	StringUtils2.removeCommonPrefix(new String[] {null, null})                       = {null, null}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"", ""})                           = {"", ""}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"", null})                         = {"", null}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"abc", null, null})                = {"abc", null, null}
	 * 	StringUtils2.removeCommonPrefix(new String[] {null, null, "abc"})                = {null, null, "abc"}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"", "abc"})                        = {"", "abc"}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"abc", ""})                        = {"abc", ""}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"abc", "abc"})                     = {"", ""}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"abc", "a"})                       = {"bc", ""}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"ab", "abxyz"})                    = {"", "xyz"}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"abcde", "abxyz"})                 = {"cde", "xyz"}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"abcde", "xyz"})                   = {"abcde", "xyz"}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"xyz", "abcde"})                   = {"xyz", "abcde"}
	 * 	StringUtils2.removeCommonPrefix(new String[] {"machine a am i", "robot a am i"}) = {"machine", "robot"}
	 * </pre>
	 *
	 * @param strs
	 *            Array of String objects, entries may be {@code null}.
	 * @return A new array of Strings with the same elements as
	 *         <code>strs</code>, each stripped of their common prefix;
	 *         {@code null} if <code>strs</code> is {@code null};
	 *         <code>strs</code> if not more than one element is present.
	 * @since 0.3
	 */
	public static @Nullable String[] removeCommonPrefix(final @Nullable String... strs) {
		if (strs == null) {
			return null;
		}

		if (strs.length <= 1) {
			return strs;
		}

		final String commonPrefix = StringUtils.getCommonPrefix(strs);
		if (commonPrefix != null && !commonPrefix.isEmpty()) {
			final int commonPrefixLength = commonPrefix.length();
			final String[] result = new String[strs.length];

			for (int i = 0; i < strs.length; i++) {
				result[i] = StringUtils.substring(strs[i], commonPrefixLength);
			}

			return result;
		}

		return strs;
	}
}
