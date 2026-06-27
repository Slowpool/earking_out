package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import java.util.UUID;
import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;

public abstract class PerfectPitchPane<PCDTO extends PerfectPitchConfigDTO<? extends PerfectPitchExercise>> extends PuzzlePane<PCDTO> {

    public PerfectPitchPane(final UUID sessionId, final PCDTO config, final double width, final double height) {
        super(sessionId, config, width, height);
    }
}
