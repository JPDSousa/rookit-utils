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
package org.rookit.utils.registry;

import com.google.inject.Inject;
import io.reactivex.Single;
import io.reactivex.functions.Function;

final class BaseRegistriesImpl implements BaseRegistries {

    @Inject
    private BaseRegistriesImpl() {}

    @Override
    public <K, I, O> Registry<K, O> mapValueRegistry(final Registry<K, I> upstream, final Function<I, Single<O>> mapper) {
        return new MapValueRegistry<>(upstream, mapper);
    }

    @Override
    public <K1, K2, V> Registry<K2, V> mapKeyRegistry(final Registry<K1, V> upstream, final Function<K2, K1> mapper) {
        return new MapKeyRegistry<>(upstream, mapper);
    }
}
