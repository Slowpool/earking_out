package org.swetlokognatsk.earking_out.core.ports.base;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;

public interface PolymorphicAggregateRepository<BaseID, A extends AggregateRoot<? extends BaseID>> extends AggregateRepository {
    <PolymorphicID extends BaseID, Aggregate extends AggregateRoot<PolymorphicID>> Aggregate get(PolymorphicID id);

    <Aggregate extends A> void save(Aggregate aggregate);
}
