package org.swetlokognatsk.earking_out.core.domain.model.exercises;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.typed.VisualPerfectPitchExercise;

public final class ExercisesFactory {
    public static Exercise create(ExerciseNames name, ExerciseTypes exerciseType) {
        return switch (name)
        {
            case PERFECT_PITCH -> switch (exerciseType) {
                case VISUAL -> new VisualPerfectPitchExercise();
                case AUDIO -> new AudioPerfectPitchExercise();
            };
            default -> throw new RuntimeException("unknown exercise");
        };
    }
}
