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

    // TODO it's not needed anymore, but remained for backward mapping (from form to)
    // protected VisualPerfectPitchConfig mapToDomainConfig() {
    //     // TODO ParsingException?
    //     var targetNumberOfPuzzles = Integer.valueOf(numberOfPuzzlesField.getText());

    //     return new VisualPerfectPitchConfig(targetNumberOfPuzzles, true, null, null, PerfectPitchInputMode.KEYBOARD_AS_PIANO);
    // }
}
