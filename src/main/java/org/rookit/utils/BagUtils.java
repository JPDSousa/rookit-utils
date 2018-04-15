package org.rookit.utils;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

@SuppressWarnings("javadoc")
public abstract class BagUtils {

    public static <T> Optional<T> getHighestCount(final Bag<T> bag) {
        int max = 0;
        T maxElement = null;

        for (final T element : bag) {
            final int count = bag.getCount(element);
            if (Objects.isNull(maxElement) || count > max) {
                maxElement = element;
                max = count;
            }
        }

        return Optional.ofNullable(maxElement);
    }

    public static <T> Bag<T> newHashBag(final T initial) {
        return new HashBag<>(Collections.singleton(initial));
    }

    private BagUtils() {
    }
}
