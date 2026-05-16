package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;

public class VisualPerfectPitchConfigPane extends PerfectPitchConfigPane<VisualPerfectPitchConfig> {

    public VisualPerfectPitchConfigPane(final VisualPerfectPitchConfig puzzleConfig) {
        super(puzzleConfig);

    }

    protected VisualPerfectPitchConfig mapToDomainConfig() {
        // TODO ParsingException?
        var targetNumberOfPuzzles = Integer.valueOf(numberOfPuzzlesField.getText());
        return new VisualPerfectPitchConfig(targetNumberOfPuzzles);
    }
}
