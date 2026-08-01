package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public class AnyPianoKeyboardTest extends PianoKeyboardTest {

    protected PianoKeyboardId getSomeSuitablePianoKeyboardId() {
        return PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING;
    }

    @Test
    public void playSoundOnPress() {
        var pianoKeyboard = createPianoKeyboard();
        pianoKeyboard.pressKey(PianoKeyNumber.FIRST_NOTE_NUMBER);

        var events = pianoKeyboard.flushEvents();
        assertEquals(1, events.size());

        var event = events.getFirst();
        assertEquals(PianoKeyPressedEvent.class, event.getClass());
    }
}
