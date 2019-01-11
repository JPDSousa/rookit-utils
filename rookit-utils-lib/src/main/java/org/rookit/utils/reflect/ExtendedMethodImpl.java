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

import org.rookit.failsafe.Failsafe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

final class ExtendedMethodImpl implements ExtendedMethod {

    private final Failsafe failsafe;
    private final Object invoker;
    private final Method method;

    ExtendedMethodImpl(final Failsafe failsafe, final Object invoker, final Method method) {
        this.failsafe = failsafe;
        this.invoker = invoker;
        this.method = method;
    }

    @Override
    public String name() {
        return this.method.getName();
    }

    @Override
    public boolean isAccessible() {
        return this.method.isAccessible();
    }

    @Override
    public Class<?>[] arguments() {
        return this.method.getParameterTypes();
    }

    @Override
    public Class<?> returnType() {
        return this.method.getReturnType();
    }

    @Override
    public boolean returnsVoid() {
        final Class<?> returnType = returnType();
        return returnType.equals(void.class) || returnType.equals(Void.class);
    }

    Method getMethod() {
        return this.method;
    }

    @Override
    public Optional<Object> apply(final Object[] args) {
        final Method method = getMethod();
        final boolean isAccessible = method.isAccessible();
        try {
            method.setAccessible(true);
            return Optional.of(method.invoke(this.invoker, args));
        } catch (final IllegalAccessException | IllegalArgumentException e) {
            return this.failsafe.handleException().runtimeException(e);
        } catch (final InvocationTargetException e) {
            return this.failsafe.handleException().invocationTargetException(e);
        } finally {
            method.setAccessible(isAccessible);
        }
    }

    @Override
    public String toString() {
        return "ExtendedMethodImpl{" +
                "failsafe=" + this.failsafe +
                ", invoker=" + this.invoker +
                ", method=" + this.method +
                "}";
    }
}
