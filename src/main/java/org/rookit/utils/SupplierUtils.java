package org.rookit.utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

@SuppressWarnings("javadoc")
public abstract class SupplierUtils {

	public static IntSupplier incrementalSupplier(final int lowerBound) {
		final AtomicInteger state = new AtomicInteger(lowerBound);
		
		return () -> state.getAndIncrement();
	}
	
	public static <T> Supplier<T> fixedValueSupplier(T fixedValue) {
		return () -> fixedValue;
	}
	
	private SupplierUtils() {}
	
}
