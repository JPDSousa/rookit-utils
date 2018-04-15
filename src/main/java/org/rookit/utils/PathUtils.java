
package org.rookit.utils;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@SuppressWarnings("javadoc")
public class PathUtils {

    public static Path createFile(final Path path) {
        Preconditions.checkArgument(path != null, "The path cannot be null");
        try {
            final Optional<Path> parentOrNone = Optional.ofNullable(path.getParent());
            if (parentOrNone.isPresent()) {
                final Path parent = parentOrNone.get();
                if (Files.notExists(parent)) {
                    Files.createDirectories(parent);
                }
            }
            
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            return path;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PathUtils() {
    }

}
