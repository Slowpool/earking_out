package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PianoKeyboardOneKeyTouchTest extends PianoKeyboardTest {

    protected PianoKeyboardId getSomeSuitablePianoKeyboardId() {
        return PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING;
    }

    @Test
    public void initWithoutSelectedKeys() {
        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void initWithSelectedKey() {
        try {
            createPianoKeyboard(new PianoKeyNumber[] { ANY_PIANO_KEY_NUMBER });
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void initWithSelectedKeys() {
        var pianoKeyNumber = ANY_PIANO_KEY_NUMBER;
        var severalSelectedKeys = new PianoKeyNumber[] { pianoKeyNumber, pianoKeyNumber.increment() };
        try {
            createPianoKeyboard(severalSelectedKeys);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void selectedKeysAfterOneKeyTouching() {
        pianoKeyboard.touchKey(ANY_PIANO_KEY_NUMBER);

        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void selectedKeysAfterPressing() {
        PianoKeyNumber pianoKey = ANY_PIANO_KEY_NUMBER;

        pianoKeyboard.pressKey(pianoKey);

        assertOnlyThisKeyIsSelected(pianoKey, pianoKeyboard);
    }

    @Test
    public void selectedKeysAfterReleasing() {
        PianoKeyNumber pianoKey = ANY_PIANO_KEY_NUMBER;
        pianoKeyboard.pressKey(pianoKey);

        pianoKeyboard.releaseKey();

        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void pressKeyTwice() {
        PianoKeyNumber pianoKey = ANY_PIANO_KEY_NUMBER;

        pianoKeyboard.pressKey(pianoKey);
        try {
            pianoKeyboard.pressKey(pianoKey);
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void pressTwoKeys() {
        var pianoKeyNumber = ANY_PIANO_KEY_NUMBER;
        PianoKeyNumber[] pianoKeys = new PianoKeyNumber[] { pianoKeyNumber, pianoKeyNumber.increment() };

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

    @Test
    public void restoreSeveralSelectedKeys() {
        try {
            pianoKeyboard.restoreSelectedKeys(SOME_PIANO_KEYS);
            fail();
        }
        // one key touch cannot have selected keys. if any key is selected, it also must be pressed, whereas restoreSelectedKeys mustn't do anything with pressed key
        catch (IllegalStateException e) {
        }
    }

    @Test
    public void restoreSelectedKeysWhenSomeOtherKeysAreSelected() {
        pianoKeyboard.pressKey(ANY_PIANO_KEY_NUMBER);

        try {
            pianoKeyboard.restoreSelectedKey(ANY_ANOTHER_PIANO_KEY_NUMBER);
            fail();
        }
        // one key touch cannot have selected keys. if any key is selected, it also must be pressed, whereas restoreSelectedKeys mustn't do anything with pressed key
        catch (IllegalStateException e) {
        }
    }
}
