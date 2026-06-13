package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.PerfectPitchPuzzleGenerator;

public abstract class PerfectPitchPane<E extends PerfectPitchExercise, PCDTO extends PerfectPitchConfigDTO<E>, H extends Hint, PG extends PerfectPitchPuzzleGenerator<PCDTO>, P extends PerfectPitchPuzzle<E, PCDTO, H, PG>> extends PuzzlePane<E, PCDTO, H, PG, P> {

    public PerfectPitchPane(final Session<PCDTO> config, final double width, final double height) {
        super(config, width, height);

    }
}
