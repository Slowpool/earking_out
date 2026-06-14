package org.swetlokognatsk.earking_out.core.ports.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRepository;

public interface PianoKeyboardRepository extends AggregateRepository<PianoKeyboardId, PianoKeyboardAggregate> {
}
