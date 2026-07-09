package org.swetlokognatsk.earking_out.core.ports.piano;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRepository;

// TODO actually it's not classic ddd repo. this is not-transactional adapter that just somehow utilize the pianoKeyboard. the actual transactional stuff happens in PuzzleConfigAggregate itself.
public interface PianoKeyboardStorageAdapter extends AggregateRepository<PianoKeyboardId, PianoKeyboardAggregate> {
    PianoKeyboardDTO getViewDto(final PianoKeyboardId pianoKeyboardId);

    PianoKeyboardAggregate[] getByExercise(final Exercise exercise);
}
