package org.swetlokognatsk.earking_out.core.domain.events.puzzles.configs.perfectpitch;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.typed.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;

public class VisualPerfectPitchConfigUpdated extends PerfectPitchConfigUpdated<VisualPerfectPitchExercise, VisualPerfectPitchConfig> {

    public VisualPerfectPitchConfigUpdated(final LocalDateTime timestamp, final VisualPerfectPitchExercise exercise, final VisualPerfectPitchConfig newConfig) {
        super(timestamp, exercise, newConfig);

    }
}
