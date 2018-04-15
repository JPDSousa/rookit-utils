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

package org.rookit.utils.log;

import java.nio.file.Path;

import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.ConsoleAppender.Target;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;

final class AppenderFactory {

    static ConsoleAppender createConsoleAppender(final Configuration config, final String name, final Target target) {
        final PatternLayout layout = PatternLayout.newBuilder()
                .withPattern(DefaultConfiguration.DEFAULT_PATTERN)
                .withConfiguration(config)
                .build();
        final ConsoleAppender appender = ConsoleAppender.newBuilder()
                .setConfiguration(config)
                .withLayout(layout)
                .setTarget(target)
                .withIgnoreExceptions(true)
                .withName(name)
                .build();

        appender.start();
        return appender;
    }

    static FileAppender createFileAppender(final Configuration config, final Path path, final String name) {
        final PatternLayout layout = PatternLayout.newBuilder()
                .withPattern(DefaultConfiguration.DEFAULT_PATTERN)
                .withConfiguration(config)
                .build();
        final FileAppender appender = FileAppender.newBuilder()
                .withFileName(path.toString())
                .setConfiguration(config)
                .withAppend(true)
                .withLayout(layout)
                .withName(name)
                .build();

        appender.start();
        return appender;
    }
}
