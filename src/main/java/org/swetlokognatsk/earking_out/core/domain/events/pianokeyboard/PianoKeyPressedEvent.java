package org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.OwnedPianoKeyboardId;

public final class PianoKeyPressedEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    public final PianoKeyNumber pianoKeyNumber;
    public final OwnedPianoKeyboardId pianoKeyboardId;

    public PianoKeyPressedEvent(final UserId userId, final LocalDateTime timestamp, final OwnedPianoKeyboardId pianoKeyboardId, final PianoKeyNumber pianoKeyNumber) {
        super(userId, timestamp);

        this.pianoKeyboardId = pianoKeyboardId;
        this.pianoKeyNumber = pianoKeyNumber;
    }

}
