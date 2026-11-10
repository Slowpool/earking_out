package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import java.io.Serializable;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;

/**
 * Owned - means that some concrete piano keyboard belongs to some user.
 */
public record OwnedPianoKeyboardId(PianoKeyboardId pianoKeyboardId, UserId userId) implements Serializable {
    private static final long serialVersionUID = 1L;

}
