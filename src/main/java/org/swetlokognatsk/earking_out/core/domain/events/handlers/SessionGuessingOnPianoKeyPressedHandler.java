package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.SessionService;

public final class SessionGuessingOnPianoKeyPressedHandler extends DomainEventHandler<PianoKeyPressedEvent> {

    private final Map<Exercise, SessionService<?, ?, ?>> sessionServices;

    private <E extends Exercise, SS extends SessionService<E, ?, ?>> SS getService(final E exercise) {
        return (SS) sessionServices.get(exercise);
    }

    public SessionGuessingOnPianoKeyPressedHandler(final Map<Exercise, SessionService<?, ?, ?>> sessionServices) {
        this.sessionServices = sessionServices;
    }

    public void handle(final PianoKeyPressedEvent event) {
        if (event.pianoKeyboardId != PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING) {
            return;
        }

        var exercise = event.pianoKeyboardId.exercise;
        switch (exercise) {
        case AudioPerfectPitchExercise appe:
            AudioPerfectPitchSessionService service = getService(appe);
            service.guessViaPianoKeyPressing(event.pianoKeyNumber);
            break;
        default:
            throw new IllegalArgumentException("unknown exercise: " + exercise);
        }
    }
}
