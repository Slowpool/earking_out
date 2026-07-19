package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PianoKeyboardAggregatesFactory extends AggregatesFactory<PianoKeyboardAggregate> {

    public PianoKeyboardAggregate create(final PianoKeyboardId id, final PianoKeyboardSoundMode soundMode) {
        return create(id, new PianoKeyNumber[0], soundMode);
    }

    public PianoKeyboardAggregate create(final PianoKeyboardId id, final PianoKeyNumber[] selectedKeys, final PianoKeyboardSoundMode soundMode) {
        return new PianoKeyboardAggregate(id, selectedKeys, soundMode);
    }
}
