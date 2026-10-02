package org.swetlokognatsk.earking_out.app.web.views.models.fillers.perfectpitch;

import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.swetlokognatsk.earking_out.SpringApp;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModelsBuilder;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound.WebAudioPerfectPitchHintDemonstrator;

import lombok.AllArgsConstructor;

@Component
@Lazy
@AllArgsConstructor
@ConditionalOnExpression(SpringApp.IS_WEB_BUILD)
public class AudioPerfectPitchPuzzleViewFiller {

    private final PianoKeyboardViewModelsBuilder pianoKeyboardsBuilder;
    private final AudioPerfectPitchSessionRepository sessionRepository;

    public void fill(final ModelAndView modelAndView) {
        var session = sessionRepository.getActiveSession();
        modelAndView.addObject("numberOfCompletedPuzzles", session.getStats().puzzlesCompleted);

        modelAndView.addObject("targetNumberOfPuzzles", session.getPuzzleConfig().targetNumberOfPuzzles);

        var pianoKeyNumberHint = DI.get(WebAudioPerfectPitchHintDemonstrator.class)
            .pianoKeyNumberToPlay.value;
            // TODO specific class for this logic?
        var hint = "/sounds/piano_keys/key%d.wav".formatted(pianoKeyNumberHint);
        modelAndView.addObject("hint", hint);
        
        var guessingPianoKeyboardViewModel = pianoKeyboardsBuilder.build(AUDIO_PERFECT_PITCH_NOTES_GUESSING);
        modelAndView.addObject("guessingPianoKeyboardModel", guessingPianoKeyboardViewModel);
    }
}
