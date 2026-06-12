package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class AudioPerfectPitchConfigAggregatesFactory extends PuzzleConfigAggregatesFactory<AudioPerfectPitchConfigAggregate> {

    public AudioPerfectPitchConfigAggregate createDefault() {
        return new AudioPerfectPitchConfigAggregate(0, true, new byte[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO, getPianoKeyboardRepository());
    }

    protected static PianoKeyboardRepository getPianoKeyboardRepository() {
        return DI.get(PianoKeyboardRepository.class);
    }
}
