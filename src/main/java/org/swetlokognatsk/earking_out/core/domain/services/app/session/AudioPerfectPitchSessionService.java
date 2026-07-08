package org.swetlokognatsk.earking_out.core.domain.services.app.session;

import java.util.UUID;

import org.swetlokognatsk.earking_out.app.desktop.events.session.HearAgainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.SessionService;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;

public final class AudioPerfectPitchSessionService extends SessionService<AudioPerfectPitchExercise, AudioPerfectPitchSessionAggregate, AudioPerfectPitchSessionRepository> {

    public AudioPerfectPitchSessionService(final PuzzleConfigRepository puzzleConfigRepository, final AudioPerfectPitchSessionRepository sessionRepository, final SessionAggregatesFactory sessionAggregatesFactory) {
        super(puzzleConfigRepository, sessionRepository, sessionAggregatesFactory);
    }

    public void guessViaPianoKeyPressing(final UUID sessionId, final PianoKeyNumber keyNumber) {
        var session = (AudioPerfectPitchSessionAggregate) sessionRepository.get(sessionId);
        session.guessViaPianoKeyPressing(keyNumber);
        sessionRepository.save(session);
    }

    public void releasePianoKey(final UUID sessionId) {
        var session = (AudioPerfectPitchSessionAggregate) sessionRepository.get(sessionId);
        session.releasePianoKey();
        sessionRepository.save(session);
    }

    public void hearAgain() {
        
    }
}
