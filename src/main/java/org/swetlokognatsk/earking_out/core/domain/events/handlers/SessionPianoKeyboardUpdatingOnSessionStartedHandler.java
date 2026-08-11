package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.inftrastructure.eventsourcing.EventStream;

public final class SessionPianoKeyboardUpdatingOnSessionStartedHandler {

    private final PianoKeyboardService pianoKeyboardService;

    public SessionPianoKeyboardUpdatingOnSessionStartedHandler(final PianoKeyboardService pianoKeyboardService) {
        this.pianoKeyboardService = pianoKeyboardService;
    }

    public void handleSessionStartedEvent(final SessionStartedEvent event) {
        switch (event.puzzleConfigDto.exercise) {
        case AudioPerfectPitchExercise appe:
            pianoKeyboardService.refreshPianoKeyboardState(PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING);
            break;
        default:
            break;
        }
    }
}
