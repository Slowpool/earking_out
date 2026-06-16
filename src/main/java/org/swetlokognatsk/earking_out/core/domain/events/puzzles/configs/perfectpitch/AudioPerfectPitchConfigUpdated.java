package org.swetlokognatsk.earking_out.core.domain.events.puzzles.configs.perfectpitch;

import java.time.LocalDateTime;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;

public final class AudioPerfectPitchConfigUpdated extends PerfectPitchConfigUpdated<AudioPerfectPitchExercise, AudioPerfectPitchConfigAggregate> {
    public AudioPerfectPitchConfigUpdated(LocalDateTime timestamp, AudioPerfectPitchExercise exercise, AudioPerfectPitchConfigAggregate newConfig) {
        super(timestamp, exercise, newConfig);

    }
}
