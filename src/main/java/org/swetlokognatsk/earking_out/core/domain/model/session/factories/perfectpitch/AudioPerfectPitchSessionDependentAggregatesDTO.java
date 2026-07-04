package org.swetlokognatsk.earking_out.core.domain.model.session.factories.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.base.DependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;

public final class AudioPerfectPitchSessionDependentAggregatesDTO extends DependentAggregatesDTO {
    public final PianoKeyboardAggregate[] pianoKeyboardAggregates;

    public AudioPerfectPitchSessionDependentAggregatesDTO(final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        this.pianoKeyboardAggregates = pianoKeyboardAggregates;
    }
}
