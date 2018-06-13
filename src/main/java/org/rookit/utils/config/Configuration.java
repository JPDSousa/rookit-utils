package org.rookit.utils.config;

public interface Configuration {

    <V> V get(String name, Class<V> clazz);
}
