package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.IHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.IPerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.puzzles.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

public class HintFinderByExercise implements IHintFinder {
    public Hint find(Puzzle<?, ?, ?> puzzle) {
        var specificHintFinder = findSpecificHintFinder(puzzle);
        return specificHintFinder.find(puzzle);
    }

    private IHintFinder findSpecificHintFinder(Puzzle<?, ?, ?> puzzle) {
        var specificHintFinder = switch (puzzle) {
        case PerfectPitchPuzzle perfectPitchPuzzle -> IPerfectPitchHintFinder.class;
        default -> throw new RuntimeException("unknown puzzle on looking for specificHintFinder");
        };
        return DI.get(specificHintFinder);
    }
}
