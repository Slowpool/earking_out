package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PianoKeyboardOneKeyTouchTest extends PianoKeyboardTest {

    protected PianoKeyboardId getSomeSuitablePianoKeyboardId() {
        return PianoKeyboardId.PERFECT_PITCH_NOTES_GUESSING;
    }

    @Test
    public void initWithoutSelectedKeys() {
        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void initWithSelectedKey() {
        try {
            createPianoKeyboard(new PianoKeyNumber[] { FIRST_NOTE_NUMBER });
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void initWithSelectedKeys() {
        try {
            createPianoKeyboard(new PianoKeyNumber[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER.increment() });
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void touchOneKey() {
        PianoKeyNumber pianoKey = FIRST_NOTE_NUMBER;

        pianoKeyboard.touchKey(pianoKey);

        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void pressOneKey() {
        PianoKeyNumber pianoKey = FIRST_NOTE_NUMBER;

        pianoKeyboard.pressKey(pianoKey);
        assertOnlyThisKeyIsSelected(pianoKey, pianoKeyboard);

        pianoKeyboard.releaseKey();
        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void pressKeyTwice() {
        PianoKeyNumber pianoKey = FIRST_NOTE_NUMBER;

        pianoKeyboard.pressKey(pianoKey);
        try {
            pianoKeyboard.pressKey(pianoKey);
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void pressTwoKeys() {
        PianoKeyNumber[] pianoKeys = new PianoKeyNumber[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER.increment() };

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
