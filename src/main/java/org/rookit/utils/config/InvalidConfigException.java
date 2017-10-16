package org.rookit.utils.config;

@SuppressWarnings("javadoc")
public class InvalidConfigException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidConfigException(Throwable e) {
		super(e);
	}
	
	public InvalidConfigException(String message) {
		super(message);
	}
	
	public InvalidConfigException(String fieldName, String value) {
		super("Invalid configuration " + value + " for field " + fieldName);
	}

}
