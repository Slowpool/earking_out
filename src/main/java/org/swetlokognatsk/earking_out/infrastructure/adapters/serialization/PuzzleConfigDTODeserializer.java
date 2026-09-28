package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;

public final class PuzzleConfigDTODeserializer extends ExerciseBasedPolymorphicDeserializer<PuzzleConfigDTO<?>> {

    public PuzzleConfigDTODeserializer(final Class<?> vc) {
        super(vc);
    }

    protected Class<? extends PuzzleConfigDTO<?>> getTargetType(final Exercise deserializedExercise) {
        return switch (deserializedExercise) {
        case AudioPerfectPitchExercise appe -> AudioPerfectPitchConfigDTO.class;
        case VisualPerfectPitchExercise vppe -> VisualPerfectPitchConfigDTO.class;
        default -> throw new RuntimeException("unknown exercise: %s".formatted(deserializedExercise.toString()));
        };
    }
}
