package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;

public final class PianoKeyboardAggregatesFactory extends AggregatesFactory<PianoKeyboardAggregate> {

    private final PianoKeysFactory pianoKeysFactory;

    public PianoKeyboardAggregatesFactory(final ObjectCloner cloner, final PianoKeysFactory pianoKeysFactory) {
        super(cloner);
        this.pianoKeysFactory = pianoKeysFactory;
    }

    public PianoKeyboardAggregate create(final PianoKeyboardId id) {
        return create(id, new PianoKeyNumber[0]);
    }

    public PianoKeyboardAggregate create(final PianoKeyboardId id, final PianoKeyNumber[] selectedKeys) {
        return new PianoKeyboardAggregate(id, selectedKeys, pianoKeysFactory);
    }
}
