package org.swetlokognatsk.earking_out.core.domain.model.base;

// TODO use this interface for all factories
public abstract interface Factory<O> {
    public abstract O createDefault();

    public abstract O createDeepCopy(final O o);
}
