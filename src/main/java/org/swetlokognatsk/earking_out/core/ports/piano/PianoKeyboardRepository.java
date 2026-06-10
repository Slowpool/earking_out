package org.swetlokognatsk.earking_out.core.ports.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;

// TODO use generics for repositories
public interface PianoKeyboardRepository {
    PianoKeyboardAggregate get(final PianoKeyboardId pianoKeyboardId);
    void save(final PianoKeyboardAggregate pianoKeyboardAggregate);
}
