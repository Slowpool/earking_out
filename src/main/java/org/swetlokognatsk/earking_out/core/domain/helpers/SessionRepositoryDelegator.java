package org.swetlokognatsk.earking_out.core.domain.helpers;

import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;

// TODO this class is actually awkward workaround of session repositories polymorphism
// TODO it should not be here
public final class SessionRepositoryDelegator {
    // TODO think about it
    private SessionRepository<?>[] repositories = new SessionRepository[] { DI.get(AudioPerfectPitchSessionRepository.class) };

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
}
