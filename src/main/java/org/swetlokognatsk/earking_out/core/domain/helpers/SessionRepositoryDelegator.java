package org.swetlokognatsk.earking_out.core.domain.helpers;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;

// TODO this class is actually awkward workaround of session repositories polymorphism
// TODO it should not be here
@Service
public final class SessionRepositoryDelegator {
    private final SessionRepository<?>[] repositories;
    private final SessionAggregateDTOAssembler dtoAssembler;

    public SessionRepositoryDelegator(final AudioPerfectPitchSessionRepository audioPerfectPitchSessionRepository, final SessionAggregateDTOAssembler dtoAssembler) {
        repositories = new SessionRepository[] { audioPerfectPitchSessionRepository };
        this.dtoAssembler = dtoAssembler;
    }

    public SessionAggregateDTO<?, ?, ?, ?> getSessionAggregateDTO(final SessionId sessionId, final UserId userId) {
        for (var repository : repositories) {
            try {
                return repository.getDto(sessionId, userId);
            } catch (AggregateNotFoundException e) {
            }
        }
        throw new AggregateNotFoundException();
    }

    public SessionAggregate<?, ?, ?, ?> get(final SessionId sessionId) {
        for (var repository : repositories) {
            try {
                return repository.get(sessionId);
            } catch (AggregateNotFoundException e) {
            }
        }
        throw new AggregateNotFoundException();
    }

    // TODO think about its implementation
    public SessionAggregateDTO<?, ?, ?, ?> getSessionAggregateDTO(final SessionId sessionId) {
        var sessionAggregate = get(sessionId);

        return dtoAssembler.assemble(sessionAggregate);
    }
}
