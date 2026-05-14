package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.typed.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;

public class VisualPerfectPitchConfig extends PerfectPitchConfig<VisualPerfectPitchExercise> {

    public VisualPerfectPitchConfig(final int targetNumberOfPuzzles) {
        super(targetNumberOfPuzzles);

    }

    protected final ExerciseTypes getExerciseType() {
        return ExerciseTypes.VISUAL;
    }
}
