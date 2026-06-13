package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.EndHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.AudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.VisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

/**
 * Under the hood it's a mediator - all it does is delegating the finding to
 * specific finder.
 */
public final class HintFinderDelegator implements HintFinder {

    public <H extends Hint, P extends Puzzle<?, ?, H, ?>> H find(P puzzle) {
        // TODO cache only the last hintFinder in memory
        var specificHintFinder = createSpecificHintFinder(puzzle);
        return specificHintFinder.find(puzzle.solution);
    }

    private <H extends Hint, P extends Puzzle<?, ?, H, ?>> EndHintFinder<H> createSpecificHintFinder(P puzzle) {
        var exercise = puzzle.config.exercise;
        var specificHintFinder = switch (exercise) {
        case AudioPerfectPitchExercise e -> AudioPerfectPitchHints.class;
        case VisualPerfectPitchExercise e -> VisualPerfectPitchHints.class;
        default -> throw new RuntimeException("unknown exercise on looking for specificHintFinder: " + exercise);
        };
        return (EndHintFinder<H>) DI.get(specificHintFinder);
    }
}
