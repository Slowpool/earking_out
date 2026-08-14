package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;

public final class PianoKeyboardSeveralKeysSelect extends PianoKeyboardTest {

    protected PianoKeyboardId getSomeSuitablePianoKeyboardId() {
        return PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_PICKER;
    }

    @Test
    public void initKeyboardWithoutKeys() {
        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void initKeyboardWithOneKey() {
        var pianoKey = ANY_PIANO_KEY_NUMBER;
        pianoKeyboard = createPianoKeyboard(new PianoKeyNumber[] { pianoKey });

        assertOnlyThisKeyIsSelected(pianoKey, pianoKeyboard);
    }

    @Test
    public void initKeyboardWithSeveralKeys() {
        var selectedPianoKeys = new PianoKeyNumber[] { ANY_PIANO_KEY_NUMBER, ANY_PIANO_KEY_NUMBER.increment() };
        pianoKeyboard = createPianoKeyboard(selectedPianoKeys);

        assertOnlyTheseKeysAreSelected(selectedPianoKeys, pianoKeyboard);
    }

    @Test
    public void selectedKeysAfterRelease() {
        var pianoKey = ANY_PIANO_KEY_NUMBER;
        pianoKeyboard.pressKey(pianoKey);

        pianoKeyboard.releaseKey();

        assertOnlyThisKeyIsSelected(pianoKey, pianoKeyboard);
    }

    @Test
    public void touchingSeveralKeys() {
        var pianoKeys = new PianoKeyNumber[] { ANY_PIANO_KEY_NUMBER, ANY_PIANO_KEY_NUMBER.increment() };

        pianoKeyboard.touchKey(pianoKeys[0]);
        pianoKeyboard.touchKey(pianoKeys[1]);

        assertOnlyTheseKeysAreSelected(pianoKeys, pianoKeyboard);
    }

    @Test
    public void touchTheSameKeyTwice() {
        var pianoKey = ANY_PIANO_KEY_NUMBER;

        pianoKeyboard.touchKey(pianoKey);
        pianoKeyboard.touchKey(pianoKey);

        assertNoSelectedKeys(pianoKeyboard);
    }

    // TODO 11 tests are expected to be wrong
    @Test
    public void restoreSeveralSelectedKeys() {
        pianoKeyboard.restoreSelectedKeys(SOME_PIANO_KEYS);

        assertOnlyTheseKeysAreSelected(SOME_PIANO_KEYS, pianoKeyboard);
    }

    @Test
    public void restoreSeveralSelectedKeysDoesNotAddPressedKey() {
        pianoKeyboard.restoreSelectedKeys(SOME_PIANO_KEYS);

        assertNull(pianoKeyboard.getPressedPianoKeyNumber());
    }

    @Test
    public void restoreSeveralSelectedKeysEvents() {
        pianoKeyboard.flushEvents();

        pianoKeyboard.restoreSelectedKeys(SOME_PIANO_KEYS);

        var numberOfEvents = pianoKeyboard.flushEvents().size();
        assertEquals(0, numberOfEvents);
    }

    @Test
    public void restoreSelectedKeyWhenSomeAnotherKeyIsSelected() {
        pianoKeyboard.touchKey(ANY_PIANO_KEY_NUMBER);

        try {
            pianoKeyboard.restoreSelectedKey(ANY_ANOTHER_PIANO_KEY_NUMBER);
            fail();
        }
        // TODO it actually can be implemented somehow, but i cannot imagine situation when it's useful
        catch (IllegalStateException e) {
        }
    }

    @Test
    public void restoreSelectedKeysWithNullKey() {
        try {
            pianoKeyboard.restoreSelectedKeys(new PianoKeyNumber[] { ANY_PIANO_KEY_NUMBER, null, ANY_ANOTHER_PIANO_KEY_NUMBER });
            fail();
        } catch (NullPointerException e) {
        }
    }

}
