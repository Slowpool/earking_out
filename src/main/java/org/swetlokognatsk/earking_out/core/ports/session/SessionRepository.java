package org.swetlokognatsk.earking_out.core.ports.session;

import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRootRepository;

// TODO create event-sourcing implementation
public interface SessionRepository<SA extends SessionAggregate<?, ?, ?, ?>> extends AggregateRootRepository<SessionId, SA> {
}
