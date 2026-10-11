package org.swetlokognatsk.earking_out.core.ports.piano;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.OwnedPianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.ports.base.TypedAggregateRepository;

public interface PianoKeyboardRepository extends TypedAggregateRepository<OwnedPianoKeyboardId, PianoKeyboardAggregate> {
    PianoKeyboardAggregate get(final PianoKeyboardId pianoKeyboardId) throws AggregateNotFoundException;

    /**
     * If aggregate is not found, it must be created, saved and then returned. So,
     * this method must never return null.
     */
    PianoKeyboardAggregate getInevitably(PianoKeyboardId pianoKeyboardId);

    PianoKeyboardDTO getPianoKeyboardDTO(PianoKeyboardId pianoKeyboardId) throws AggregateNotFoundException;

    PianoKeyboardDTO getPianoKeyboardDTOInevitably(PianoKeyboardId pianoKeyboardId);

}
