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
package org.rookit.utils.log;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender.Target;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

@SuppressWarnings("javadoc")
public final class LogManager {

    private static final Map<String, LogManager> CATEGORY_CACHE = Maps.newConcurrentMap();

    public static LogManager create(final LogCategory category) {
        Preconditions.checkNotNull(category);
        LogManager manager = CATEGORY_CACHE.get(category.getName());
        if (Objects.isNull(manager)) {
            manager = new LogManager(category);
            CATEGORY_CACHE.put(category.getName(), manager);
        }
        return manager;
    }

    public static void reset() {
        CATEGORY_CACHE.clear();
    }

    private final LoggerContext context;

    private LogManager(final LogCategory category) {
        this.context = new org.apache.logging.log4j.core.LoggerContext("RookitLogger");
        final Configuration config = this.context.getConfiguration();
        config.getAppenders().clear();

        final String name = category.getName();
        final String packageName = category.getPackage().getName();

        final Appender fileAppender = AppenderFactory.createFileAppender(config, category.getPath(), name + "_file");
        final Appender outAppender = AppenderFactory.createConsoleAppender(config, name + "_std", Target.SYSTEM_OUT);
        final Appender errAppender = AppenderFactory.createConsoleAppender(config, name + "_err", Target.SYSTEM_ERR);
        final LoggerConfig loggerConfig = new LoggerConfig(name, Level.ALL, false);
        // Warns go to
        loggerConfig.addAppender(fileAppender, Level.ALL, null);
        loggerConfig.addAppender(errAppender, Level.WARN, null);
        // Info goes to
        // loggerConfig.addAppender(outAppender, Level.WARN,
        // LevelRangeFilter.createFilter(Level.OFF, Level.WARN, null, null));

        config.addAppender(fileAppender);
        config.addAppender(outAppender);
        config.addAppender(errAppender);
        config.addLogger(packageName, loggerConfig);
    }

    public Logger getLogger(final Class<?> clazz) {
        return this.context.getLogger(clazz.getName());
    }

}
