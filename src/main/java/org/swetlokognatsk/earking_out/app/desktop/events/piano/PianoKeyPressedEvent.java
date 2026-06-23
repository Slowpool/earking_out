package org.swetlokognatsk.earking_out.app.desktop.events.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;

import javafx.event.EventType;

public final class PianoKeyPressedEvent extends PianoKeyEvent {
    public static final EventType<PianoKeyPressedEvent> PIANO_KEY_PRESSED = new EventType<>("PIANO_KEY_PRESSED");

    public PianoKeyPressedEvent(final EventType<?> eventType, final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber keyNumber) {
        super(eventType, pianoKeyboardId, keyNumber);

    }
}
