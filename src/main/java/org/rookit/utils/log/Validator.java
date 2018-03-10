/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
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

import java.io.IOException;

import org.apache.logging.log4j.Logger;


@SuppressWarnings("javadoc")
public abstract class Validator {
	
	private final LogManager category;
	private final Logger categoryLogger;
	
	private final ObjectValidator stateValidator;
	private final ObjectValidator argumentValidator;
	
	public Validator(final LogManager category) {
		this.category = category;
		this.categoryLogger = category.getLogger(getClass());
		this.stateValidator = new ObjectValidator(this.categoryLogger, IllegalStateException::new);
		this.argumentValidator = new ObjectValidator(this.categoryLogger, IllegalArgumentException::new);
	}
	
	public final ObjectValidator checkState() {
		return this.stateValidator;
	}
	
	public final ObjectValidator checkArgument() {
		return this.argumentValidator;
	}
	
	public <T> T handleIOException(final IOException cause) {
		return Errors.throwException(new RuntimeException(cause), categoryLogger);
	}
	
	public <T> T invalidOperation(final String message) {
		return Errors.throwException(new UnsupportedOperationException(message), categoryLogger);
		
	}
	
	public <T> T runtimeException(final String message) {
		return Errors.throwException(new RuntimeException(message), categoryLogger);
	}
	
	public <T> T runtimeException(final Throwable cause) {
		return Errors.throwException(new RuntimeException(cause), categoryLogger);
	}
	
	public <T> T runtimeException(final String message, final Throwable cause) {
		return Errors.throwException(new RuntimeException(message, cause), categoryLogger);
	}

	public Logger getLogger(final Class<?> clazz) {
		return category.getLogger(clazz);
	}
	
}
