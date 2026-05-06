package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints;

import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.IHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.IPerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.puzzles.PerfectPitchPuzzle;

public class HintFinderByExercise implements IHintFinder {
    public <T extends Hint> T find(Puzzle<?, ?, ?> puzzle) {
        var specificHintFinder = findSpecificHintFinder(puzzle);
        return specificHintFinder.find(puzzle);
    }

    private IHintFinder findSpecificHintFinder(Puzzle<?, ?, ?> puzzle) {
        // TODO this way or by exercise?
        var specificHintFinder = switch (puzzle) {
        case PerfectPitchPuzzle perfectPitchPuzzle -> IPerfectPitchHintFinder.class;
        default -> throw new RuntimeException("unknown puzzle on looking for specificHintFinder");
        };
        return DI.get(specificHintFinder);
    }
}
