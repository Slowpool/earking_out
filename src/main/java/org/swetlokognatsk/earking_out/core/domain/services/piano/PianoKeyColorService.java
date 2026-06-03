package org.swetlokognatsk.earking_out.core.domain.services.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyColor;

public interface PianoKeyColorService {
    PianoKeyColor getColor(byte keyNumber);
}
