package org.swetlokognatsk.earking_out.core.ports.di;

import java.util.function.Function;

public interface IoCContainer {
    <T> T get(Class<T> someClass, Object... args);

    void refreshDependencies();

    <T> void register(Class<T> interfaceClass, Class<? extends T> implementationClass) throws IllegalStateException;

    <T> void register(Class<T> interfaceClass, T object) throws IllegalStateException;
}
