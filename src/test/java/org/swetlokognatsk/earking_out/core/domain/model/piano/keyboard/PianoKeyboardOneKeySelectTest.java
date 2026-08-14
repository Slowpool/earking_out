package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;

public final class PianoKeyboardOneKeySelectTest extends PianoKeyboardTest {

    protected PianoKeyboardId getSomeSuitablePianoKeyboardId() {
        return PianoKeyboardId.AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER;
    }

    @Test
    public void initKeyboardWithoutKeys() {
        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void initKeyboardWithKey() {
        var presetKeys = new PianoKeyNumber[] { ANY_PIANO_KEY_NUMBER };
        var pianoKeyboard = createPianoKeyboard(presetKeys);

        assertOnlyTheseKeysAreSelected(presetKeys, pianoKeyboard);
    }

    @Test
    public void initKeyboardWithKeys() {
        var presetKeys = new PianoKeyNumber[] { ANY_PIANO_KEY_NUMBER, ANY_PIANO_KEY_NUMBER.increment() };
        try {
            createPianoKeyboard(presetKeys);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void touchOneKey() {
        PianoKeyNumber keyNumber = ANY_PIANO_KEY_NUMBER;

        pianoKeyboard.touchKey(keyNumber);

        assertOnlyThisKeyIsSelected(keyNumber, pianoKeyboard);
    }

    @Test
    public void touchTheSameNote() {
        PianoKeyNumber keyNumber = ANY_PIANO_KEY_NUMBER;

        final int NUMBER_OF_TOUCHES = 2;
        for (int i = 0; i < NUMBER_OF_TOUCHES; i++) {
            pianoKeyboard.touchKey(keyNumber);
        }

        assertOnlyThisKeyIsSelected(keyNumber, pianoKeyboard);
    }

    @Test
    public void touchSeveralKeys() {
        var pianoKeys = new PianoKeyNumber[] { ANY_PIANO_KEY_NUMBER, ANY_PIANO_KEY_NUMBER.increment() };

        for (var pianoKey : pianoKeys) {
            pianoKeyboard.touchKey(pianoKey);
        }

        assertOnlyThisKeyIsSelected(pianoKeys[pianoKeys.length - 1], pianoKeyboard);
    }

    @Test
    public void initWithDuplicateKeys() {
        var presetKeys = new PianoKeyNumber[] { ANY_PIANO_KEY_NUMBER, ANY_PIANO_KEY_NUMBER };
        try {
            createPianoKeyboard(presetKeys);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void pianoKeyRemainsSelectedAfterRelease() {
        // arrange
        var pianoKeyboard = createPianoKeyboard();
        pianoKeyboard.pressKey(ANY_PIANO_KEY_NUMBER);
        // action
        pianoKeyboard.releaseKey();
        // assert
        assertOnlyThisKeyIsSelected(ANY_PIANO_KEY_NUMBER, pianoKeyboard);
    }

    /**
     * see
     * {@link org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardOneKeySelectTest.pianoKeyRemainsSelectedAfterTouchAndRelease}
     * why there're such an indents between lines
     */
    @Test
    public void pianoKeyRemainsSelectedAfterTouchAndRelease() {
        var pianoKeyboard = createPianoKeyboard();
        pianoKeyboard.touchKey(ANY_PIANO_KEY_NUMBER);
        pianoKeyboard.pressKey(ANY_PIANO_KEY_NUMBER);

        pianoKeyboard.releaseKey();

        assertOnlyThisKeyIsSelected(ANY_PIANO_KEY_NUMBER, pianoKeyboard);
    }

    /**
     * see
     * {@link org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardOneKeySelectTest.pianoKeyRemainsSelectedAfterTouchAndRelease}
     * why there're such an indents between lines
     */
    @Test
    public void pianoKeyRemainsSelectedAfterTouchOneAndReleaseAnotherKey() {
        var pianoKeyboard = createPianoKeyboard();
        pianoKeyboard.touchKey(ANY_PIANO_KEY_NUMBER);
        var nextPianoKeyNumber = ANY_PIANO_KEY_NUMBER.increment();
        pianoKeyboard.pressKey(nextPianoKeyNumber);

        pianoKeyboard.releaseKey();

        assertOnlyThisKeyIsSelected(nextPianoKeyNumber, pianoKeyboard);
    }

    @Test
    public void restoreSeveralSelectedKeys() {
        try {
            pianoKeyboard.restoreSelectedKeys(SOME_PIANO_KEYS);
            fail();
        }
        // TODO throw different exceptions here for all piano keyboard modes
        // only one key can be selected
        catch (IllegalStateException e) {
        }
    }

    @Test
    public void restoreSelectedKey() {
        var pianoKey = ANY_PIANO_KEY_NUMBER;
        pianoKeyboard.restoreSelectedKey(pianoKey);

        assertOnlyThisKeyIsSelected(pianoKey, pianoKeyboard);
    }

    @Test
    public void restoreSelectedKeyDoesNotAddPressedKey() {
        pianoKeyboard.restoreSelectedKey(ANY_PIANO_KEY_NUMBER);

        assertNull(pianoKeyboard.getPressedPianoKeyNumber());
    }

    @Test
    public void restoreSelectedKeyEvents() {
        pianoKeyboard.flushEvents();

        pianoKeyboard.restoreSelectedKey(ANY_PIANO_KEY_NUMBER);

        var numberOfEvents = pianoKeyboard.flushEvents().size();
        assertEquals(0, numberOfEvents);
    }

    @Test
    public void restoreSelectedKeysWhenSomeAnotherKeyIsSelected() {
        pianoKeyboard.touchKey(ANY_PIANO_KEY_NUMBER);

        try {
            pianoKeyboard.restoreSelectedKey(ANY_ANOTHER_PIANO_KEY_NUMBER);
            fail();
        }
        // TODO throw different exceptions here for all piano keyboard modes
        catch (IllegalStateException e) {
        }
    }

}
