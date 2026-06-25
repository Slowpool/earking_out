package org.swetlokognatsk.earking_out.core.domain.model.base;

import java.util.List;

public abstract interface Model {
    /** Must be implemented dynamically, i.e. so that each getErrors call conducts a new validation */
    abstract List<String> getErrors();

    default boolean isValid() {
        return getErrors().size() == 0;
    }
}
