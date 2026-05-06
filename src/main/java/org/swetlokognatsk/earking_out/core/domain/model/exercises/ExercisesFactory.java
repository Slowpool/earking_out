package org.swetlokognatsk.earking_out.core.domain.model.exercises;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.PerfectPitchExercise;

public final class ExercisesFactory {
    public static Exercise create(ExerciseNames name, ExerciseTypes exerciseType) {
        if (name == ExerciseNames.PERFECT_PITCH) {
            return new PerfectPitchExercise(name, exerciseType);
        }
        throw new RuntimeException("unknown exercise");

    }
}
