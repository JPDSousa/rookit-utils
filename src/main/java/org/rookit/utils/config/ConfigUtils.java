/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
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

package org.rookit.utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;

@SuppressWarnings("javadoc")
public final class ConfigUtils {

    private static Gson gson;

    public static <T> T fromPath(final Path path, final Class<T> type) {
        if (gson == null) {
            gson = buildGson();
        }
        try (final FileReader reader = new FileReader(path.toFile())) {
            return gson.fromJson(reader, type);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T, E> T getOrDefault(final E get, final T def, final Function<E, T> mapper) {
        return get != null ? mapper.apply(get) : def;
    }

    public static <T> T getOrDefault(final T get, final T def) {
        return get != null ? get : def;
    }

    public static void toPath(final Object config, final Path path) throws IOException {
        if (gson == null) {
            gson = buildGson();
        }
        Files.write(path, gson.toJson(config).getBytes(), StandardOpenOption.WRITE);
    }

    private static final Gson buildGson() {
        final GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        return builder.create();
    }

    private ConfigUtils() {
    }

}
