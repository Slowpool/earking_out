package org.swetlokognatsk.earking_out.app.desktop.events.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import javafx.event.EventType;

public final class PianoKeyReleasedEvent extends PianoKeyEvent {
    public static final EventType<PianoKeyPressedEvent> PIANO_KEY_RELEASED = new EventType<>("PIANO_KEY_RELEASED");

    public PianoKeyReleasedEvent(final EventType<?> eventType, final PianoKeyboardId pianoKeyboardId, final Byte keyNumber) {
        super(eventType, pianoKeyboardId, keyNumber);

    }
}
