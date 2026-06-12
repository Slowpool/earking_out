package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class VisualPerfectPitchConfigAggregatesFactory extends PuzzleConfigAggregatesFactory<VisualPerfectPitchConfigAggregate> {

    public VisualPerfectPitchConfigAggregate createDefault() {
        return new VisualPerfectPitchConfigAggregate(0, true, new byte[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO, getPianoKeyboardRepository());
    }

    // TODO exterminate DRY violation (this is copied from AudioPerfectPitch...)
    protected static PianoKeyboardRepository getPianoKeyboardRepository() {
        return DI.get(PianoKeyboardRepository.class);
    }
}
