package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.aggregates.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.aggregates.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public abstract class PerfectPitchConfigAggregate<PC extends PerfectPitchConfig<?>> extends PuzzleConfigAggregate<PC> {

    public PerfectPitchConfigAggregate(final PC puzzleConfig, final PianoKeyboardRepository pianoKeyboardRepository) {
        super(puzzleConfig, pianoKeyboardRepository);

    }

}
