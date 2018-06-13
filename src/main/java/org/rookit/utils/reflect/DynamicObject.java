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

package org.rookit.utils.reflect;

import com.google.common.base.MoreObjects;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@SuppressWarnings("javadoc")
public final class DynamicObject {

    public static DynamicObject fromObject(final Object object) {
        final Class<?> clazz = object.getClass();
        final Collection<MethodEnricher> methods = Arrays.stream(clazz.getMethods())
                .map(method -> new MethodEnricherImpl(object, method))
                .collect(Collectors.toList());

        return new DynamicObject(methods);
    }

    private final Collection<MethodEnricher> getters;

    private DynamicObject(final Collection<MethodEnricher> getters) {
        this.getters = getters;
    }

    public Collection<MethodEnricher> methods() {
        return Collections.unmodifiableCollection(this.getters);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("getters", this.getters)
                .toString();
    }
}
