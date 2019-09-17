package org.espilce.commons.lang.test.junit5;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ParameterizedTest(name = "[{index}] {0}, {1}")
@ArgumentsSource(ConversionArgumentsProvider.class)
public @interface TestConversion {
	String value() default DEFAULT;
	
	static final String DEFAULT = "org.espilce.commons.lang.test.junit5.TestConversion.DEFAULT_VALUE";
}
