package org.swetlokognatsk.earking_out.core.domain.services.app.session;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.SessionService;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.session.services.SessionRepository;

public final class AudioPerfectPitchSessionService extends SessionService {

    public AudioPerfectPitchSessionService(final PuzzleConfigRepository puzzleConfigRepository, final SessionRepository sessionRepository, final SessionAggregatesFactory sessionAggregatesFactory) {
        super(puzzleConfigRepository, sessionRepository, sessionAggregatesFactory);
    }

    // TODO use it
    public void guessViaPianoKeyPressing(final UUID sessionId, final PianoKeyNumber keyNumber) {
        var session = (AudioPerfectPitchSessionAggregate) sessionRepository.get(sessionId);
        session.guessViaPianoKeyPressing(keyNumber);
        sessionRepository.save(session);
    }

    // TODO use it
    public void releasePianoKey(final UUID sessionId) {
        var session = (AudioPerfectPitchSessionAggregate) sessionRepository.get(sessionId);
        session.releasePianoKey();
        sessionRepository.save(session);
    }
}
