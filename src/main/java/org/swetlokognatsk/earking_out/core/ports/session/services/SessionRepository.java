package org.swetlokognatsk.earking_out.core.ports.session.services;

import java.util.UUID;

import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRepository;

public interface SessionRepository extends AggregateRepository<UUID, SessionAggregate<?, ?, ?>> {
}
