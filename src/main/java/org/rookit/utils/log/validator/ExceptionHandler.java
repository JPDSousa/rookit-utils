
package org.rookit.utils.log.validator;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.rookit.utils.log.Errors;

@SuppressWarnings("javadoc")
public class ExceptionHandler {

    private final Logger logger;

    ExceptionHandler(final Logger logger) {
        super();
        this.logger = logger;
    }

    public <T> T inputOutputException(final IOException cause) {
        return Errors.throwException(new RuntimeException(cause), this.logger);
    }

    public <T> T runtimeException(final String message) {
        return Errors.throwException(new RuntimeException(message), this.logger);
    }

    public <T> T runtimeException(final String message, final Throwable cause) {
        return Errors.throwException(new RuntimeException(message, cause), this.logger);
    }

    public <T> T runtimeException(final Throwable cause) {
        return Errors.throwException(new RuntimeException(cause), this.logger);
    }

    public <T> T unsupportedOperation(final String message) {
        return Errors.throwException(new UnsupportedOperationException(message), this.logger);
    }
}
