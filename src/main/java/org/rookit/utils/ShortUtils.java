package org.rookit.utils;

@SuppressWarnings("javadoc")
public abstract class ShortUtils {

    public static boolean isCastable(final int value) {
        return value <= Short.MAX_VALUE && value >= Short.MIN_VALUE;
    }

    public static boolean isCastable(final String value) {
        try {
            Short.valueOf(value);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    private ShortUtils() {
    }

}
