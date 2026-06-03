package org.swetlokognatsk.earking_out.core.domain.model.piano;

import static org.junit.Assert.*;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;

import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.PianoKeyboardTestHelper.*;

public final class PianoKeyboardOneKeySelectTest extends PianoKeyboardTest {

    protected PianoKeyboardId getSomeSuitablePianoKeyboardId() {
        return PianoKeyboardId.ROOT_NOTE_PICKER;
    }

    @Test
    public void initKeyboardWithoutKeys() {
        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    // TODO use https://github.com/piotr-yuxuan/custom-ignore-annotation/
    public void initKeyboardWithKey() {
        var presetKeys = new byte[] { FIRST_NOTE_NUMBER };
        var pianoKeyboard = createPianoKeyboard(presetKeys);

        assertOnlyTheseKeysAreSelected(presetKeys, pianoKeyboard);
    }

    @Test
    public void initKeyboardWithKeys() {
        var presetKeys = new byte[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER + 1 };
        try {
            createPianoKeyboard(presetKeys);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void touchOneKey() {
        byte pianoKey = FIRST_NOTE_NUMBER;

        pianoKeyboard.touchKey(pianoKey);

        assertOnlyThisKeyIsSelected(pianoKey, pianoKeyboard);
    }

    @Test
    public void touchTheSameNote() {
        byte pianoKey = FIRST_NOTE_NUMBER;

        final int NUMBER_OF_TOUCHES = 2;
        for (int i = 0; i < NUMBER_OF_TOUCHES; i++) {
            pianoKeyboard.touchKey(pianoKey);
        }

        assertOnlyThisKeyIsSelected(pianoKey, pianoKeyboard);
    }

    @Test
    public void touchNonExistingKeys() {
        var nonExistingPianoKeys = new byte[] { FIRST_NOTE_NUMBER - 1, (byte) (FIRST_NOTE_NUMBER + PIANO_KEYS_NUMBER) };

        for (var nonExistingPianoKey : nonExistingPianoKeys) {
            try {
                pianoKeyboard.touchKey(nonExistingPianoKey);
                fail();
            } catch (IndexOutOfBoundsException e) {
            }
        }
    }

    @Test
    public void touchSeveralKeys() {
        var pianoKeys = new byte[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER + 1 };

        for (var pianoKey : pianoKeys) {
            pianoKeyboard.touchKey(pianoKey);
        }

        assertOnlyThisKeyIsSelected(pianoKeys[pianoKeys.length - 1], pianoKeyboard);
    }

    @Test
    public void initWithDuplicateKeys() {
        var presetKeys = new byte[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER };
        try {
            createPianoKeyboard(presetKeys);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

}
