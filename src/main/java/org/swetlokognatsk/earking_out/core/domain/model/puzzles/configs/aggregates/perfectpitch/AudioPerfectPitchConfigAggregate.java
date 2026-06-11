package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.aggregates.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public class AudioPerfectPitchConfigAggregate extends PerfectPitchConfigAggregate<AudioPerfectPitchConfig> {

    public AudioPerfectPitchConfigAggregate(final AudioPerfectPitchConfig puzzleConfig, final PianoKeyboardRepository pianoKeyboardRepository) {
        super(puzzleConfig, pianoKeyboardRepository);

    }

}
