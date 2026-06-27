package org.swetlokognatsk.earking_out.core.domain.events.puzzles.configs.perfectpitch;

import java.time.LocalDateTime;

import org.swetlokognatsk.earking_out.core.domain.events.puzzles.configs.PuzzleConfigUpdated;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;

abstract class PerfectPitchConfigUpdated<E extends PerfectPitchExercise, PC extends PerfectPitchConfigAggregate<E>> extends PuzzleConfigUpdated<E, PC> {

    public PerfectPitchConfigUpdated(final LocalDateTime timestamp, final E exercise, final PC newConfig) {
        super(timestamp, exercise, newConfig);
    }
}
