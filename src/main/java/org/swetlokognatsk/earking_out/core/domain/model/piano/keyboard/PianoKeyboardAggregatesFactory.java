package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PianoKeyboardAggregatesFactory extends AggregatesFactory<PianoKeyboardAggregate> {

    public PianoKeyboardAggregate create(final PianoKeyboardId id) {
        return create(id, new PianoKeyNumber[0]);
    }

    public PianoKeyboardAggregate create(final PianoKeyboardId id, final PianoKeyNumber[] selectedKeys) {
        return new PianoKeyboardAggregate(id, selectedKeys);
    }
}
