package org.swetlokognatsk.earking_out.core.domain.services.app;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.session.services.SessionRepository;

public final class SessionService {
    protected final PuzzleConfigRepository puzzleConfigRepository;
    protected final SessionRepository sessionRepository;
    protected final SessionAggregatesFactory sessionAggregatesFactory;

    public SessionService(final PuzzleConfigRepository puzzleConfigRepository, final SessionRepository sessionRepository, final SessionAggregatesFactory sessionAggregatesFactory) {
        this.puzzleConfigRepository = puzzleConfigRepository;
        this.sessionRepository = sessionRepository;
        this.sessionAggregatesFactory = sessionAggregatesFactory;
    }

    public UUID start(final Exercise exercise) {
        var session = sessionAggregatesFactory.create(exercise);
        sessionRepository.save(session);
        return session.getId();
    }

    // TODO test
    public void abort(final UUID sessionId) {
        var session = sessionRepository.get(sessionId);
        session.abort();
        sessionRepository.save(session);
    }

    // TODO use it
    public void guessViaPianoKeyPressing(final UUID sessionId, final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber keyNumber) {
        var session = sessionRepository.get(sessionId);
        session.guessViaPianoKeyPressing(keyNumber);
        sessionRepository.save(session);
    }

    // TODO use it
    public void releasePianoKey(final PianoKeyboardId pianoKeyboardId) {

    }

}
