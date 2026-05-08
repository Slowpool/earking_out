package org.swetlokognatsk.earking_out.core.domain.events.puzzles.configs.perfectpitch;

import java.time.LocalDateTime;

import org.swetlokognatsk.earking_out.core.domain.events.puzzles.configs.PuzzleConfigUpdated;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;

class PerfectPitchConfigUpdated<E extends PerfectPitchExercise, PC extends PerfectPitchConfig<E>> extends PuzzleConfigUpdated<E, PC> {

    public PerfectPitchConfigUpdated(final LocalDateTime timestamp, final E exercise, final PC newConfig) {
        super(timestamp, exercise, newConfig);

    }
}
