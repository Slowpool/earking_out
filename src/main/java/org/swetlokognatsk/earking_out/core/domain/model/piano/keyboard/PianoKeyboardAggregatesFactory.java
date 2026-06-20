package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.swetlokognatsk.earking_out.core.domain.model.base.DependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.base.Factory;

public final class PianoKeyboardAggregatesFactory implements Factory<PianoKeyboardAggregate, DependentAggregatesDTO> {

    public PianoKeyboardAggregate createDefault(final DependentAggregatesDTO dependentAggregates) {
        throw new RuntimeException("not implemented");
    }

    public PianoKeyboardAggregate create(final PianoKeyboardId id) {
        return create(id, new byte[0]);
    }

    public PianoKeyboardAggregate create(final PianoKeyboardId id, final byte[] selectedKeys) {
        return new PianoKeyboardAggregate(id, selectedKeys);
    }
}
