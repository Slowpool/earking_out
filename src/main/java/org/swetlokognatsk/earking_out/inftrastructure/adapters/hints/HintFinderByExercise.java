package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.IPerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

public class HintFinderByExercise implements HintFinder {
    public Hint find(Puzzle<?, ?, ?> puzzle) {
        var specificHintFinder = findSpecificHintFinder(puzzle);
        return specificHintFinder.find(puzzle);
    }

    private HintFinder findSpecificHintFinder(Puzzle<?, ?, ?> puzzle) {
        var specificHintFinder = switch (puzzle) {
        case PerfectPitchPuzzle perfectPitchPuzzle -> IPerfectPitchHintFinder.class;
        default -> throw new RuntimeException("unknown puzzle on looking for specificHintFinder: " + puzzle.getClass().getName());
        };
        return DI.get(specificHintFinder);
    }
}
