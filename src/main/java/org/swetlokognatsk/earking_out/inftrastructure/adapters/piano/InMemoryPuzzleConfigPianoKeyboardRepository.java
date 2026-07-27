package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId.*;

import org.springframework.context.annotation.Lazy;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.piano.PuzzleConfigPianoKeyboardStorageAdapter;

public final class InMemoryPuzzleConfigPianoKeyboardRepository extends InMemoryPianoKeyboardRepository implements PuzzleConfigPianoKeyboardStorageAdapter {
    protected static final PianoKeyboardId[] pianoKeyboardIds = new PianoKeyboardId[] { AUDIO_PERFECT_PITCH_NOTES_PICKER, AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER };

    public InMemoryPuzzleConfigPianoKeyboardRepository(final PianoKeyboardAggregatesFactory pianoKeyboardAggregatesFactory) {
        super(pianoKeyboardAggregatesFactory);
    }

    protected PianoKeyboardId[] getPianoKeyboardIds() {
        return pianoKeyboardIds;
    }

}
