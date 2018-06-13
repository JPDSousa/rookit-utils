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

package org.rookit.utils.log.validator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.rookit.utils.OptionalUtils;
import org.rookit.utils.log.Errors;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("javadoc")
public final class ObjectValidator {

    private final Function<String, RuntimeException> exceptionGenerator;
    private final Logger logger;

    public ObjectValidator(final Logger logger, final Function<String, RuntimeException> exceptionGenerator) {
        this.logger = logger;
        this.exceptionGenerator = exceptionGenerator;
    }

    public <T> T checkSingleEntryMap(final Map<?, ?> object, final String name) {
        isNotNull(object, name);
        return is(object.size() == 1, "The map %s is not a singleton", name);
    }

    public <T, I> T doesNotContain(final Optional<I> objectOrNone, final I expected, final String objectOrNoneName) {
        return is(OptionalUtils.contains(objectOrNone, expected), "The %s does not contain %s", objectOrNoneName,
                expected);
    }

    public <T> T is(final boolean condition, final String message, final Object... args) {
        return check(condition, String.format(message, args));
    }

    public <T, R extends Comparable<R>> T isBetween(final R object,
            final Range<R> range,
            final String objectName) {
        return is(range.contains(object), "Range for %s is %s but value is %s",
                objectName, range, object);
    }

    public <T> T isEmpty(final Collection<?> object, final String name) {
        isNotNull(object, name);
        return is(object.isEmpty(), "The %s must be empty", name);
    }

    public <T> T isEquals(final Object expected,
            final Object actual,
            final String name) {
        isNotNull(actual, "actual class");
        isNotNull(expected, "expected class");
        return is(Objects.equals(expected, actual), "%s of type %s is not the same class as %s",
                actual, name, expected);
    }

    public <T> T isGreaterThanOrEqualTo(final Collection<?> collection,
            final int minimumSize,
            final String collectionName) {
        isNotNull(collection, collectionName);
        return is(collection.size() >= minimumSize, "%s must contain at least %d items.",
                collectionName,
                Integer.valueOf(minimumSize));
    }

    public <T, E> T isNotAllThereIs(final Collection<E> subset,
            final Collection<E> collection,
            final String collectionName) {
        isNotNull(subset, "subset");
        isNotNull(collection, collectionName);
        final boolean hasSameContent = CollectionUtils.isEqualCollection(Sets.newHashSet(subset),
                Sets.newHashSet(collection));

        return is(!hasSameContent, "The subset represents all elements of collection %s", collectionName);
    }

    public <T, K> T isNotContainedIn(final K key,
            final Map<K, ?> container,
            final String keyName,
            final String containerName) {
        isNotNull(key, keyName);
        isNotNull(container, containerName);
        return is(!container.containsKey(key), "%s %s already exists in %s",
                keyName, key, containerName);
    }

    public <T> T isNotContainedIn(final Object object,
            final Collection<?> collection,
            final String objectName) {
        isNotNull(object, objectName);
        isNotNull(collection, "collection");
        return is(!collection.contains(object), "The collection cannot contain %s", objectName);
    }

    public <T, E> T isNotEmpty(final byte[] array, final String name) {
        isNotNull(array, name);
        return is(array.length > 0, "The %s cannot be empty", name);
    }

    public <T> T isNotEmpty(final Collection<?> object, final String name) {
        isNotNull(object, name);
        return is(!object.isEmpty(), "The %s cannot be empty", name);
    }

    public <T> T isNotEmpty(final String object, final String name) {
        isNotNull(object, name);
        return is(StringUtils.isNotBlank(object), "The %s must not be empty or blank", name);
    }

    public <T> T isNotEquals(final double value, final double unexpected, final String valueName) {
        return is(value != unexpected, "The %s cannot be equal to %d", valueName, Double.valueOf(unexpected));
    }

    public <T> T isNotEquals(final Object object, final Object unexpected, final String objectName) {
        return is(!Objects.equals(object, unexpected), "The %s cannot be equal to %s", objectName, unexpected);
    }

    public <T> T isNotIntersecting(final Iterable<T> source,
            final Iterable<T> target,
            final String sourceName,
            final String targetName) {
        isNotNull(source, sourceName);
        isNotNull(target, targetName);
        final Collection<T> intersection = CollectionUtils.intersection(source, target);

        return is(intersection.isEmpty(), "%s is contained both in %s and %s", intersection.toString(), sourceName,
                targetName);
    }

    public <T> T isNotNull(final Object object, final String name) {
        return is(Objects.nonNull(object), "The %s must not be null", name);
    }

    public <T> T isNull(final Object object, final String name) {
        return is(Objects.isNull(object), "The %s must be null", name);
    }

    public <T> T isPositive(final int object, final String name) {
        return is(object > 0, "%s must be greater than 0", name);
    }

    public <T> T isPositive(final long object, final String name) {
        return is(object > 0L, "%s must be greater than 0", name);
    }

    public <T> T isSum(final Collection<Float> values, final float expected) {
        final double sum = values.stream()
                .mapToDouble(Float::doubleValue)
                .sum();

        return is(sum == expected, "The sum of all values must be %s", expected);
    }

    private <T> T check(final boolean condition, final String message) {
        if (!condition) {
            return Errors.throwException(this.exceptionGenerator.apply(message), this.logger);
        }
        return null;
    }

    public <T> T isNotFuture(final ChronoLocalDate last, final String name) {
        isNotNull(last, name);
        return is(!last.isAfter(LocalDate.now()), "%s of variable %s must be in the past", last, name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("exceptionGenerator", this.exceptionGenerator)
                .add("logger", this.logger)
                .toString();
    }

    public <T> T isNotNegative(final long count, final String name) {
        return is(count >= 0, "%s of variable cannot be negative.", count, name);
    }
}
