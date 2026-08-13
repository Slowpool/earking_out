package org.swetlokognatsk.earking_out.core.domain.services.app;

import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardContext;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;

public final class PianoKeyboardService {

    private final PianoKeyboardRepository repository;
    private final AudioPerfectPitchSessionRepository sessionRepository;

    public PianoKeyboardService(final PianoKeyboardRepository repository, final AudioPerfectPitchSessionRepository sessionRepository) {
        this.repository = repository;
        this.sessionRepository = sessionRepository;
    }

    // TODO apply polymorphism
    public void pressPianoKey(final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber pianoKeyNumber) {
        if (pianoKeyboardId.context == PianoKeyboardContext.SESSION) {
            ensureThereIsActiveSession(pianoKeyboardId.exercise);
        }

        var pianoKeyboardAggregate = repository.get(pianoKeyboardId);
        try {
            pianoKeyboardAggregate.pressKey(pianoKeyNumber);
            repository.save(pianoKeyboardAggregate);
        }
        // TODO just Exception?
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void ensureThereIsActiveSession(final Exercise exercise) {
        // TODO refactoring, polymorphism
        switch (exercise) {
        case AudioPerfectPitchExercise appe:
            sessionRepository.getActiveSession();
            break;
        default:
            break;
        }
    }

    public void releasePianoKey(final PianoKeyboardId pianoKeyboardId) {
        if (pianoKeyboardId.context == PianoKeyboardContext.SESSION) {
            ensureThereIsActiveSession(pianoKeyboardId.exercise);
        }

        var pianoKeyboardAggregate = repository.get(pianoKeyboardId);
        try {
            pianoKeyboardAggregate.releaseKey();
            repository.save(pianoKeyboardAggregate);
        }
        // TODO just Exception?
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void resetPianoKeyboardState(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyboardAggregate = repository.get(pianoKeyboardId);
        try {
            pianoKeyboardAggregate.resetState();
            repository.save(pianoKeyboardAggregate);
        }
        // TODO just Exception?
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
