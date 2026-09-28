package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.VisualPerfectPitchSolution;

public final class SolutionDeserializer extends ExerciseBasedPolymorphicDeserializer<Solution> {

    public SolutionDeserializer(final Class<?> vc) {
        super(vc);
    }

    protected Class<? extends Solution> getTargetType(final Exercise deserializedExercise) {
        return switch (deserializedExercise) {
        case AudioPerfectPitchExercise appe -> AudioPerfectPitchSolution.class;
        case VisualPerfectPitchExercise vppe -> VisualPerfectPitchSolution.class;
        default -> throw new RuntimeException("unknown exercise: %s".formatted(deserializedExercise.toString()));
        };
    }
}
