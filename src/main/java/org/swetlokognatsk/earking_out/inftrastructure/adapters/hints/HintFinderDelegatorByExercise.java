package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.PerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

// TODO does this pattern have common name?
/**
 * Under the hood it's a mediator - all it does is delegating the finding to
 * specific finder.
 */
public class HintFinderDelegatorByExercise<H extends Hint, P extends Puzzle<?, ?, H, ?>> implements HintFinder<H, P> {

    public H find(P puzzle) {
        var specificHintFinder = createSpecificHintFinder(puzzle);
        return specificHintFinder.find(puzzle);
    }

    private HintFinder<H, P> createSpecificHintFinder(P puzzle) {
        var specificHintFinder = switch (puzzle) {
        case PerfectPitchPuzzle x -> PerfectPitchHintFinder.class;
        default -> throw new RuntimeException("unknown puzzle on looking for specificHintFinder: " + puzzle.getClass().getName());
        };
        // TODO why there's no error without cast?
        return (HintFinder<H, P>)DI.get(specificHintFinder);
    }
}
