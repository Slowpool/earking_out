package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.swetlokognatsk.earking_out.core.domain.model.base.DependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.base.Factory;

public final class PianoKeyboardAggregatesFactory implements Factory<PianoKeyboardAggregate, DependentAggregatesDTO> {

    public PianoKeyboardAggregate createDefault(final DependentAggregatesDTO dependentAggregates) {
        throw new RuntimeException("not implemented");
    }
}
