package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;

public abstract class PerfectPitchExercise extends Exercise {

    public PerfectPitchExercise(ExerciseTypes type) {
        super(ExerciseNames.PERFECT_PITCH, type);

    }

    public String tName() {
        return "perfect pitch";
    }
}
