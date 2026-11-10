package org.swetlokognatsk.earking_out.app.web.views.models.fillers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.swetlokognatsk.earking_out.app.web.views.models.fillers.perfectpitch.AudioPerfectPitchConfigViewFiller;
import org.swetlokognatsk.earking_out.app.web.views.models.fillers.perfectpitch.AudioPerfectPitchPuzzleViewFiller;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

@Component
public class PuzzleViewFiller {

    public void fill(final Exercise exercise, final ModelAndView modelAndView) {
        var concreteFiller = switch (exercise) {
        case AudioPerfectPitchExercise appe -> DI.get(AudioPerfectPitchPuzzleViewFiller.class);
        default -> throw new IllegalArgumentException("unknown exercise: %s".formatted(exercise.toString()));
        };
        concreteFiller.fill(modelAndView);
    }
}
