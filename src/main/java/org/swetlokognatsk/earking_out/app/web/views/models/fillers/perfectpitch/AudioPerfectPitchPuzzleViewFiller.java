package org.swetlokognatsk.earking_out.app.web.views.models.fillers.perfectpitch;

import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING;
import org.springframework.web.servlet.ModelAndView;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModelsProjector;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound.WebAudioPerfectPitchHintDemonstrator;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;
import lombok.AllArgsConstructor;

@WebComponent
@AllArgsConstructor
public class AudioPerfectPitchPuzzleViewFiller {

    private final PianoKeyboardViewModelsProjector pianoKeyboardsProjector;
    private final AudioPerfectPitchSessionRepository sessionRepository;

    public void fill(final ModelAndView modelAndView) {
        var session = sessionRepository.getActiveSession();

        modelAndView.addObject("sessionId", session.getId());

        modelAndView.addObject("numberOfCompletedPuzzles", session.getStats().puzzlesCompleted);

        var puzzleConfig = session.getPuzzleConfig();
        modelAndView.addObject("targetNumberOfPuzzles", puzzleConfig.targetNumberOfPuzzles);

        modelAndView.addObject("inputMode", puzzleConfig.inputMode);

        var hint = DI.get(WebAudioPerfectPitchHintDemonstrator.class)
                .getHintUrl();
        modelAndView.addObject("hint", hint);

        var guessingPianoKeyboardViewModel = pianoKeyboardsProjector.build(AUDIO_PERFECT_PITCH_NOTES_GUESSING);
        modelAndView.addObject("guessingPianoKeyboardModel", guessingPianoKeyboardViewModel);
    }
}
