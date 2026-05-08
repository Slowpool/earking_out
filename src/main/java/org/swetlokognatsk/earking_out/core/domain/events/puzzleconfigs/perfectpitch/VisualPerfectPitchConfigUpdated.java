package org.swetlokognatsk.earking_out.core.domain.events.puzzleconfigs.perfectpitch;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.typed.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;

public class VisualPerfectPitchConfigUpdated extends PerfectPitchConfigUpdated<VisualPerfectPitchExercise, VisualPerfectPitchConfig> {

    public VisualPerfectPitchConfigUpdated(LocalDateTime timestamp, VisualPerfectPitchExercise exercise, VisualPerfectPitchConfig newConfig) {
        super(timestamp, exercise, newConfig);

    }
}
