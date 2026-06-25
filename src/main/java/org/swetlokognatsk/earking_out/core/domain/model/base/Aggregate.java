package org.swetlokognatsk.earking_out.core.domain.model.base;

import java.util.List;
import org.apache.commons.lang3.NotImplementedException;

// TODO add aggregate root

public abstract class Aggregate<ID> extends Entity<ID> implements Model {

    public Aggregate(final ID id) {
        super(id);
    }

    // TODO delete this latch, implement it inside each aggregate
    public List<String> getErrors() {
        throw new NotImplementedException();
    }
}
