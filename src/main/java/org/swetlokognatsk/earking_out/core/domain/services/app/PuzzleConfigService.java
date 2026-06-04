package org.swetlokognatsk.earking_out.core.domain.services.app;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
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
}
