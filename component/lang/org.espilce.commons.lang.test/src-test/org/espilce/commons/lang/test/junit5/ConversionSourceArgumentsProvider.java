package org.espilce.commons.lang.test.junit5;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
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

public class ConversionSourceArgumentsProvider extends AConversionArgumentsProvider
		implements AnnotationConsumer<ConversionSource>
{
	private static final String[] SEPARATORS = new String[] { SEPARATOR, ALT_SEPARATOR };
	private static final String[] ROOTS = new String[] { "/", "c:/", "//" };
	
	private static final String PLACEHOLDER = "{}";
	private static final String NEWLINE = "\n";
	
	private String[] values;
	private boolean backslash;
	
	@Override
	public void accept(final ConversionSource t) {
		this.values = t.value();
		this.backslash = t.backslash();
	}
	
	@Override
	public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
		retrieveClassConfig(context);
		
		final CsvParserSettings settings = new CsvParserSettings();
		settings.getFormat().setDelimiter(',');
		settings.getFormat().setLineSeparator(NEWLINE);
		settings.getFormat().setQuote('\'');
		settings.getFormat().setQuoteEscape('\'');
		settings.setEmptyValue("");
		settings.setAutoConfigurationEnabled(false);
		final CsvParser csvParser = new CsvParser(settings);
		final AtomicLong index = new AtomicLong(0);
		
		final List<String[]> allValues = Arrays.stream(this.values)
				.map(line -> {
					String[] values = Preconditions
							.notNull(csvParser.parseLine(line + NEWLINE), () -> "Line at index " + index.get() + " contains invalid CSV: \"" + line + "\"");
					if (values.length == 1) {
						values = new String[] { values[0], null };
					}
					return values;
				})
				.peek(
						values -> Preconditions.condition(
								values.length == 2,
								() -> "Line at index " + index.get() + " has not 2 values: \"" + values + "\""
						)
				)
				.peek(values -> index.incrementAndGet())
				.collect(Collectors.toList());
		
		final Map<String, String> inputMap = new LinkedHashMap<>();
		
		for (final String[] value : allValues) {
			inputMap.put(value[0], value[1]);
		}
		
		final String template = inputMap.keySet().stream().filter(k -> k.contains(PLACEHOLDER)).findAny()
				.orElseThrow(() -> new JUnitException("Cannot find \"" + PLACEHOLDER + "\" to replace"));
		final String tplValue = inputMap.get(template);
		
		final Map<String, String> outputMap = new LinkedHashMap<>(inputMap);
		
		outputMap.remove(template);
		
		final String[] separators = this.backslash ? SEPARATORS : new String[] { SEPARATOR };
		
		final int expectedSize = ROOTS.length * separators.length * (int) getMethods().count();
		
		for (final String start : ROOTS) {
			final Pattern pattern = Pattern
					.compile("^" + ((start.length() == 1) ? (start + "([^" + SEPARATOR + "]|$)") : start) + ".*");
			final String tpl = inputMap.keySet().stream().filter(k -> pattern.matcher(k).matches()).findAny()
					.orElse(template);
			for (final String sep : separators) {
				final String key = tpl.replace(PLACEHOLDER, start).replace(SEPARATOR, sep);
				final String valueRaw = inputMap.getOrDefault(tpl, tplValue);
				final String value = valueRaw == null ? null : valueRaw.replace(PLACEHOLDER, start);
				// if (!start.replace(SEPARATOR, sep).startsWith(ALT_SEPARATOR)
				// || this.backslash) {
					if (!outputMap.containsKey(key)) {
						outputMap.put(key, value);
					}
				// } else {
				// expectedSize -= 2;
				// }
			}
		}
		
		final List<Arguments> result = outputMap.keySet().stream()
				.flatMap(k -> getMethods().map(f -> Arguments.of(f, k, outputMap.get(k))))
				.collect(Collectors.toList());
		
		
		Preconditions.condition(
				result.size() == expectedSize,
				"Not " + expectedSize + " but " + result.size() + " elements: "
						+ result.stream().map(a -> Arrays.asList(a.get())).collect(Collectors.toList())
		);
		
		return result.stream();
	}
}
