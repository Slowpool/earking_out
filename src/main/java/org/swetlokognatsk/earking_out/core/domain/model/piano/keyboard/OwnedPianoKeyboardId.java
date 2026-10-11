package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;

public record OwnedPianoKeyboardId(PianoKeyboardId pianoKeyboardId, UserId userId) {

}
