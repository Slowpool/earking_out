package org.swetlokognatsk.earking_out.core.ports.piano;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRepository;

public interface PianoKeyboardRepository extends AggregateRepository<PianoKeyboardId, PianoKeyboardAggregate> {
    PianoKeyboardDTO getViewDto(final PianoKeyboardId pianoKeyboardId);

    // // TODO is it used anywhere?
    // PianoKeyboardAggregate[] getByExercise(final Exercise exercise);
}
