package org.swetlokognatsk.earking_out.core.domain.helpers;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;

// TODO this class is actually awkward workaround of session repositories polymorphism
// TODO it should not be here
@Service
public final class SessionRepositoryDelegator {
    private final SessionRepository<?>[] repositories;

    public SessionRepositoryDelegator(final AudioPerfectPitchSessionRepository audioPerfectPitchSessionRepository) {
        repositories = new SessionRepository[] { audioPerfectPitchSessionRepository };
    }

    public SessionAggregate<?, ?, ?, ?> get(final SessionId sessionId) {
        SessionAggregate<?, ?, ?, ?> sessionAggregate;
        for (var repository : repositories) {
            try {
                sessionAggregate = repository.get(sessionId);
                return sessionAggregate;
            } catch (IllegalArgumentException e) {
            }
        }
        throw new IllegalArgumentException();
    }

    public SessionAggregateDTO<?, ?, ?, ?> getSessionAggregateDTO(final SessionId sessionId) {
        var sessionAggregate = get(sessionId);

        return SessionAggregateDTOAssembler.assemble(sessionAggregate);
    }
}
