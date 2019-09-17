package org.espilce.commons.lang.test.junit5;

import java.util.stream.Stream;

import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.support.AnnotationConsumer;

public class ConversionArgumentsProvider extends AConversionArgumentsProvider
		implements AnnotationConsumer<TestConversion>
{
	private static final String ALT_SEPARATOR = "\\";
	
	private String value;

	@Override
	public void accept(final TestConversion t) {
		final String tmp = t.value();
		
		if (tmp != TestConversion.DEFAULT) {
			this.value = tmp;
		} else {
			this.value = null;
		}
	}
	
	@Override
	public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
		retrieveClassConfig(context);
		
		final Stream<String> base;
		if (OS.WINDOWS.isCurrentOs()) {
			base = Stream.of(this.value, this.value.replace(SEPARATOR, ALT_SEPARATOR));
		} else {
			base = Stream.of(this.value);
		}
		
		return base
				.flatMap(v -> getMethods().map(f -> Arguments.of(f, v)));
	}
}
