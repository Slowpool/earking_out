package org.swetlokognatsk.earking_out.core.domain.services.domain.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyColor;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public interface PianoKeyColorService {
    PianoKeyColor getColor(final PianoKeyNumber keyNumber);
}
