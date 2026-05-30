package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;

public final class VisualPerfectPitchExercise extends PerfectPitchExercise {

    public VisualPerfectPitchExercise() {
        super(ExerciseTypes.VISUAL);

    }

    public String tType() {
        return "visual";
    }
}
