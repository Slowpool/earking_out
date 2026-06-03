package org.swetlokognatsk.earking_out.core.domain.model.piano;

import org.junit.Test;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.PianoKeyboardTestHelper.*;

public final class PianoKeyboardSeveralKeysSelect extends PianoKeyboardTest {

    protected PianoKeyboardId getSomeSuitablePianoKeyboardId() {
        return PianoKeyboardId.PERFECT_PITCH_NOTES_PICKER;
    }

    @Test
    public void initKeyboardWithoutKeys() {
        assertNoSelectedKeys(pianoKeyboard);
    }
}
