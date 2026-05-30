package org.swetlokognatsk.earking_out.core.domain.model.exercises;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.VisualPerfectPitchExercise;

public final class ExercisesFactory {

    private ExercisesFactory() {
    }

    public static Exercise create(ExerciseNames name, ExerciseTypes type) {
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
