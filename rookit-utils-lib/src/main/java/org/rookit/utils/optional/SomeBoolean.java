package org.rookit.utils.optional;

import com.google.common.base.MoreObjects;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import org.immutables.value.internal.$processor$.meta.$ValueMirrors;

@$ValueMirrors.Immutable
class SomeBoolean implements OptionalBoolean {

    private final boolean value;

    SomeBoolean(final boolean value) {
        this.value = value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public boolean getAsBoolean() {
        return this.value;
    }

    @Override
    public OptionalBoolean ifPresent(final BooleanConsumer consumer) {
        consumer.accept(this.value);
        return this;
    }

    @Override
    public String toString() {
        //noinspection DuplicateStringLiteralInspection
        return MoreObjects.toStringHelper(this)
                .add("value", this.value)
                .toString();
    }
}
