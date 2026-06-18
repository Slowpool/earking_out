package org.swetlokognatsk.earking_out.core.ports.base;

public interface ObjectCloner {
    <T> T clone(final T object);
}
