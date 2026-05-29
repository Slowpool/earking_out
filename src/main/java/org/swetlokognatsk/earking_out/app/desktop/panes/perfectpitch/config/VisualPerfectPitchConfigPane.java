package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;

public class VisualPerfectPitchConfigPane extends PerfectPitchConfigPane<VisualPerfectPitchExercise, VisualPerfectPitchConfig> {

    public VisualPerfectPitchConfigPane(final VisualPerfectPitchConfig puzzleConfig, double width, double height) {
        super(puzzleConfig, width, height);

    }

    protected void setFieldsValuesFromConfig(VisualPerfectPitchConfig puzzleConfig) {
        super.setFieldsValuesFromConfig((PerfectPitchConfig<VisualPerfectPitchExercise>) puzzleConfig);
    }
}
