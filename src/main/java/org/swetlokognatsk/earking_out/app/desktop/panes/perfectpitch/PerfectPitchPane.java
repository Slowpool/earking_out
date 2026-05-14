package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch;

import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;

public abstract class PerfectPitchPane<E extends PerfectPitchExercise, PC extends PerfectPitchConfig<E>> extends PuzzlePane<E, PC> {

    public PerfectPitchPane(PC config) {
        super(config);

    }
}
