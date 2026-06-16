package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.base.DependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;

public final class PerfectPitchConfigDependentAggregatesDTO extends DependentAggregatesDTO {
    public static final PerfectPitchConfigDependentAggregatesDTO EMPTY = new PerfectPitchConfigDependentAggregatesDTO(new PianoKeyboardAggregate[0]);
    public final PianoKeyboardAggregate[] pianoKeyboardAggregates;

    public PerfectPitchConfigDependentAggregatesDTO(final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        this.pianoKeyboardAggregates = pianoKeyboardAggregates;
    }
}
