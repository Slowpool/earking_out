package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;

public class AudioPerfectPitchConfigPane extends PerfectPitchConfigPane<AudioPerfectPitchExercise, AudioPerfectPitchConfig> {

    public AudioPerfectPitchConfigPane(final AudioPerfectPitchConfig puzzleConfig, final double width, final double height) {
        super(puzzleConfig, width, height);

    }
}
