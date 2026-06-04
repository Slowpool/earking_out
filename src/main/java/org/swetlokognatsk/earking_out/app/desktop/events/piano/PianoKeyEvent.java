package org.swetlokognatsk.earking_out.app.desktop.events.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import javafx.event.Event;
import javafx.event.EventType;

abstract class PianoKeyEvent extends Event {
    public final PianoKeyboardId pianoKeyboardId;
    public final Byte keyNumber;

    public PianoKeyEvent(final EventType<?> eventType, final PianoKeyboardId pianoKeyboardId, final Byte keyNumber) {
        super(eventType);

        this.pianoKeyboardId = pianoKeyboardId;
        this.keyNumber = keyNumber;

    }
}
