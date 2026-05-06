package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.IPerfectPitchHintFinder;

public class PerfectPitchHintFinder implements IPerfectPitchHintFinder {
    public <T extends Hint> T find(Puzzle<?, ?, ?> puzzle) {
        return switch (puzzle.exercise.type) {
        case VISUAL -> findVisualHint(puzzle);
        case AUDIO -> findAudioHint(puzzle);
        default -> null;
        };
    }

    private <T extends Hint> T findVisualHint(Puzzle<?, ?, ?> puzzle) {
        return VisualPerfectPitchHints.find(puzzle.solution);
    }

    private <T extends Hint> T findAudioHint(Puzzle<?, ?, ?> puzzle) {
        return AudioPerfectPitchHints.find(puzzle.solution);
    }

}
