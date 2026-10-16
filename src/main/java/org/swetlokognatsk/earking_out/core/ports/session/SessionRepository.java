package org.swetlokognatsk.earking_out.core.ports.session;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.ports.base.TypedAggregateRepository;

// TODO create event-sourcing implementation
public interface SessionRepository<SA extends SessionAggregate<?, ?, ?, ?>> extends TypedAggregateRepository<SessionId, SA> {
    SA getActiveSession();

    <SADTO extends SessionAggregateDTO<?, ?, ?, SA>> SADTO getDto(SessionId id, UserId userId);
}
