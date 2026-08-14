package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.SessionService;

public final class AudioPerfectPitchGuessingOnPianoKeyPressedHandler extends DomainEventHandler<PianoKeyPressedEvent> {

    private final AudioPerfectPitchSessionService sessionService;

    public AudioPerfectPitchGuessingOnPianoKeyPressedHandler(final AudioPerfectPitchSessionService sessionService) {
        this.sessionService = sessionService;
    }

    public void handle(final PianoKeyPressedEvent event) {
        if (event.pianoKeyboardId != PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING) {
            return;
        }

        sessionService.guessViaPianoKeyPressing(event.pianoKeyNumber);
    }
}
