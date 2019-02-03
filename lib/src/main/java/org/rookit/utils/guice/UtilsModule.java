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

import com.google.common.io.Closer;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.utils.collection.MapUtils;
import org.rookit.utils.collection.MapUtilsImpl;
import org.rookit.utils.io.DummyInputStream;
import org.rookit.utils.io.DummyOutputStream;
import org.rookit.utils.io.DummyReader;
import org.rookit.utils.io.DummyWriter;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.optional.OptionalFactoryImpl;
import org.rookit.utils.optional.OptionalUtils;
import org.rookit.utils.optional.OptionalUtilsImpl;
import org.rookit.utils.primitive.ShortUtils;
import org.rookit.utils.primitive.ShortUtilsImpl;
import org.rookit.utils.primitive.VoidUtils;
import org.rookit.utils.primitive.VoidUtilsImpl;
import org.rookit.utils.reflect.ReflectModule;
import org.rookit.utils.string.StringUtils;
import org.rookit.utils.string.StringUtilsImpl;
import org.rookit.utils.supplier.SupplierUtils;
import org.rookit.utils.supplier.SupplierUtilsImpl;
import org.rookit.utils.reflect.BaseExtendedClassFactory;
import org.rookit.utils.reflect.ExtendedClassFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public final class UtilsModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new UtilsModule(),
            ReflectModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private UtilsModule() {}

    @Override
    protected void configure() {
        bindDummy();

        bind(OptionalUtils.class).toInstance(OptionalUtilsImpl.create());
        bind(VoidUtils.class).toInstance(VoidUtilsImpl.create());
        bind(SupplierUtils.class).toInstance(SupplierUtilsImpl.create());
        bind(MapUtils.class).to(MapUtilsImpl.class).in(Singleton.class);
        bind(ShortUtils.class).to(ShortUtilsImpl.class).in(Singleton.class);
        bind(Closer.class).toProvider(CloserProvider.class).in(Singleton.class);
        //bind(PrintUtils.class).to(PrintUtilsImpl.class).in(Singleton.class);
        bind(OptionalFactory.class).to(OptionalFactoryImpl.class).in(Singleton.class);
        bind(StringUtils.class).to(StringUtilsImpl.class).in(Singleton.class);
        bind(ExtendedClassFactory.class).to(BaseExtendedClassFactory.class).in(Singleton.class);
    }

    private void bindDummy() {
        bind(InputStream.class).annotatedWith(Dummy.class).toInstance(DummyInputStream.get());
        bind(OutputStream.class).annotatedWith(Dummy.class).toInstance(DummyOutputStream.get());
        bind(Reader.class).annotatedWith(Dummy.class).toInstance(DummyReader.get());
        bind(Writer.class).annotatedWith(Dummy.class).toInstance(DummyWriter.get());
    }
}
