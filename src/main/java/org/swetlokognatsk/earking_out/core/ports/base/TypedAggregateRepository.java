package org.swetlokognatsk.earking_out.core.ports.base;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;

// abstract because it is expected to at first be extended by another interface, only then implemented by class
public abstract interface TypedAggregateRepository<ID, A extends AggregateRoot<ID>> extends AggregateRepository {
    A get(ID id);

    void save(A aggregate);
}
