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
package org.rookit.utils.guice;

import com.google.common.base.Suppliers;
import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeConverterBinding;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class LazyInjector implements Injector {

    public static Injector create(final Iterable<Module> modules) {
        return new LazyInjector(modules);
    }

    private final Supplier<Injector> lazySupplier;

    private LazyInjector(final Iterable<Module> modules) {
        this.lazySupplier = Suppliers.memoize(() -> Guice.createInjector(modules));
    }

    @Override
    public void injectMembers(final Object instance) {
        this.lazySupplier.get().injectMembers(instance);
    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(final TypeLiteral<T> typeLiteral) {
        return this.lazySupplier.get().getMembersInjector(typeLiteral);
    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(final Class<T> type) {
        return this.lazySupplier.get().getMembersInjector(type);
    }

    @Override
    public Map<Key<?>, Binding<?>> getBindings() {
        return this.lazySupplier.get().getBindings();
    }

    @Override
    public Map<Key<?>, Binding<?>> getAllBindings() {
        return this.lazySupplier.get().getAllBindings();
    }

    @Override
    public <T> Binding<T> getBinding(final Key<T> key) {
        return this.lazySupplier.get().getBinding(key);
    }

    @Override
    public <T> Binding<T> getBinding(final Class<T> type) {
        return this.lazySupplier.get().getBinding(type);
    }

    @Override
    public <T> Binding<T> getExistingBinding(final Key<T> key) {
        return this.lazySupplier.get().getExistingBinding(key);
    }

    @Override
    public <T> List<Binding<T>> findBindingsByType(final TypeLiteral<T> type) {
        return this.lazySupplier.get().findBindingsByType(type);
    }

    @Override
    public <T> Provider<T> getProvider(final Key<T> key) {
        return this.lazySupplier.get().getProvider(key);
    }

    @Override
    public <T> Provider<T> getProvider(final Class<T> type) {
        return this.lazySupplier.get().getProvider(type);
    }

    @Override
    public <T> T getInstance(final Key<T> key) {
        return this.lazySupplier.get().getInstance(key);
    }

    @Override
    public <T> T getInstance(final Class<T> type) {
        return this.lazySupplier.get().getInstance(type);
    }

    @Override
    public Injector getParent() {
        return this.lazySupplier.get().getParent();
    }

    @Override
    public Injector createChildInjector(final Iterable<? extends Module> modules) {
        return this.lazySupplier.get().createChildInjector(modules);
    }

    @Override
    public Injector createChildInjector(final Module... modules) {
        return this.lazySupplier.get().createChildInjector(modules);
    }

    @Override
    public Map<Class<? extends Annotation>, Scope> getScopeBindings() {
        return this.lazySupplier.get().getScopeBindings();
    }

    @Override
    public Set<TypeConverterBinding> getTypeConverterBindings() {
        return this.lazySupplier.get().getTypeConverterBindings();
    }

    @Override
    public String toString() {
        return "LazyInjector{" +
                "lazySupplier=" + this.lazySupplier +
                "}";
    }
}
