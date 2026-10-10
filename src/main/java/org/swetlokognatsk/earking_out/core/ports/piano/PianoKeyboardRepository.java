package org.swetlokognatsk.earking_out.core.ports.piano;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.ports.base.TypedAggregateRepository;

public interface PianoKeyboardRepository extends TypedAggregateRepository<PianoKeyboardId, PianoKeyboardAggregate> {
    PianoKeyboardDTO getPianoKeyboardDTO(final PianoKeyboardId pianoKeyboardId);

    // // TODO is it used anywhere?
    // PianoKeyboardAggregate[] getByExercise(final Exercise exercise);
}
