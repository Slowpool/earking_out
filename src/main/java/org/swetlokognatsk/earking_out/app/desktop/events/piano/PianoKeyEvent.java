package org.swetlokognatsk.earking_out.app.desktop.events.piano;

import javafx.event.Event;
import javafx.event.EventType;

abstract class PianoKeyEvent extends Event {
    public final Byte keyNumber;

    public PianoKeyEvent(final EventType<?> eventType, final Byte keyNumber) {
        super(eventType);

        this.keyNumber = keyNumber;

    }
}
