package org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;

public final class PianoKeyPressedEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    public final PianoKeyNumber pianoKeyNumber;
    public final PianoKeyboardId pianoKeyboardId;

    public PianoKeyPressedEvent(final LocalDateTime timestamp, final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber pianoKeyNumber) {
        super(timestamp);

        this.pianoKeyboardId = pianoKeyboardId;
        this.pianoKeyNumber = pianoKeyNumber;
    }

}
