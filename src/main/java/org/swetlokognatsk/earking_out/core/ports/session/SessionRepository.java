package org.swetlokognatsk.earking_out.core.ports.session;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRepository;

// TODO create event-sourcing implementation
public interface SessionRepository<SA extends SessionAggregate<?, ?, ?, ?>> extends AggregateRepository<UUID, SA> {
}
