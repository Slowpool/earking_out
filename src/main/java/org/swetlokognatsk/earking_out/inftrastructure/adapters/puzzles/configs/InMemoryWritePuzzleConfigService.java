package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.config.WritePuzzleConfigService;

public final class InMemoryWritePuzzleConfigService implements WritePuzzleConfigService {

    // TODO it has to be more elegant
    public void save(final PuzzleConfig<?> puzzleConfig) {
        switch (puzzleConfig.exercise) {
        case AudioPerfectPitchExercise exercise:
            InMemoryReadPuzzleConfigService.appc = (AudioPerfectPitchConfig) puzzleConfig;
            break;
        default:

            break;
        }
    }
}
