package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.VisualPerfectPitchPuzzle;

public final class PuzzleDeserializer extends ExerciseBasedPolymorphicDeserializer<Puzzle<?, ?>> {

    public PuzzleDeserializer(final Class<?> vc) {
        super(vc);
    }

    protected Class<? extends Puzzle<?, ?>> getTargetType(final Exercise deserializedExercise) {
        return switch (deserializedExercise) {
        case AudioPerfectPitchExercise appe -> AudioPerfectPitchPuzzle.class;
        case VisualPerfectPitchExercise vppe -> VisualPerfectPitchPuzzle.class;
        default -> throw new RuntimeException("unknown exercise: %s".formatted(deserializedExercise.toString()));
        };
    }

}
