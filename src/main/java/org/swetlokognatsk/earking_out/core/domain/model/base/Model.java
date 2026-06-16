package org.swetlokognatsk.earking_out.core.domain.model.base;

public abstract interface Model {
    abstract String[] getErrors();

    default boolean isValid() {
        return getErrors().length == 0;
    }
}
