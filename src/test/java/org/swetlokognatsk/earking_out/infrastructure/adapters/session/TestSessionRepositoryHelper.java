package org.swetlokognatsk.earking_out.infrastructure.adapters.session;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;

public final class TestSessionRepositoryHelper {

    public static <SR extends SessionRepository<?>> SessionId seedTestSession(final SR repository) {
        var sessionAggregatesFactory = DI.get(SessionAggregatesFactory.class);
        SessionId sessionId;
        // TODO can it be more elegant?
        switch (repository) {
        case AudioPerfectPitchSessionRepository appsr:
            var exercise = new AudioPerfectPitchExercise();
            AudioPerfectPitchSessionAggregate someSession = sessionAggregatesFactory.create(exercise);
            appsr.save(someSession);
            sessionId = someSession.getId();
            break;
        default:
            throw new IllegalStateException();
        }
        return sessionId;
    }
}
