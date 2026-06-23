package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;

public final class PianoKeyboardSeveralKeysSelect extends PianoKeyboardTest {

    protected PianoKeyboardId getSomeSuitablePianoKeyboardId() {
        return PianoKeyboardId.PERFECT_PITCH_NOTES_PICKER;
    }

    @Test
    public void initKeyboardWithoutKeys() {
        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void initKeyboardWithOneKey() {
        var pianoKey = FIRST_NOTE_NUMBER;
        pianoKeyboard = createPianoKeyboard(new PianoKeyNumber[] { pianoKey });

        assertOnlyThisKeyIsSelected(pianoKey, pianoKeyboard);
    }

    @Test
    public void initKeyboardWithSeveralKeys() {
        var selectedPianoKeys = new PianoKeyNumber[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER.increment() };
        pianoKeyboard = createPianoKeyboard(selectedPianoKeys);

        assertOnlyTheseKeysAreSelected(selectedPianoKeys, pianoKeyboard);

    }

    @Test
    public void touchingSeveralKeys() {
        var pianoKeys = new PianoKeyNumber[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER.increment() };

        pianoKeyboard.touchKey(pianoKeys[0]);
        pianoKeyboard.touchKey(pianoKeys[1]);

        assertOnlyTheseKeysAreSelected(pianoKeys, pianoKeyboard);
    }

    @Test
    public void touchTheSameKeyTwice() {
        var pianoKey = FIRST_NOTE_NUMBER;

        pianoKeyboard.touchKey(pianoKey);
        pianoKeyboard.touchKey(pianoKey);

        assertNoSelectedKeys(pianoKeyboard);
    }
}
