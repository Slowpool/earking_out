package org.swetlokognatsk.earking_out.core.domain.services.app;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

public final class PuzzleConfigService {

    protected final PuzzleConfigRepository repository;

    public PuzzleConfigService(final PuzzleConfigRepository repository) {
        this.repository = repository;
    }

    public void updateProperty(final Exercise exercise, final String property, final Object value) {
        var puzzleConfigAggregate = repository.get(exercise);
        try {
            puzzleConfigAggregate.updateProperty(property, value);
            repository.save(puzzleConfigAggregate);
        }
        // TODO just Exception?
        catch (Exception e) {
        }
    }

    // TODO should it be here or in separated PerfectPitchConfigService?
    public void updatePropertyViaPianoKeyPressing(final PianoKeyboardId pianoKeyboardId, final byte keyNumber) {
        var exercise = pianoKeyboardId.exercise;
        var puzzleConfigAggregate = repository.get(exercise);
        try {
            puzzleConfigAggregate.updateViaPianoKeyPressing(pianoKeyboardId, keyNumber);
            repository.save(puzzleConfigAggregate);
        }
        // TODO just Exception?
        catch (Exception e) {
        }

    }
}
