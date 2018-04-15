
package org.rookit.utils.builder;

import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Generated;

@SuppressWarnings("javadoc")
public abstract class AbstractImmutableBuilder<B extends AbstractImmutableBuilder<B, P>, P> implements Builder<P> {

    private final B previousStage;

    protected AbstractImmutableBuilder(final B previousStage) {
        this.previousStage = previousStage;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof AbstractImmutableBuilder) {
            final AbstractImmutableBuilder<?, ?> that = (AbstractImmutableBuilder<?, ?>) object;
            return Objects.equals(this.previousStage, that.previousStage);
        }
        return false;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.previousStage);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("previousStage", this.previousStage)
                .toString();
    }

    protected abstract void applyStage(P builtObject);

    protected final Optional<B> getPreviousStage() {
        return Optional.ofNullable(this.previousStage);
    }

}
