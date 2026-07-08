package org.swetlokognatsk.earking_out.core.domain.events.puzzles.configs.perfectpitch;

import java.time.LocalDateTime;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;

public final class VisualPerfectPitchConfigUpdated extends PerfectPitchConfigUpdated<VisualPerfectPitchExercise, VisualPerfectPitchConfigAggregate> {

    public VisualPerfectPitchConfigUpdated(final LocalDateTime timestamp, final VisualPerfectPitchExercise exercise, final VisualPerfectPitchConfigAggregate newConfig) {
        super(timestamp, exercise, newConfig);
    }
}
