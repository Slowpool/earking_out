package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

public abstract class PerfectPitchConfig<E extends PerfectPitchExercise> extends PuzzleConfig<E> {

    public PerfectPitchConfig(final int targetNumberOfPuzzles) {
        super(targetNumberOfPuzzles);

    }

    protected final ExerciseNames getExerciseName() {
        return ExerciseNames.PERFECT_PITCH;
    }
}
