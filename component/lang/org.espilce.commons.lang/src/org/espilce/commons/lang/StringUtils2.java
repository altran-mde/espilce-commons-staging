package org.espilce.commons.lang;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class StringUtils2 {
	private static final String[] NEWLINE_VARIANTS = { "\r\n", "\n\r", "\r" };
	private static final String DEFAULT_NORMALIZED_NEWLINE = "\n";
	
	public static @Nullable String normalizeNewline(final @Nullable String text) {
		return normalizeNewline(text, DEFAULT_NORMALIZED_NEWLINE);
	}
	
	public static @Nullable String normalizeNewline(final @Nullable String text, final @NonNull String replacement) {
		return StringUtils.replaceEach(text, NEWLINE_VARIANTS,
				new String[] { replacement, replacement, replacement });
	}
	
	public static @Nullable String[] normalizeNewlines(final @NonNull String replacement,
			final @Nullable String[] strs) {
		if (strs == null) {
			return null;
		}
		
		if (strs.length == 0) {
			return strs;
		}
		
		final String[] result = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			result[i] = normalizeNewline(strs[i], replacement);
		}
		
		return result;
	}
	
	public static @Nullable String[] normalizeNewlines(final @Nullable String... strs) {
		return normalizeNewlines(DEFAULT_NORMALIZED_NEWLINE, strs);
	}
	
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
