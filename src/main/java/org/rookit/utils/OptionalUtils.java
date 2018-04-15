package org.rookit.utils;

import java.util.Arrays;
import java.util.Optional;

@SuppressWarnings("javadoc")
public final class OptionalUtils {

    public static boolean allPresent(final Optional<?>... optionals) {
        return Arrays.stream(optionals)
                .allMatch(Optional::isPresent);
    }

    private OptionalUtils() {
    }

}
