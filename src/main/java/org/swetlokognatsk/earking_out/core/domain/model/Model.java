package org.swetlokognatsk.earking_out.core.domain.model;

public abstract class Model {
    public abstract String[] getErrors();

    public boolean isValid() {
        return getErrors().length == 0;
    }
}
