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

package org.rookit.utils.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("javadoc")
public class Resources {

    private static final Path LOGS = Paths.get("logs");

    public static final Path RESOURCES_MAIN = Paths.get("src", "main", "resources");
    public static final Path RESOURCES_TEST = Paths.get("src", "test", "resources");

    public static Path logPath() {
        ensureExists(LOGS, true);
        return LOGS;
    }

    private static void ensureExists(final Path path, final boolean isDirectory) {
        final boolean exists = !Files.exists(path);
        try {
            if (exists && isDirectory) {
                Files.createDirectories(path);
            } else if (exists) {
                Files.createFile(path);
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
