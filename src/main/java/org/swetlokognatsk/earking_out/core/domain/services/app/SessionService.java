package org.swetlokognatsk.earking_out.core.domain.services.app;

import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;

public abstract class SessionService<E extends Exercise, SA extends SessionAggregate<E, ?, ?, ?>, SP extends SessionRepository<SA>> {
    protected final PuzzleConfigRepository puzzleConfigRepository;
    protected final SP sessionRepository;
    protected final SessionAggregatesFactory sessionAggregatesFactory;

    protected abstract E getExercise();

    public SessionService(final PuzzleConfigRepository puzzleConfigRepository, final SP sessionRepository, final SessionAggregatesFactory sessionAggregatesFactory) {
        this.puzzleConfigRepository = Objects.requireNonNull(puzzleConfigRepository);
        this.sessionRepository = Objects.requireNonNull(sessionRepository);
        this.sessionAggregatesFactory = Objects.requireNonNull(sessionAggregatesFactory);
    }

    public final SessionId start() {
        var session = (SA) sessionAggregatesFactory.create(getExercise());
        sessionRepository.save(session);
        return session.getId();
    }

    public final void abort(final SessionId sessionId) {
        var session = sessionRepository.get(sessionId);
        session.abort();
        sessionRepository.save(session);
    }

}
