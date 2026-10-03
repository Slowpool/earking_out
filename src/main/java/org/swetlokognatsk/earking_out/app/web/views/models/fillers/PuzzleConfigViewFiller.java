package org.swetlokognatsk.earking_out.app.web.views.models.fillers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.PIANO_KEYS_NUMBER;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;
import java.util.ArrayList;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyViewModel;
import org.swetlokognatsk.earking_out.app.web.views.models.PianoKeyboardViewModel;
import org.swetlokognatsk.earking_out.app.web.views.models.fillers.PuzzleConfigViewFiller;
import org.swetlokognatsk.earking_out.app.web.views.models.fillers.perfectpitch.AudioPerfectPitchConfigViewFiller;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.*;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode.*;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise.unknownExercise;

@Component
public class PuzzleConfigViewFiller {

    public void fill(final Exercise exercise, final ModelAndView modelAndView) {
        var concreteFiller = switch (exercise) {
        case AudioPerfectPitchExercise appe -> DI.get(AudioPerfectPitchConfigViewFiller.class);
        default -> throw new IllegalArgumentException(unknownExercise(exercise));
        };
        concreteFiller.fill(modelAndView);
    }

}
