package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.key;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyColor;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyMode;

public record PianoKeyDTO(byte keyNumber, PianoKeyColor color, PianoKeyMode mode, boolean isSelected, boolean isPressed) {
}
