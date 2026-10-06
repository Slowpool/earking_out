package org.swetlokognatsk.earking_out.app.web.services.responsebuilders.perfectpitch;

import org.swetlokognatsk.earking_out.app.web.models.responses.perfectpitch.AudioPerfectPitchPianoKeyPressingResponse;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModelsBuilder;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AudioPerfectPitchPianoKeyPressingResponseBuilder {

    private final PianoKeyboardViewModelsBuilder pianoKeyboardViewModelsBuilder;
    private final AudioPerfectPitchGuessResponseBuilder guessResponseBuilder;

    public AudioPerfectPitchPianoKeyPressingResponse build(final SessionId sessionId) {
        // well, i'm not sure it should be hardcoded, but i can't imagine cases when another piano keyboard id may be used here
        var pianoKeyboard = pianoKeyboardViewModelsBuilder.build(AUDIO_PERFECT_PITCH_NOTES_GUESSING);
        var guessResult = guessResponseBuilder.build(sessionId);
        return new AudioPerfectPitchPianoKeyPressingResponse(pianoKeyboard, guessResult);
    }
}
