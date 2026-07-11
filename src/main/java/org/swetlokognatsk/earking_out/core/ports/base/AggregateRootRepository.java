package org.swetlokognatsk.earking_out.core.ports.base;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;

public abstract interface AggregateRootRepository<ID, A extends AggregateRoot<ID>> extends AggregateRepository<ID, A> {
    
}
