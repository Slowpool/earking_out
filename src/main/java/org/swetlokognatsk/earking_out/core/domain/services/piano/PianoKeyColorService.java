package org.swetlokognatsk.earking_out.core.domain.services.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.PianoKeyColor;

public interface PianoKeyColorService {
    PianoKeyColor getColor(byte keyNumber);
}
