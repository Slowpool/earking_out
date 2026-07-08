package org.swetlokognatsk.earking_out.core.domain.helpers;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;

// TODO this class is actually awkward workaround of session repositories polymorphism
public final class SessionRepositoryDelegator {

    // TODO what's the difference between `SomeClass<?>` and `SomeClass`?
    protected SessionRepository<?>[] repositories = new SessionRepository[] { DI.get(AudioPerfectPitchSessionRepository.class) };

    public SessionAggregate<?, ?, ?, ?> get(final UUID sessionId) {
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
