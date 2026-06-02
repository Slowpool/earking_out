package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;

public final class VisualPerfectPitchExercise extends PerfectPitchExercise {

    public VisualPerfectPitchExercise() {
        super(ExerciseTypes.VISUAL);

    }

    public String tType() {
        return "visual";
    }
}
