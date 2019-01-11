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

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeAll;
import org.rookit.test.junit.InjectorTest;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.optional.OptionalFactoryImpl;
import org.rookit.utils.optional.OptionalUtils;
import org.rookit.utils.optional.OptionalUtilsImpl;
import org.rookit.utils.primitive.ShortUtils;
import org.rookit.utils.primitive.ShortUtilsImpl;
import org.rookit.utils.string.StringUtils;
import org.rookit.utils.string.StringUtilsImpl;

import java.util.Map;

@SuppressWarnings("JUnitTestCaseWithNoTests") // yes it does, in InjectorTest
public final class UtilsModuleTest implements InjectorTest {

    private static Injector injector;

    @BeforeAll
    public static void createInjector() {
        injector = Guice.createInjector(UtilsModule.getModule());
    }

    @Override
    public Map<Class<?>, Class<?>> expectedBindings() {
        return ImmutableMap.of(
                OptionalFactory.class, OptionalFactoryImpl.class,
                ShortUtils.class, ShortUtilsImpl.class,
                StringUtils.class, StringUtilsImpl.class,
                OptionalUtils.class, OptionalUtilsImpl.class
        );
    }

    @Override
    public Injector injector() {
        return injector;
    }

}
