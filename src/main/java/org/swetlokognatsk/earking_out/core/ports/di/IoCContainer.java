package org.swetlokognatsk.earking_out.core.ports.di;

public interface IoCContainer {
    <T> T get(Class<T> someClass, Object... args);

    void refreshDependencies();
}
