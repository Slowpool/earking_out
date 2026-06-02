package org.swetlokognatsk.earking_out.core.domain.model.piano;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.PianoKeyboardTestHelper.*;
import org.junit.Before;
import org.junit.Test;

public final class PianoKeyboardOneKeyTouchTest {

    private PianoKeyboardAggregate pianoKeyboard;

    @Before
    public void before() {
        pianoKeyboard = createPianoKeyboard(PianoKeyboardMode.ONE_KEY_TOUCH);

    }

    @Test
    public void initWithoutSelectedKeys() {

        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void initWithSelectedKey() {
        try {
            createPianoKeyboard(PianoKeyboardMode.ONE_KEY_TOUCH, new byte[] { FIRST_NOTE_NUMBER });
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void initWithSelectedKeys() {
        try {
            createPianoKeyboard(PianoKeyboardMode.ONE_KEY_TOUCH, new byte[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER + 1 });
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void touchOneKey() {
        byte pianoKey = FIRST_NOTE_NUMBER;

        pianoKeyboard.touchKey(pianoKey);

        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void pressOneKey() {
        byte pianoKey = FIRST_NOTE_NUMBER;

        pianoKeyboard.pressKey(pianoKey);
        assertOnlyTheseKeysAreSelected(pianoKey, pianoKeyboard);

        pianoKeyboard.releaseKey();
        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void pressKeyTwice() {
        byte pianoKey = FIRST_NOTE_NUMBER;

        pianoKeyboard.pressKey(pianoKey);
        try {
            pianoKeyboard.pressKey(pianoKey);
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void pressTwoKeys() {
        byte[] pianoKeys = new byte[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER + 1 };

        pianoKeyboard.pressKey(pianoKeys[0]);
        try {
            pianoKeyboard.pressKey(pianoKeys[1]);
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void releaseWithoutPressing() {
        try {
            pianoKeyboard.releaseKey();
            fail();
        } catch (IllegalStateException e) {
        }

    }
}
