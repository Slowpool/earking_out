package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;

public final class TestInMemoryAllPianoKeyboardRepository extends InMemoryPianoKeyboardRepository {

    public TestInMemoryAllPianoKeyboardRepository(final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory) {
        super(pianoKeyboardAggregatesFactory);
    }

    protected PianoKeyboardId[] getPianoKeyboardIds() {
        return PianoKeyboardId.values();
    }

}
