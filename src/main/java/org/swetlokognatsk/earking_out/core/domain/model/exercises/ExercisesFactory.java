package org.swetlokognatsk.earking_out.core.domain.model.exercises;

import java.util.Arrays;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;

public final class ExercisesFactory {
    // TODO use it everywhere instead of new AudioPerfectPitchExercise()
    public static final AudioPerfectPitchExercise AUDIO_PERFECT_PITCH_EXERCISE = new AudioPerfectPitchExercise();
    
    private static final Exercise[] allExercises = new Exercise[] { new AudioPerfectPitchExercise() };

    public static Exercise[] getAll() {
        return allExercises;
    }

    public static Exercise[] getAll(final ExerciseNames exerciseName) {
        var filteredExercises = Arrays.stream(allExercises).filter((Exercise exercise) -> exercise.name == ExerciseNames.PERFECT_PITCH).toArray(Exercise[]::new);
        return filteredExercises;
    }

    private ExercisesFactory() {
    }

    public static Exercise create(final ExerciseNames name, final ExerciseTypes type) {
        return switch (name) {
        case PERFECT_PITCH -> switch (type) {
        case VISUAL -> new VisualPerfectPitchExercise();
        case AUDIO -> new AudioPerfectPitchExercise();
        default -> throw new IllegalArgumentException("unknown exercise type: " + type);
        };
        default -> throw new IllegalArgumentException("unknown exercise: " + name);
        };
    }
}
