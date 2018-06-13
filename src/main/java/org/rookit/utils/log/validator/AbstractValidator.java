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

import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.Logger;
import org.rookit.utils.log.LogManager;

@SuppressWarnings("javadoc")
public abstract class AbstractValidator implements Validator {

    private final LogManager category;
    private final Logger categoryLogger;
    private final ExceptionHandler exceptionHandler;

    private final ObjectValidator stateValidator;
    private final ObjectValidator argumentValidator;

    protected AbstractValidator(final LogManager category) {
        final Class<? extends AbstractValidator> thisClass = getClass();

        this.category = category;
        this.categoryLogger = category.getLogger(thisClass);
        this.stateValidator = new ObjectValidator(this.categoryLogger, IllegalStateException::new);
        this.argumentValidator = new ObjectValidator(this.categoryLogger, IllegalArgumentException::new);
        this.exceptionHandler = new ExceptionHandler(this.categoryLogger);
    }

    @Override
    public ObjectValidator checkArgument() {
        return this.argumentValidator;
    }

    @Override
    public ObjectValidator checkState() {
        return this.stateValidator;
    }

    @Override
    public Logger getLogger(final Class<?> clazz) {
        return this.category.getLogger(clazz);
    }

    @Override
    public ExceptionHandler handleException() {
        return this.exceptionHandler;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("category", this.category)
                .add("categoryLogger", this.categoryLogger)
                .add("exceptionHandler", this.exceptionHandler)
                .add("stateValidator", this.stateValidator)
                .add("argumentValidator", this.argumentValidator)
                .toString();
    }
}