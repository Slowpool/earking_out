package org.swetlokognatsk.earking_out.core.ports.base;

import org.swetlokognatsk.earking_out.core.domain.model.base.Aggregate;

public abstract interface AggregateRepository<ID, A extends Aggregate<ID>> {
    A get(final ID id);

    void save(final A aggregate);
}
