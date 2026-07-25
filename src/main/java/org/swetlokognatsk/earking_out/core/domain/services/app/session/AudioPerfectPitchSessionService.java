package org.swetlokognatsk.earking_out.core.domain.services.app.session;

import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.SessionService;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;

public final class AudioPerfectPitchSessionService extends SessionService<AudioPerfectPitchExercise, AudioPerfectPitchSessionAggregate, AudioPerfectPitchSessionRepository> {

    protected AudioPerfectPitchExercise getExercise() {
        return (AudioPerfectPitchExercise) ExercisesFactory.create(ExerciseNames.PERFECT_PITCH, ExerciseTypes.AUDIO);
    }

    public AudioPerfectPitchSessionService(final PuzzleConfigRepository puzzleConfigRepository, final AudioPerfectPitchSessionRepository sessionRepository, final SessionAggregatesFactory sessionAggregatesFactory) {
        super(puzzleConfigRepository, sessionRepository, sessionAggregatesFactory);
    }

    public void guessViaPianoKeyPressing(final SessionId sessionId, final PianoKeyNumber keyNumber) {
        var session = (AudioPerfectPitchSessionAggregate) sessionRepository.get(sessionId);
        session.guessViaPianoKeyPressing(keyNumber);
        sessionRepository.save(session);
    }

    public void releasePianoKey(final SessionId sessionId) {
        var session = (AudioPerfectPitchSessionAggregate) sessionRepository.get(sessionId);
        session.releasePianoKey();
        sessionRepository.save(session);
    }

    public void hearAgain(final SessionId sessionId) {
        var session = (AudioPerfectPitchSessionAggregate) sessionRepository.get(sessionId);
        session.hearAgain();
        sessionRepository.save(session);
    }
}
