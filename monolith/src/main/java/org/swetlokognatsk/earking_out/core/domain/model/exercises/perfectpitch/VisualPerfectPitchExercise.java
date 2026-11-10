package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;

public final class VisualPerfectPitchExercise extends PerfectPitchExercise {
    private static final long serialVersionUID = 1L;

    public VisualPerfectPitchExercise() {
        super(ExerciseTypes.VISUAL);
    }

    public String tType() {
        return "visual";
    }
}
