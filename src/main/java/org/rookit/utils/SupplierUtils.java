package org.rookit.utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

@SuppressWarnings("javadoc")
public abstract class SupplierUtils {

    public static <T> Supplier<T> fixedValueSupplier(final T fixedValue) {
        return () -> fixedValue;
    }

    public static IntSupplier incrementalSupplier(final int lowerBound) {
        final AtomicInteger state = new AtomicInteger(lowerBound);

        return () -> state.getAndIncrement();
    }

    private SupplierUtils() {
    }

}
