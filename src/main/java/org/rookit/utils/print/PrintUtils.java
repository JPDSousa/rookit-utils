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
package org.rookit.utils.print;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.rookit.utils.print.TypeFormat;

@SuppressWarnings("javadoc")
public final class PrintUtils {
	
	private static final Duration ONE_HOUR = Duration.ofHours(1);
	
	public static String duration(Duration duration) {
		if(duration.minus(ONE_HOUR).isNegative()) {
			return new StringBuilder(5)
					.append(normalizeUnit(duration.toMinutes()))
					.append(':')
					.append(normalizeUnit(duration.getSeconds() % 60))
					.toString();
		}
		return durationHMS(duration);
	}
	
	private static String durationHMS(Duration duration) {
		return new StringBuilder(10)
				.append(normalizeUnit(duration.toHours()))
				.append(':')
				.append(normalizeUnit(duration.toMinutes() % 60))
				.append(':')
				.append(normalizeUnit(duration.getSeconds() % 60))
				.toString();
	}
	
	private static String normalizeUnit(long unit) {
		if(unit < 10) {
			return "0" + unit;
		}
		return String.valueOf(unit);
	}

	public static <T> String getIterableAsString(final Iterable<T> elements, final String separator, final String emptyMessage) {
		if(IterableUtils.isEmpty(elements)){
			return emptyMessage;
		}
		return StreamSupport.stream(elements.spliterator(), false).map(Object::toString).collect(Collectors.joining(separator));
	}

	public static <T> String getIterableAsString(final Iterable<T> elements, final TypeFormat format, final String emptyMessage){
		return getIterableAsString(elements, format.getSeparator(), emptyMessage);
	}

	public static <T> String getIterableAsString(final Iterable<T> elements, final char separator, final String emptyMessage){
		return getIterableAsString(elements, Character.toString(separator), emptyMessage);
	}

	public static <T> String getIterableAsString(final Iterable<T> elements, final String separator) {
		return StreamSupport.stream(elements.spliterator(), false).map(Object::toString).collect(Collectors.joining(separator));
	}

	public static <T> String getIterableAsString(final Iterable<T> elements, final TypeFormat format){
		return getIterableAsString(elements, format.getSeparator());
	}

	public static <T> String getIterableAsString(final Iterable<T> elements, final char separator){
		return getIterableAsString(elements, Character.toString(separator));
	}

	private PrintUtils(){}

}
