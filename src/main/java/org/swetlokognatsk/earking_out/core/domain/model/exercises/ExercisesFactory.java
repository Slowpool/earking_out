package org.swetlokognatsk.earking_out.core.domain.model.exercises;

import java.util.Arrays;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;

public final class ExercisesFactory {
    private static final Exercise[] allExercises = new Exercise[] { new VisualPerfectPitchExercise(), new AudioPerfectPitchExercise() };

    public static Exercise[] getAll() {
        return allExercises;
    }

    public static Exercise[] getAll(final ExerciseNames exerciseName) {
        var filteredExercises = Arrays.stream(allExercises).filter((Exercise exercise) -> exercise.name == ExerciseNames.PERFECT_PITCH).toArray(Exercise[]::new);
        return filteredExercises;
    }

    private ExercisesFactory() {
    }
}
