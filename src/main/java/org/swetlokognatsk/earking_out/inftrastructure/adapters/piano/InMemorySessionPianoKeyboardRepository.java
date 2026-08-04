package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.piano.SessionPianoKeyboardStorageAdapter;

public final class InMemorySessionPianoKeyboardRepository extends InMemoryPianoKeyboardRepository implements SessionPianoKeyboardStorageAdapter {
    private static final PianoKeyboardId[] pianoKeyboardIds = new PianoKeyboardId[] { AUDIO_PERFECT_PITCH_NOTES_GUESSING };

    public InMemorySessionPianoKeyboardRepository(final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory) {
        super(pianoKeyboardAggregatesFactory);
    }

    protected PianoKeyboardId[] getPianoKeyboardIds() {
        return pianoKeyboardIds;
    }

}
