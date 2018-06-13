package org.rookit.utils.log.validator;

import org.apache.logging.log4j.Logger;

public interface Validator {
    ObjectValidator checkArgument();

    ObjectValidator checkState();

    Logger getLogger(Class<?> clazz);

    ExceptionHandler handleException();
}
