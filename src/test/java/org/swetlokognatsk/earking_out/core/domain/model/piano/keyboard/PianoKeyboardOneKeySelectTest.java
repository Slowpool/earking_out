package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;

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
        var presetKeys = new PianoKeyNumber[] { FIRST_NOTE_NUMBER };
        var pianoKeyboard = createPianoKeyboard(presetKeys);

        assertOnlyTheseKeysAreSelected(presetKeys, pianoKeyboard);
    }

    @Test
    public void initKeyboardWithKeys() {
        var presetKeys = new PianoKeyNumber[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER.increment() };
        try {
            createPianoKeyboard(presetKeys);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void touchOneKey() {
        PianoKeyNumber keyNumber = FIRST_NOTE_NUMBER;

        pianoKeyboard.touchKey(keyNumber);

        assertOnlyThisKeyIsSelected(keyNumber, pianoKeyboard);
    }

    @Test
    public void touchTheSameNote() {
        PianoKeyNumber keyNumber = FIRST_NOTE_NUMBER;

        final int NUMBER_OF_TOUCHES = 2;
        for (int i = 0; i < NUMBER_OF_TOUCHES; i++) {
            pianoKeyboard.touchKey(keyNumber);
        }

        assertOnlyThisKeyIsSelected(keyNumber, pianoKeyboard);
    }

    @Test
    public void touchSeveralKeys() {
        var pianoKeys = new PianoKeyNumber[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER.increment() };

        for (var pianoKey : pianoKeys) {
            pianoKeyboard.touchKey(pianoKey);
        }

        assertOnlyThisKeyIsSelected(pianoKeys[pianoKeys.length - 1], pianoKeyboard);
    }

    @Test
    public void initWithDuplicateKeys() {
        var presetKeys = new PianoKeyNumber[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER };
        try {
            createPianoKeyboard(presetKeys);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

}
