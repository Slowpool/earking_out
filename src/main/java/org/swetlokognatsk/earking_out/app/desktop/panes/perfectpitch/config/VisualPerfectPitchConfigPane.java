package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;

public final class VisualPerfectPitchConfigPane extends PerfectPitchConfigPane<VisualPerfectPitchExercise, VisualPerfectPitchConfigDTO> {

    public VisualPerfectPitchConfigPane(final VisualPerfectPitchConfigDTO puzzleConfigDto, double width, double height) {
        super(puzzleConfigDto, width, height);

    }
}
