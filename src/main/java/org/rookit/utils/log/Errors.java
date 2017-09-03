package org.rookit.utils.log;

import org.apache.logging.log4j.Logger;

@SuppressWarnings("javadoc")
public class Errors {
	
	public static void handleException(RuntimeException cause, Logger logger) {
		logger.error(cause.getMessage(), cause);
		throw cause;
	}
	
	public static void handleException(RuntimeException cause, Logs log) {
		handleException(cause, log.getLogger());
	}
}
