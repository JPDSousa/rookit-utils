package org.rookit.utils.log;

import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender.Target;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.LevelRangeFilter;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

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
		final Configuration config = context.getConfiguration();
		config.getAppenders().clear();
		
		final String name = category.getName();
		final String packageName = category.getPackage().getName();
		
		final Appender fileAppender = AppenderFactory.createFileAppender(config, category.getPath(), name + "_file");
		final Appender outAppender = AppenderFactory.createConsoleAppender(config, name + "_std", Target.SYSTEM_OUT);
		final Appender errAppender = AppenderFactory.createConsoleAppender(config, name + "_err", Target.SYSTEM_ERR);
		final LoggerConfig loggerConfig = new LoggerConfig(name, Level.ALL, false);
		// Warns go to
		loggerConfig.addAppender(fileAppender, Level.WARN, null);
		loggerConfig.addAppender(errAppender, Level.ALL, null);
		// Info goes to
		loggerConfig.addAppender(outAppender, Level.INFO, LevelRangeFilter.createFilter(Level.INFO, Level.INFO, null, null));

		config.addAppender(fileAppender);
		config.addAppender(outAppender);
		config.addAppender(errAppender);
		config.addLogger(packageName, loggerConfig);
	}
	
	public Logger getLogger(final Class<?> clazz) {
		return context.getLogger(clazz.getName());
	}
	

}
