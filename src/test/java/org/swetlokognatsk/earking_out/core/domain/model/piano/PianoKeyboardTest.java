package org.swetlokognatsk.earking_out.core.domain.model.piano;

import static org.junit.Assert.*;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;

public final class PianoKeyboardTest {
    protected PianoKeyboard createPianoKeyboard(PianoKeyboardMode mode) {
        var pianoKeyboard = new PianoKeyboard(mode);
        return pianoKeyboard;
    }

    protected PianoKeyboard createPianoKeyboard(PianoKeyboardMode mode, byte[] selectedKeys) {
        var pianoKeyboard = new PianoKeyboard(mode, selectedKeys);
        return pianoKeyboard;
    }

    protected void assertOnlyTheseKeysAreSelected(byte[] pianoKeys, PianoKeyboard pianoKeyboard) {
        var selectedPianoKeys = pianoKeyboard.getSelectedKeyNumbers();

        assertEquals(pianoKeys.length, selectedPianoKeys.length);
        for (var pianoKey : pianoKeys) {
            assertTrue(ArrayUtils.contains(selectedPianoKeys, pianoKey));
        }
    }

    protected void assertOnlyTheseKeysAreSelected(byte key, PianoKeyboard pianoKeyboard) {
        assertOnlyTheseKeysAreSelected(new byte[] { key }, pianoKeyboard);
    }

    @Test
    public void oneKeySelectModeKeyboardInitWithKey() {
        var presetKeys = new byte[] { FIRST_NOTE_NUMBER };
        var pianoKeyboard = createPianoKeyboard(PianoKeyboardMode.ONE_KEY_SELECT, presetKeys);

        assertOnlyTheseKeysAreSelected(presetKeys, pianoKeyboard);
    }

    @Test
    public void oneKeySelectModeKeyboardInitWithKeys() {
        var presetKeys = new byte[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER + 1 };
        try {
            createPianoKeyboard(PianoKeyboardMode.ONE_KEY_SELECT, presetKeys);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void oneKeySelectModeOneKeyTouching() {
        var pianoKeyboard = createPianoKeyboard(PianoKeyboardMode.ONE_KEY_SELECT);
        byte pianoKey = FIRST_NOTE_NUMBER;

        pianoKeyboard.touchKey(pianoKey);

        assertOnlyTheseKeysAreSelected(pianoKey, pianoKeyboard);
    }

    @Test
    public void oneKeySelectModeTouchingTheSameNote() {
        var pianoKeyboard = createPianoKeyboard(PianoKeyboardMode.ONE_KEY_SELECT);
        byte pianoKey = FIRST_NOTE_NUMBER;

        final int NUMBER_OF_TOUCHES = 2;
        for (int i = 0; i < NUMBER_OF_TOUCHES; i++) {
            pianoKeyboard.touchKey(pianoKey);
        }

        assertOnlyTheseKeysAreSelected(pianoKey, pianoKeyboard);
    }

    @Test
    public void touchNonExistingKeys() {
        var pianoKeyboard = createPianoKeyboard(PianoKeyboardMode.ONE_KEY_SELECT);
        var nonExistingPianoKeys = new byte[] { FIRST_NOTE_NUMBER - 1, (byte)(FIRST_NOTE_NUMBER + PIANO_KEYS_NUMBER) };

        for (var nonExistingPianoKey : nonExistingPianoKeys) {
            try {
                pianoKeyboard.touchKey(nonExistingPianoKey);
                fail();
            } catch (IllegalArgumentException e) {
            }
        }
    }

    @Test
    public void oneKeySelectModeSeveralKeysTouching() {
        var pianoKeyboard = createPianoKeyboard(PianoKeyboardMode.ONE_KEY_SELECT);
        var pianoKeys = new byte[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER + 1 };

        for (var pianoKey : pianoKeys) {
            pianoKeyboard.touchKey(pianoKey);
        }

        assertOnlyTheseKeysAreSelected(pianoKeys[pianoKeys.length - 1], pianoKeyboard);
    }

    @Test
    public void oneKeySelectModeInitWithDuplicateKeys() {
        // TODO
        fail();
    }

}
