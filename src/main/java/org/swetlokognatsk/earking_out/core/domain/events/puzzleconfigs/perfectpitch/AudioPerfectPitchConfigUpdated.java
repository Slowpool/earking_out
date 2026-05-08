package org.swetlokognatsk.earking_out.core.domain.events.puzzleconfigs.perfectpitch;

import java.time.LocalDateTime;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;

public class AudioPerfectPitchConfigUpdated extends PerfectPitchConfigUpdated<AudioPerfectPitchExercise, AudioPerfectPitchConfig> {
    public AudioPerfectPitchConfigUpdated(LocalDateTime timestamp, AudioPerfectPitchExercise exercise, AudioPerfectPitchConfig newConfig) {
        super(timestamp, exercise, newConfig);

    }
}
