package org.swetlokognatsk.earking_out.core.domain.model.base;

public abstract class AggregateRoot<ID> extends Aggregate<ID> {
    private static final long serialVersionUID = 1L;

    public AggregateRoot(final ID id) {
        super(id);
    }

}
