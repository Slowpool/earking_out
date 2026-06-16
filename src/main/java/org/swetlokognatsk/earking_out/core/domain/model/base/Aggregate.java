package org.swetlokognatsk.earking_out.core.domain.model.base;

import org.apache.commons.lang3.NotImplementedException;

public abstract class Aggregate<ID> extends Entity<ID> implements Model {

    public Aggregate(final ID id) {
        super(id);
    }

    public String[] getErrors() {
        throw new NotImplementedException();
    }
}
