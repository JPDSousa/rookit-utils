
package org.rookit.utils;

import java.util.Map;
import java.util.Optional;

@SuppressWarnings("javadoc")
public class MapUtils {

    public static <A> Optional<A> get(final Map<String, Object> data,
            final String name,
            final Class<? extends A> clazz) {
        return Optional.ofNullable(data.get(name))
                .filter(clazz::isInstance)
                .map(clazz::cast);
    }

    private MapUtils() {
    }

}
