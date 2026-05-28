package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.FiniteHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.AudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.VisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

// TODO does this pattern have common name?
/**
 * Under the hood it's a mediator - all it does is delegating the finding to
 * specific finder.
 */
public class HintFinderDelegator<H extends Hint, P extends Puzzle<?, ?, H, ?>> implements HintFinder<H, P> {

    public H find(P puzzle) {
        // TODO cache only the last hintFinder in memory
        var specificHintFinder = createSpecificHintFinder(puzzle);
        return specificHintFinder.find(puzzle.solution);
    }

    private FiniteHintFinder<H> createSpecificHintFinder(P puzzle) {
        var exercise = puzzle.exercise;
        var specificHintFinder = switch (exercise.name) {
        case PERFECT_PITCH -> switch (exercise.type) {
        case AUDIO -> AudioPerfectPitchHints.class;
        case VISUAL -> VisualPerfectPitchHints.class;
        default -> throw new RuntimeException("unknown exercise type on looking for specificHintFinder: " + exercise.type);
        };
        default -> throw new RuntimeException("unknown exercise on looking for specificHintFinder: " + exercise.name);
        };
        return (FiniteHintFinder<H>) DI.get(specificHintFinder);
    }
}
