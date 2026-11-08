package org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;

public class PianoKeyReleasedEvent extends PianoKeyActionEvent {
    private static final long serialVersionUID = 1L;

    public PianoKeyReleasedEvent(final UserId userId, final LocalDateTime timestamp, final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber pianoKeyNumber) {
        super(userId, timestamp, pianoKeyboardId, pianoKeyNumber);
    }

}
