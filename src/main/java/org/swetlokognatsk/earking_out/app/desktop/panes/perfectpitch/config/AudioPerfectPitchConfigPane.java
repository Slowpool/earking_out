package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;

public class AudioPerfectPitchConfigPane extends PerfectPitchConfigPane<AudioPerfectPitchConfig> {

    public AudioPerfectPitchConfigPane(final AudioPerfectPitchConfig puzzleConfig) {
        super(puzzleConfig);

    }

    protected AudioPerfectPitchConfig mapToDomainConfig() {
        // TODO ParsingException?
        var targetNumberOfPuzzles = Integer.valueOf(numberOfPuzzlesField.getText());
        return new AudioPerfectPitchConfig(targetNumberOfPuzzles);
    }
}
