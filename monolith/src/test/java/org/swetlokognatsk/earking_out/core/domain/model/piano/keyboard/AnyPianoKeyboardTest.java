package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyReleasedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.events.messagebroker.MessageBroker;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;

@EOSpringBootTest
public final class AnyPianoKeyboardTest extends PianoKeyboardTest {
    private static final PianoKeyNumber ANY_NOTE_NUMBER = FIRST_NOTE_NUMBER;

    private PianoKeyboardTestHelper pianoKeyboardHelper;

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

    @BeforeEach
    void setup() {
        pianoKeyboardHelper = DI.get(PianoKeyboardTestHelper.class);
    }

    @Test
    void createEventOnKeyPress() {
        var pianoKeyboard = createPianoKeyboard();
        pianoKeyboard.pressKey(PianoKeyNumber.FIRST_NOTE_NUMBER);

        var events = pianoKeyboard.flushEvents();

        assertEquals(1, events.size());
        var event = events.getFirst();
        assertEquals(PianoKeyPressedEvent.class, event.getClass());
    }

    @Test
    void createEventOnKeyRelease() {
        var pianoKeyboard = createPianoKeyboard();
        pianoKeyboard.pressKey(PianoKeyNumber.FIRST_NOTE_NUMBER);
        pianoKeyboard.flushEvents();
        pianoKeyboard.releaseKey();

        var events = pianoKeyboard.flushEvents();

        assertEquals(1, events.size());
        var event = events.getFirst();
        assertEquals(PianoKeyReleasedEvent.class, event.getClass());
    }

    @Test
    void selectedKeysDisappearAfterRefresh() {
        var pianoKeyboard = createPianoKeyboard();
        ensureSomePianoKeysAreSelected(pianoKeyboard);

        pianoKeyboard.resetState();

        pianoKeyboardHelper.assertNoSelectedKeys(pianoKeyboard);
    }

    @Test
    void pressedKeysDisappearAfterRefresh() {
        var pianoKeyboard = createPianoKeyboard();
        ensureSomePianoKeysArePressed(pianoKeyboard);

        pianoKeyboard.resetState();

        pianoKeyboardHelper.assertNoPressedKeys(pianoKeyboard);
    }

    @Test
    void thereAreNoEventsAfterEventsFlushing() {
        var pianoKeyboard = createPianoKeyboard();
        makeSomeEvents(pianoKeyboard);

        pianoKeyboard.resetState();

        pianoKeyboardHelper.assertDoesNotHaveEvents(pianoKeyboard);
    }
}
