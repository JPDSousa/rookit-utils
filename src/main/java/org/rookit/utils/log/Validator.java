/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.utils.log;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import org.apache.logging.log4j.Logger;

import com.google.common.collect.Range;

@SuppressWarnings("javadoc")
public abstract class Validator {
	
	private final LogManager category;
	private final Logger categoryLogger;
	
	public Validator(LogManager category) {
		this.category = category;
		this.categoryLogger = category.getLogger(getClass());
	}
	
	protected <T> T check(final boolean condition,
			final Supplier<RuntimeException> exceptionSupplier) {
		if (!condition) {
			return Errors.throwException(exceptionSupplier, categoryLogger);
		}
		return null;
	}
	
	public <T> T checkState(final boolean condition, final String message, final Object... args) {
		return check(condition, () -> new IllegalStateException(String.format(message, args)));
	}
	
	public <T> T checkArgument(final boolean condition, final String message, final Object... args) {
		return check(condition, () -> new IllegalArgumentException(String.format(message, args)));
	}
	
	public <T> T checkArgumentNotNull(final Object argument, final String message) {
		return checkArgument(Objects.nonNull(argument), message);
	}
	
	public <T> T checkArgumentStringNotEmpty(final String argument, final String message) {
		checkArgumentNotNull(argument, message);
		return checkArgument(!argument.isEmpty(), message);
	}
	
	public <T> T checkArgumentNonEmptyCollection(final Collection<?> argument, 
			final String message) {
		checkArgumentNotNull(argument, message);
		return checkArgument(!argument.isEmpty(), message);
	}
	
	public <T> T checkArgumentEmptyCollection(Collection<?> argument, String message) {
		checkArgumentNotNull(argument, message);
		return checkArgument(argument.isEmpty(), message);
	}
	
	public <T> T checkArgumentNotContains(final Object argument, final Collection<?> collection, 
			final String message) {
		checkArgumentNotNull(collection, "The collection cannot be null");
		return checkArgument(!collection.contains(argument), message);
	}
	
	public <T> T checkArgumentPositive(final long argument, final String message) {
		checkArgumentNotNull(argument, message);
		return checkArgument(argument > 0, message);
	}
	
	public <T> T checkArgumentPositive(final int argument, String message) {
		checkArgumentNotNull(argument, message);
		return checkArgument(argument > 0, message);
	}
	
	public <T> T checkArgumentBetween(final int argument, final int min, final int max, 
			final String message) {
		return checkArgumentBetween(argument, min, max, message, message);
	}
	
	public <T> T checkArgumentBetween(final double argument, 
			final double min, 
			final double max,
			final String message) {
		return checkArgumentBetween(argument, min, max, message, message);
	}
	
	public <T> T checkArgumentBetween(final double argument, 
			final double min, 
			final double max, 
			final String minMessage, 
			final String maxMessage) {
		checkArgument(argument >= min, minMessage);
		return checkArgument(argument <= max, maxMessage);
	}
	
	public <T, R extends Comparable<R>> T checkArgumentBetween(final R argument, 
			final Range<R> range, 
			final String argumentName) {
		final StringBuilder msgBuilder = new StringBuilder("Range for ")
				.append(argumentName)
				.append(" is ")
				.append(range)
				.append(" but value is ")
				.append(argument);
		
		return checkArgument(range.contains(argument), msgBuilder.toString());
	}
	
	public <T> T checkArgumentBetween(final int argument, final int min, final int max, 
			final String minMessage, final String maxMessage) {
		checkArgument(argument >= min, minMessage);
		return checkArgument(argument <= max, maxMessage);
	}
	
	public <T> T checkArgumentClass(final Class<?> expected, final Class<?> actual, 
			final String message) {
		checkArgumentNotNull(actual, message);
		checkArgumentNotNull(expected, "The expected class is null");
		return checkArgument(Objects.equals(expected, actual), message);
	}
	
	public <T> T checkSingleEntryMap(final Map<?, ?> map, final String message) {
		checkArgumentNotNull(map, "The map is null");
		return checkArgument(map.size() == 1, message);
	}
	
	public <T> T handleIOException(final IOException cause) {
		return Errors.throwException(new RuntimeException(cause), categoryLogger);
	}
	
	public <T> T invalidOperation(final String message) {
		return Errors.throwException(new UnsupportedOperationException(message), categoryLogger);
		
	}
	
	public <T> T runtimeException(final String message) {
		return Errors.throwException(new RuntimeException(message), categoryLogger);
	}
	
	public <T> T runtimeException(final Throwable cause) {
		return Errors.throwException(new RuntimeException(cause), categoryLogger);
	}
	
	public <T> T runtimeException(final String message, final Throwable cause) {
		return Errors.throwException(new RuntimeException(message, cause), categoryLogger);
	}

	public Logger getLogger(final Class<?> clazz) {
		return category.getLogger(clazz);
	}
	
}
