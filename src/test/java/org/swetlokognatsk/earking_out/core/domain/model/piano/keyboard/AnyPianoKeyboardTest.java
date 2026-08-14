package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;

public final class AnyPianoKeyboardTest extends PianoKeyboardTest {
    private static final PianoKeyNumber ANY_NOTE_NUMBER = FIRST_NOTE_NUMBER;

    protected PianoKeyboardId getSomeSuitablePianoKeyboardId() {
        return PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING;
    }

    private void ensureSomePianoKeysAreSelected(final PianoKeyboardAggregate pianoKeyboardAggregate) {
        if (pianoKeyboardAggregate.getSelectedKeyNumbers().length == 0) {
            pianoKeyboardAggregate.pressKey(FIRST_NOTE_NUMBER);
        }
    }

    private void ensureSomePianoKeysArePressed(final PianoKeyboardAggregate pianoKeyboardAggregate) {
        if (pianoKeyboardAggregate.getPressedPianoKeyNumber() == null) {
            pianoKeyboardAggregate.pressKey(ANY_NOTE_NUMBER);
        }
    }

    private void makeSomeEvents(final PianoKeyboardAggregate pianoKeyboardAggregate) {
        ensureSomePianoKeysAreSelected(pianoKeyboardAggregate);
        ensureSomePianoKeysArePressed(pianoKeyboardAggregate);
    }

    @Before
    public void setup() {
        DI.refreshDependencies();
    }

    @Test
    public void playSoundOnPress() {
        var pianoKeyboard = createPianoKeyboard();
        pianoKeyboard.pressKey(PianoKeyNumber.FIRST_NOTE_NUMBER);

        var events = pianoKeyboard.flushEvents();
        assertEquals(1, events.size());

        var event = events.getFirst();
        assertTrue(PianoKeyPressedEvent.class.equals(event.getClass()));
    }

    @Test
    public void selectedKeysDisappearAfterRefresh() {
        var pianoKeyboard = createPianoKeyboard();
        ensureSomePianoKeysAreSelected(pianoKeyboard);

        pianoKeyboard.resetState();

        assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    public void pressedKeysDisappearAfterRefresh() {
        var pianoKeyboard = createPianoKeyboard();
        ensureSomePianoKeysArePressed(pianoKeyboard);

        pianoKeyboard.resetState();

        assertNoPressedKeys(pianoKeyboard);
    }

    @Test
    public void thereAreNoEventsAfterEventsFlushing() {
        var pianoKeyboard = createPianoKeyboard();
        makeSomeEvents(pianoKeyboard);

        pianoKeyboard.resetState();

        assertDoesNotHaveEvents(pianoKeyboard);
    }
}
