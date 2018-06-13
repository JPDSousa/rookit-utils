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
package org.rookit.utils.log.validator;

import org.apache.logging.log4j.Logger;
import org.rookit.utils.log.Errors;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
        if (cause instanceof RuntimeException) {
            return Errors.throwException((RuntimeException) cause, this.logger);
        }
        return Errors.throwException(new RuntimeException(cause), this.logger);
    }

    public <T> T invocationTargetException(final InvocationTargetException cause) {
        return runtimeException(cause.getCause());
    }

    public <T> T unsupportedOperation(final String message, final Object... args) {
        return Errors.throwException(new UnsupportedOperationException(String.format(message, args)), this.logger);
    }

}
