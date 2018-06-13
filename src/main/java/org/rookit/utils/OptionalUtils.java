/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
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

package org.rookit.utils;

import org.rookit.utils.function.ToShortFunction;
import org.rookit.utils.optional.OptionalShort;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.ToIntFunction;

@SuppressWarnings({"javadoc", "OptionalUsedAsFieldOrParameterType"})
public final class OptionalUtils {

    public static boolean isAllPresent(final Optional<?>... optionals) {
        return Arrays.stream(optionals)
                .allMatch(Optional::isPresent);
    }

    public static <T> Optional<T> orElse(final Optional<T> optional, final Optional<T> fallback) {
        return optional.isPresent() ? optional : fallback;
    }

    public static <T extends Comparable<T>> int compare(final Optional<T> left, final Optional<T> right) {
        if (isAllPresent(left, right)) {
            return left.get().compareTo(right.get());
        }
        if (!left.isPresent() && !right.isPresent()) {
            return 0;
        }
        if (left.isPresent()) {
            return 1;
        }
        return -1;
    }

    public static <T> boolean contains(final Optional<T> optional, final T expected) {
        return optional.isPresent()
                && Objects.equals(optional.get(), expected);
    }

    private OptionalUtils() {
    }

    public static <T> OptionalInt mapToInt(final Optional<T> optional, final ToIntFunction<T> numberExtractor) {
        return optional.map(t -> OptionalInt.of(numberExtractor.applyAsInt(t)))
                .orElseGet(OptionalInt::empty);
    }

    public static <T> OptionalShort mapToShort(final Optional<T> optional, final ToShortFunction<T> numberExtractor) {
        return optional.map(t -> OptionalShort.of(numberExtractor.apply(t)))
                .orElseGet(OptionalShort::empty);
    }
}
