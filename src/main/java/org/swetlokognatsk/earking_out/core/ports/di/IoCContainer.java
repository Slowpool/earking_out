package org.swetlokognatsk.earking_out.core.ports.di;

import java.util.function.Function;

public interface IoCContainer {
    <T> T get(Class<T> someClass, Object... args);

    void refreshDependencies();

    <T> void register(final Class<T> someClass, final Function<Object[], ?> depFactory);
}
