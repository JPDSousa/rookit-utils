package org.rookit.utils.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.ConsoleAppender.Target;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.rookit.utils.resource.ResourceManager;

@SuppressWarnings("javadoc")
public enum Logs {

	DATABASE,
	PARSING,
	FILE_SYSTEM,
	CONNECTION,
	CORE,
	CONFIG;

	static {
		final LoggerContext context = (LoggerContext) LogManager.getContext();
		final Configuration config = context.getConfiguration();
		final Appender consoleAppender = createConsoleAppender(config);

		config.addAppender(consoleAppender);
		for(Logs log : values()) {
			log.setLoggers(config, consoleAppender);
		}
		context.updateLoggers(config);
	}

	private static ConsoleAppender createConsoleAppender(Configuration config) {
		final PatternLayout layout = PatternLayout.createDefaultLayout(config);
		final ConsoleAppender appender = ConsoleAppender.newBuilder()
				.setConfiguration(config)
				.withLayout(layout)
				.setTarget(Target.SYSTEM_ERR)
				.withIgnoreExceptions(true)
				.withName("Console")
				.build();

		appender.start();
		return appender;
	}

	private Path getPath() {
		final Path path = ResourceManager.logPath().resolve(name());
		createIfNotExists(path);
		return path.resolve(getCurrentFileName());
	}

	private String getCurrentFileName() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return LocalDate.now().format(formatter) + ".log";
	}

	private void createIfNotExists(Path path) {
		try {
			if(Files.notExists(path)) {
				Files.createDirectories(path);
			}
		} catch (IOException e) {
			throw new AssertionError("Could not create directory: " + path, e);
		}
	}

	private FileAppender createFileAppender(Configuration config) {
		final PatternLayout layout = PatternLayout.createDefaultLayout(config);
		final FileAppender appender = FileAppender.newBuilder()
				.withFileName(getPath().toString())
				.setConfiguration(config)
				.withAppend(true)
				.withLayout(layout)
				.withName(name())
				.build();

		appender.start();
		return appender;
	}

	private void setLoggers(Configuration config, Appender consoleAppender) {
		final FileAppender fileAppender = createFileAppender(config);
		final LoggerConfig config1 = new LoggerConfig(name(), Level.WARN, false);
		final LoggerConfig config2 = new LoggerConfig(name(), Level.INFO, false);
		config1.addAppender(fileAppender, Level.WARN, null);
		config1.addAppender(consoleAppender, Level.WARN, null);
		config2.addAppender(consoleAppender, Level.INFO, null);

		config.addAppender(fileAppender);
		config.addLogger(name(), config1);
		config.addLogger(name(), config2);
	}

	public Logger getLogger() {
		return LogManager.getContext().getLogger(name());
	}

}
