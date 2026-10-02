package org.swetlokognatsk.earking_out.app.web.services;

import org.swetlokognatsk.earking_out.app.web.models.responses.AudioPerfectPitchPianoKeyPressingResponse;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModelsBuilder;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound.WebAudioPerfectPitchHintDemonstrator;
import lombok.AllArgsConstructor;

@Component
@Lazy
@AllArgsConstructor
public class AudioPerfectPitchPianoKeyPressingResponseBuilder {

    private final PianoKeyboardViewModelsBuilder pianoKeyboardViewModelsBuilder;
    private final AudioPerfectPitchSessionRepository sessionRepository;

    public AudioPerfectPitchPianoKeyPressingResponse build() {
        // well, i'm not sure it should be hardcoded, but i can't made up cases when another piano keyboard id may be used here
        var pianoKeyboard = pianoKeyboardViewModelsBuilder.build(AUDIO_PERFECT_PITCH_NOTES_GUESSING);

        var session = sessionRepository.getActiveSession();

        var prevGuessIsSuccessful = session.getPrevGuessIsSuccessful();
        var newHint = prevGuessIsSuccessful ? DI.get(WebAudioPerfectPitchHintDemonstrator.class)
                .getHintUrl()
                : null;

        return new AudioPerfectPitchPianoKeyPressingResponse(session.getPuzzlesCompleted(), prevGuessIsSuccessful, session.getState(), pianoKeyboard, newHint);
    }
}
