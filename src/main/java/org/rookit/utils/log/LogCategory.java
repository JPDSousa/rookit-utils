package org.rookit.utils.log;

import java.nio.file.Path;

@SuppressWarnings("javadoc")
public interface LogCategory {

	Path getPath();
	
	String getName();
	
	Package getPackage();

}
