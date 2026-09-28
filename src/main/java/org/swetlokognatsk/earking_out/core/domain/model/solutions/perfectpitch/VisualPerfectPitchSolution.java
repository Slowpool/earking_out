package org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch;

import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.VISUAL_PERFECT_PITCH_EXERCISE;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class VisualPerfectPitchSolution extends PerfectPitchSolution {

    public VisualPerfectPitchSolution(final PianoKeyNumber keyNumber) {
        super(VISUAL_PERFECT_PITCH_EXERCISE, keyNumber);
    }

    public VisualPerfectPitchSolution(final VisualPerfectPitchExercise exercise, @JsonProperty("keyNumber") final PianoKeyNumber keyNumber) {
        super(exercise, keyNumber);
    }
}