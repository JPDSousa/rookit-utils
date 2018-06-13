package org.rookit.utils.optional;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@SuppressWarnings("BooleanParameter")
public interface OptionalBoolean {

    @SuppressWarnings("ConstantDeclaredInInterface")
    OptionalBoolean EMPTY = new EmptyBoolean();

    static OptionalBoolean of(final boolean value) {
        return new SomeBoolean(value);
    }

    static OptionalBoolean empty() {
        return EMPTY;
    }

    @SuppressWarnings("UnnecessaryUnboxing")
    static OptionalBoolean ofNullable(final Boolean value) {
        return Objects.isNull(value)
                ? empty()
                : OptionalBoolean.of(value.booleanValue());
    }

    boolean isPresent();

    boolean getAsBoolean();

    default boolean orElse(final boolean other) {
        return isPresent() ? getAsBoolean() : other;
    }

    default boolean orElse(final BooleanSupplier supplier) {
        return isPresent() ? getAsBoolean() : supplier.getAsBoolean();
    }

    default <X extends Throwable> boolean orElseThrow(final Supplier<X> exceptionSupplier) throws X {
        if (isPresent()) {
            return getAsBoolean();
        }
        throw exceptionSupplier.get();
    }

    OptionalBoolean ifPresent(BooleanConsumer consumer);
}
