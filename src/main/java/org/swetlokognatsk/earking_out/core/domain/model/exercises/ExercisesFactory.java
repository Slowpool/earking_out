package org.swetlokognatsk.earking_out.core.domain.model.exercises;

import java.util.Arrays;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;

public final class ExercisesFactory {
    public static final AudioPerfectPitchExercise AUDIO_PERFECT_PITCH_EXERCISE = new AudioPerfectPitchExercise();
    public static final VisualPerfectPitchExercise VISUAL_PERFECT_PITCH_EXERCISE = new VisualPerfectPitchExercise();

    private static final Exercise[] allExercises = new Exercise[] { AUDIO_PERFECT_PITCH_EXERCISE };

    public static Exercise[] getAll() {
        return allExercises;
    }

    public static Exercise[] getAll(final ExerciseNames exerciseName) {
        var filteredExercises = Arrays.stream(allExercises)
                .filter((Exercise exercise) -> exercise.name == ExerciseNames.PERFECT_PITCH)
                .toArray(Exercise[]::new);
        return filteredExercises;
    }

    private ExercisesFactory() {
    }

    public static Exercise create(final ExerciseNames name, final ExerciseTypes type) {
        return switch (name) {
        case PERFECT_PITCH -> switch (type) {
        case VISUAL -> VISUAL_PERFECT_PITCH_EXERCISE;
        case AUDIO -> AUDIO_PERFECT_PITCH_EXERCISE;
        default -> throw new IllegalArgumentException("unknown exercise type: " + type);
        };
        default -> throw new IllegalArgumentException("unknown exercise: " + name);
        };
    }
}
