package org.swetlokognatsk.earking_out.app.desktop.events.piano;

import javafx.event.EventType;

public final class PianoKeyPressedEvent extends PianoKeyEvent {
    public static final EventType<PianoKeyPressedEvent> PIANO_KEY_PRESSED = new EventType<>("PIANO_KEY_PRESSED");

    public PianoKeyPressedEvent(final EventType<?> eventType, final Byte keyNumber) {
        super(eventType, keyNumber);

    }
}
