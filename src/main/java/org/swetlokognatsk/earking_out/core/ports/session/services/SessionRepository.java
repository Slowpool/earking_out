package org.swetlokognatsk.earking_out.core.ports.session.services;

import java.util.UUID;

import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRepository;

// TODO create event-sourcing implementation
public interface SessionRepository extends AggregateRepository<UUID, SessionAggregate<?, ?, ?>> {
}
