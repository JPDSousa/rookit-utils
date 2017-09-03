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
import java.util.Collection;
import java.util.Map;

import org.apache.logging.log4j.Logger;

@SuppressWarnings("javadoc")
public class Validator {
	
	protected final Logger logger;
	
	public Validator(Logs log) {
		this(log.getLogger());
	}
	
	public Validator(Logger logger) {
		this.logger = logger;
	}
	
	public void checkArgumentNotNull(Object argument, String message) {
		if(argument == null) {
			Errors.handleException(new IllegalArgumentException(message), logger);
		}
	}
	
	public void checkArgumentStringNotEmpty(String argument, String message) {
		checkArgumentNotNull(argument, message);
		if(argument.isEmpty()) {
			Errors.handleException(new IllegalArgumentException(message), logger);
		}
	}
	
	public void checkArgumentNonEmptyCollection(Collection<?> argument, String message) {
		checkArgumentNotNull(argument, message);
		if(argument.isEmpty()) {
			Errors.handleException(new IllegalArgumentException(message), logger);
		}
	}
	
	public void checkArgumentEmptyCollection(Collection<?> argument, String message) {
		checkArgumentNotNull(argument, message);
		if(!argument.isEmpty()) {
			Errors.handleException(new IllegalArgumentException(message), logger);
		}
	}
	
	public void checkArgumentNotContains(Object argument, Collection<?> collection, String message) {
		checkArgumentNotNull(argument, message);
		if(collection.contains(argument)) {
			Errors.handleException(new IllegalArgumentException(message), logger);
		}
	}
	
	public void checkArgumentPositive(Long argument, String message) {
		checkArgumentNotNull(argument, message);
		if(argument < 0) {
			Errors.handleException(new IllegalArgumentException(message), logger);
		}
	}
	
	public void checkArgumentPositive(Integer argument, String message) {
		checkArgumentNotNull(argument, message);
		if(argument < 0) {
			Errors.handleException(new IllegalArgumentException(message), logger);
		}
	}
	
	public void checkArgumentBetween(int argument, int min, int max, String message) {
		checkArgumentBetween(argument, min, max, message, message);
	}
	
	public void checkArgumentBetween(int argument, int min, int max, String minMessage, String maxMessage) {
		if(argument < min) {
			Errors.handleException(new IllegalArgumentException(minMessage), logger);
		}
		else if(argument > max) {
			Errors.handleException(new IllegalArgumentException(maxMessage), logger);
		}
	}
	
	public void checkSingleEntryMap(Map<?, ?> map, String message) {
		if(map.size() != 1) {
			Errors.handleException(new RuntimeException(message), logger);
		}
	}
	
	public void handleIOException(IOException cause) {
		Errors.handleException(new RuntimeException(cause), logger);
	}
	
	public void info(String message) {
		logger.info(message);
	}
}
