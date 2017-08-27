package resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("javadoc")
public class ResourceManager {

	private static final Path LOGS = Paths.get("logs");

	public static Path logPath() {
		ensureExists(LOGS, true);
		return LOGS;
	}

	private static void ensureExists(Path path, boolean isDirectory) {
		final boolean exists = !Files.exists(path);
		try {
			if(exists && isDirectory) {
				Files.createDirectories(path);
			}
			else if(exists) {
				Files.createFile(path);
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
