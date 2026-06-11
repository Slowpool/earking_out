package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;

public final class AudioPerfectPitchConfigPane extends PerfectPitchConfigPane<AudioPerfectPitchExercise, AudioPerfectPitchConfigAggregate> {

    public AudioPerfectPitchConfigPane(final AudioPerfectPitchConfigAggregate puzzleConfig, final double width, final double height) {
        super(puzzleConfig, width, height);

    }
}
