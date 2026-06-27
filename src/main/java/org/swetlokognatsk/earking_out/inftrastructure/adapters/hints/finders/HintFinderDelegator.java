package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.finders;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.finders.EndHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.finders.HintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.finders.perfectpitch.PerfectPitchHints;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

/**
 * Under the hood it's a mediator - all it does is delegating the finding to
 * specific finder.
 */
public final class HintFinderDelegator implements HintFinder {

    public Hint find(final Exercise exercise, final Solution solution) {
        // TODO cache only the last hintFinder in memory
        var specificHintFinder = createSpecificHintFinder(exercise);
        return specificHintFinder.find(solution);
    }

    private <E extends Exercise, P extends Puzzle<E, ?>> EndHintFinder<?, P> createSpecificHintFinder(final E exercise) {
        var specificHintFinder = switch (exercise) {
        case AudioPerfectPitchExercise e -> PerfectPitchHints.class;
        // case VisualPerfectPitchExercise e -> VisualPerfectPitchHints.class;
        default -> throw new RuntimeException("unknown exercise on looking for specificHintFinder: " + exercise);
        };
        return (EndHintFinder<?, P>) DI.get(specificHintFinder);
    }
}
