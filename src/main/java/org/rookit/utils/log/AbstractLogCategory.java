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
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.rookit.utils.resource.Resources;

@SuppressWarnings("javadoc")
public abstract class AbstractLogCategory implements LogCategory {
	
	@Override
	public final Path getPath() {
		final Path path = Resources.logPath().resolve(getName());
		createDirectoryIfNotExists(path);
		return path.resolve(getCurrentFileName());
	}
	
	private String getCurrentFileName() {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return LocalDate.now().format(formatter) + ".log";
	}
	
	private static void createDirectoryIfNotExists(final Path path) {
		try {
			if(Files.notExists(path)) {
				Files.createDirectories(path);
			}
		} catch (IOException e) {
			throw new AssertionError("Could not create directory: " + path, e);
		}
	}
	
}
