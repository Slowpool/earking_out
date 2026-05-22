package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;

public abstract class PerfectPitchPane<E extends PerfectPitchExercise, PC extends PerfectPitchConfig<E>, H extends Hint, P extends PerfectPitchPuzzle<E, PC, H>> extends PuzzlePane<E, PC, H, P> {

    public PerfectPitchPane(Session<PC> config, double width, double height) {
        super(config, width, height);

    }
}
