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
package org.rookit.utils.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@SuppressWarnings("javadoc")
public final class ConfigUtils {
	
	private static Gson gson;
	
	private static final Gson buildGson() {
		final GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		return builder.create();
	}
	
	private ConfigUtils() {}
	
	public static <T> T fromPath(Path path, Class<T> type) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		if(gson == null) {
			gson = buildGson();
		}
		return gson.fromJson(new FileReader(path.toFile()), type);
	}
	
	public static void toPath(Object config, Path path) throws IOException {
		if(gson == null) {
			gson = buildGson();
		}
		Files.write(path, gson.toJson(config).getBytes(), StandardOpenOption.WRITE);
	}
	
	public static <T> T getOrDefault(T get, T def) {
		return get != null ? get : def;
	}
	
	public static <T, E> T getOrDefault(E get, T def, Function<E, T> mapper) {
		return get != null ? mapper.apply(get) : def;
	}
	
	public static Path createFile(Path path) {
		try {
			if(!Files.exists(path.getParent())) {
				Files.createDirectories(path.getParent());
				if(!Files.exists(path)) {
					Files.createFile(path);
				}
			}
			return path;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
