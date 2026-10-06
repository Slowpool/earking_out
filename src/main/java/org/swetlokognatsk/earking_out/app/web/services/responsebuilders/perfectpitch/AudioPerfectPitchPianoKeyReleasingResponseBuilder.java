package org.swetlokognatsk.earking_out.app.web.services.responsebuilders.perfectpitch;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING;

import org.swetlokognatsk.earking_out.app.web.models.responses.perfectpitch.AudioPerfectPitchPianoKeyReleasingResponse;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModelsBuilder;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AudioPerfectPitchPianoKeyReleasingResponseBuilder {
    private final PianoKeyboardViewModelsBuilder pianoKeyboardViewModelsBuilder;

    public AudioPerfectPitchPianoKeyReleasingResponse build() {
        var pianoKeyboard = pianoKeyboardViewModelsBuilder.build(AUDIO_PERFECT_PITCH_NOTES_GUESSING);
        return new AudioPerfectPitchPianoKeyReleasingResponse(pianoKeyboard);
    }
}
