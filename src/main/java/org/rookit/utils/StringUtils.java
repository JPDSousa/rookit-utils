package org.rookit.utils;

import java.util.Optional;

public final class StringUtils {

    private StringUtils() {}

    public static int countMatchesIgnoreCase(final String str, final String sub) {
        return org.apache.commons.lang3.StringUtils.countMatches(str.toLowerCase(), sub.toLowerCase());
    }

    public static Optional<String> getWithin(final String str, final String init, final String end){
        final int initIndex = str.indexOf(init);
        if(initIndex >= 0){
            return str.contains(end)
                    ? Optional.of(str.substring(initIndex + init.length(), str.indexOf(end, initIndex)))
                    : Optional.of(str.substring(initIndex));
        }
        return Optional.empty();
    }
}
