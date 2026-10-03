package org.swetlokognatsk.earking_out.app.web.services;

import org.swetlokognatsk.earking_out.app.web.models.responses.AudioPerfectPitchPianoKeyPressingResponse;
import org.swetlokognatsk.earking_out.app.web.services.renderers.perfectpitch.AudioPerfectPitchStatsRenderer;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModelsBuilder;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.AudioPerfectPitchSessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound.WebAudioPerfectPitchHintDemonstrator;
import lombok.AllArgsConstructor;

@Component
@Lazy
@AllArgsConstructor
public class AudioPerfectPitchPianoKeyPressingResponseBuilder {

    private final PianoKeyboardViewModelsBuilder pianoKeyboardViewModelsBuilder;
    private final SessionRepositoryDelegator sessionRepository;

    public AudioPerfectPitchPianoKeyPressingResponse build(final SessionId sessionId) {
        var session = (AudioPerfectPitchSessionAggregateDTO) sessionRepository.getSessionAggregateDTO(sessionId);

        var puzzlesCompleted = session.stats.puzzlesCompleted;
        var prevGuessIsSuccessful = session.prevGuessIsSuccessful;

        // well, i'm not sure it should be hardcoded, but i can't made up cases when another piano keyboard id may be used here
        var pianoKeyboard = pianoKeyboardViewModelsBuilder.build(AUDIO_PERFECT_PITCH_NOTES_GUESSING);

        var newHint = prevGuessIsSuccessful ? DI.get(WebAudioPerfectPitchHintDemonstrator.class)
                .getHintUrl()
                : null;

        var sessionStatsHtml = session.state == SessionStates.COMPLETED
                ? DI.get(AudioPerfectPitchStatsRenderer.class)
                        .renderPage(session)
                : null;
        return new AudioPerfectPitchPianoKeyPressingResponse(puzzlesCompleted, prevGuessIsSuccessful, session.state, pianoKeyboard, newHint, sessionStatsHtml);
    }
}
