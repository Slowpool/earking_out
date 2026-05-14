package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch;

import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;

public abstract class PerfectPitchPane<PC extends PerfectPitchConfig<? extends PerfectPitchExercise>> extends PuzzlePane<PC> {

    public PerfectPitchPane(Session<PC> config) {
        super(config);

    }
}
