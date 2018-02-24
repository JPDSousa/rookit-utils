package org.rookit.utils;

import java.time.Duration;

@SuppressWarnings("javadoc")
public abstract class DurationUtils {

	private DurationUtils() {}
	
	public static Duration plus(final Duration one, final Duration another) {
		return one.plus(another);
	}
}
