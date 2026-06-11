package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;

public final class VisualPerfectPitchConfigPane extends PerfectPitchConfigPane<VisualPerfectPitchExercise, VisualPerfectPitchConfigAggregate> {

    public VisualPerfectPitchConfigPane(final VisualPerfectPitchConfigAggregate puzzleConfig, double width, double height) {
        super(puzzleConfig, width, height);

    }
}
