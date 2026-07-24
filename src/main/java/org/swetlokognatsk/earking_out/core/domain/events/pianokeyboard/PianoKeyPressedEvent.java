package org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PianoKeyPressedEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    public final PianoKeyNumber pianoKeyNumber;

    public PianoKeyPressedEvent(final LocalDateTime timestamp, final PianoKeyNumber pianoKeyNumber) {
        super(timestamp);
        this.pianoKeyNumber = pianoKeyNumber;
    }

}
