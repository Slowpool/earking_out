package org.swetlokognatsk.earking_out.core.domain.services.domain.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyColor;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

// TODO put the logic of the only implmentation of this interface to PianoKeyColor or PianoKeyNumber
public interface PianoKeyColorService {
    PianoKeyColor getColor(final PianoKeyNumber keyNumber);
}
