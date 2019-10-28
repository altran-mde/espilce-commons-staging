/*******************************************************************************
 * Copyright (C) 2019 Altran Netherlands B.V.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.espilce.commons.lang.test.junit5;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParserSettings;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.util.Preconditions;

/**
 * Creates all combinations of {@link #getMethods() methods}, input and expected
 * value based on {@link #ROOTS}, {@link #SEPARATORS}, and whether to use
 * {@link #mutateBackslash backslash}. Can {@link #replaceSeparator} also in the
 * expected value, if set.
 */
public class ConversionSourceArgumentsProvider extends AConversionArgumentsProvider
		implements AnnotationConsumer<ConversionSource>
{
	private static final String[] SEPARATORS = new String[] { SEPARATOR, ALT_SEPARATOR };
	private static final String[] ROOTS = new String[] { "/", "c:/", "//" };
	
	private static final String PLACEHOLDER = "{}";
	private static final String NEWLINE = "\n";
	
	private String[] values;
	private boolean mutateBackslash;
	private boolean replaceSeparator;
	
	@Override
	public void accept(final ConversionSource t) {
		this.values = t.value();
		this.mutateBackslash = t.backslash();
		this.replaceSeparator = t.replace();
	}
	
	@Override
	public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
		retrieveClassConfig(context);
		
		final Map<String, String> inputMap = parseValues();
		
		final String template = findTemplate(inputMap);
		final String tplValue = inputMap.get(template);
		
		final Map<String, String> outputMap = new LinkedHashMap<>(inputMap);
		
		outputMap.remove(template);
		
		final String[] separators = this.mutateBackslash ? SEPARATORS : new String[] { SEPARATOR };
		
		final int expectedSize = ROOTS.length * separators.length * (int) getMethods().count();
		
		for (final String start : ROOTS) {
			final String tpl = choseEntryOrTemplate(inputMap, template, start);
			for (final String sep : separators) {
				final String key = replaceSeparator(sep, replaceStart(start, tpl));
				final String valueRaw = inputMap.getOrDefault(tpl, tplValue);
				String value;
				if (valueRaw == null) {
					value = valueRaw;
				} else {
					value = replaceStart(start, valueRaw);
					if (this.replaceSeparator) {
						value = replaceSeparator(sep, value);
					}
				}
				if (!outputMap.containsKey(key)) {
					outputMap.put(key, value);
				}
			}
		}
		
		final List<Arguments> result = convertToArguments(outputMap);
		
		checkArgumentsCount(expectedSize, result);
		
		return result.stream();
	}
	
	private void checkArgumentsCount(final int expectedSize, final List<Arguments> result) {
		Preconditions.condition(
				result.size() == expectedSize,
				"Not " + expectedSize + " but " + result.size() + " elements: "
						+ result.stream().map(a -> Arrays.asList(a.get())).collect(Collectors.toList())
		);
	}
	
	private List<Arguments> convertToArguments(final Map<String, String> outputMap) {
		return outputMap.keySet().stream()
				.flatMap(k -> getMethods().map(f -> Arguments.of(f, k, outputMap.get(k))))
				.collect(Collectors.toList());
	}
	
	private String replaceSeparator(final String sep, final String value) {
		return value.replace(SEPARATOR, sep);
	}
	
	private String replaceStart(final String start, final String tpl) {
		return tpl.replace(PLACEHOLDER, start);
	}
	
	private String choseEntryOrTemplate(final Map<String, String> inputMap, final String template, final String start) {
		final Pattern pattern = Pattern
				.compile("^" + ((start.length() == 1) ? (start + "([^" + SEPARATOR + "]|$)") : start) + ".*");
		final String tpl = inputMap.keySet().stream().filter(k -> pattern.matcher(k).matches()).findAny()
				.orElse(template);
		return tpl;
	}
	
	private String findTemplate(final Map<String, String> inputMap) {
		return inputMap.keySet().stream().filter(k -> k.contains(PLACEHOLDER)).findAny()
				.orElseThrow(() -> new JUnitException("Cannot find \"" + PLACEHOLDER + "\" to replace"));
	}
	
	private Map<String, String> parseValues() {
		final CsvParser csvParser = configureParser();
		final AtomicLong index = new AtomicLong(0);
		
		final List<String[]> allValues = Arrays.stream(this.values)
				.map(line -> {
					String[] values = parseLine(csvParser, index, line);
					values = padSingleLineValue(values);
					return values;
				})
				.peek(checkDoubleLineValue(index))
				.peek(values -> index.incrementAndGet())
				.collect(Collectors.toList());
		
		final Map<String, String> inputMap = new LinkedHashMap<>();
		
		for (final String[] value : allValues) {
			inputMap.put(value[0], value[1]);
		}
		return inputMap;
	}
	
	private CsvParser configureParser() {
		final CsvParserSettings settings = new CsvParserSettings();
		settings.getFormat().setDelimiter(',');
		settings.getFormat().setLineSeparator(NEWLINE);
		settings.getFormat().setQuote('\'');
		settings.getFormat().setQuoteEscape('\'');
		settings.setEmptyValue("");
		settings.setAutoConfigurationEnabled(false);
		final CsvParser csvParser = new CsvParser(settings);
		return csvParser;
	}
	
	private Consumer<? super String[]> checkDoubleLineValue(final AtomicLong index) {
		return values -> Preconditions.condition(
				values.length == 2,
				() -> "Line at index " + index.get() + " has not 2 values: \"" + values + "\""
		);
	}
	
	private String[] padSingleLineValue(String[] values) {
		if (values.length == 1) {
			values = new String[] { values[0], null };
		}
		return values;
	}
	
	private String[] parseLine(final CsvParser csvParser, final AtomicLong index, final String line) {
		return Preconditions
				.notNull(csvParser.parseLine(line + NEWLINE), () -> "Line at index " + index.get() + " contains invalid CSV: \"" + line + "\"");
	}
}
