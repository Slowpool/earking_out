package org.swetlokognatsk.earking_out.core.domain.services.app;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;

public abstract class SessionService<E extends Exercise, SA extends SessionAggregate<E, ?, ?, ?>, SP extends SessionRepository<SA>> {
    protected final PuzzleConfigRepository puzzleConfigRepository;
    protected final SP sessionRepository;
    protected final SessionAggregatesFactory sessionAggregatesFactory;

    public SessionService(final PuzzleConfigRepository puzzleConfigRepository, final SP sessionRepository, final SessionAggregatesFactory sessionAggregatesFactory) {
        this.puzzleConfigRepository = puzzleConfigRepository;
        this.sessionRepository = sessionRepository;
        this.sessionAggregatesFactory = sessionAggregatesFactory;
    }

    public final UUID start(final E exercise) {
        var session = (SA) sessionAggregatesFactory.create(exercise);
        sessionRepository.save(session);
        return session.getId();
    }

    // TODO test
    public final void abort(final UUID sessionId) {
        var session = sessionRepository.get(sessionId);
        session.abort();
        sessionRepository.save(session);
    }

}
