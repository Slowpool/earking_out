package org.swetlokognatsk.earking_out.core.domain.model.base;

public abstract class Model extends Entity {
    public abstract String[] getErrors();

    public boolean isValid() {
        return getErrors().length == 0;
    }
}
