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
