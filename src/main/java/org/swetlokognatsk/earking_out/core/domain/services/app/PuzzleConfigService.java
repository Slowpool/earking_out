package org.swetlokognatsk.earking_out.core.domain.services.app;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.ReadPuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.config.WritePuzzleConfigService;

public final class PuzzleConfigService {

    protected final WritePuzzleConfigService writeService;
    protected final ReadPuzzleConfigService readService;

    public PuzzleConfigService(final WritePuzzleConfigService writeService, final ReadPuzzleConfigService readService) {
        this.writeService = writeService;
        this.readService = readService;
    }

    public void updateProperty(final Exercise exercise, final String property, final Object value) {
        writeService.updateProperty(exercise, property, value);
    }

    // TODO should it be here or in separated PerfectPitchConfigService?
    public void updatePropertyViaPianoKeyPressing(final PianoKeyboardId pianoKeyboardId, final byte keyNumber) {
        var repository = DI.get(PuzzleConfigAggregateRepository.class);
        // TODO how to identify config here?
        var puzzleConfigAggregate = repository.get();
        try {
            puzzleConfigAggregate.updateViaPianoKeyPressing(pianoKeyboardId, keyNumber);
        }
        // TODO just Exception?
        catch (Exception e) {
        }

    }
}
