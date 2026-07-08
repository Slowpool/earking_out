package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.swetlokognatsk.earking_out.core.domain.model.base.DependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.base.Factory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PianoKeyboardAggregatesFactory extends Factory<PianoKeyboardAggregate, DependentAggregatesDTO> {

    public PianoKeyboardAggregate createDefault(final DependentAggregatesDTO dependentAggregates) {
        throw new RuntimeException("not implemented");
    }

    public PianoKeyboardAggregate create(final PianoKeyboardId id) {
        return create(id, new PianoKeyNumber[0]);
    }

    public PianoKeyboardAggregate create(final PianoKeyboardId id, final PianoKeyNumber[] selectedKeys) {
        return new PianoKeyboardAggregate(id, selectedKeys);
    }
}
