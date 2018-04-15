
package org.rookit.utils.builder;

import com.google.common.base.MoreObjects;

import java.util.Objects;

import javax.annotation.Generated;

import org.rookit.utils.log.validator.Validator;

@SuppressWarnings("javadoc")
public abstract class AbstractIdempotentBuilder<P> implements Builder<P> {

    private final Validator validator;

    private P builtObject;

    protected AbstractIdempotentBuilder(final Validator validator) {
        this.validator = validator;
    }

    @Override
    public final synchronized P build() {
        if (Objects.isNull(this.builtObject)) {
            this.builtObject = validateAndBuild();
        }
        return this.builtObject;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof AbstractIdempotentBuilder<?>) {
            final AbstractIdempotentBuilder<?> that = (AbstractIdempotentBuilder<?>) object;
            return Objects.equals(this.builtObject, that.builtObject);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.builtObject);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("builtObject", this.builtObject)
                .toString();
    }

    protected final <T> T checkNotBuilt() {
        return this.validator.checkState()
                .is(Objects.isNull(this.builtObject),
                        "This builder is already built and as such, cannot be used.");
    }

    protected abstract P validateAndBuild();

}
