package org.rookit.utils;

import java.util.function.Consumer;

@SuppressWarnings("javadoc")
public final class ConsumerUtils {
    
    public static <E> Consumer<E> noOp() {
        return item -> {
            /* Do nothing here */ };
    }

    private ConsumerUtils() {
    }

}
