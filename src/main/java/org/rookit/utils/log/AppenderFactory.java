package org.rookit.utils.log;

import java.nio.file.Path;

import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.ConsoleAppender.Target;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;

final class AppenderFactory {

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
}
