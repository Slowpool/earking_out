package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints;

import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.FiniteHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;

/**
 * Encapsulates `switch` over exercise types, narrowing down the inheriting
 * class code to just specifying the classes of `FiniteFinder`s via
 * `getVisualHintFinderClass` and `getAudioHintFinderClass` definition
 */
public abstract class HintFinderMediatorByType<H extends Hint, P extends Puzzle<?, ?, H, ?>> implements HintFinder<H, P> {
    // TODO add logic for exercises that have only Audio type or only Visual type
    protected final FiniteHintFinder<H> visualHintFinder = resolveVisualHintFinder();
    protected final FiniteHintFinder<H> audioHintFinder = resolveAudioHintFinder();

    protected abstract Class<FiniteHintFinder<H>> getVisualHintFinderClass();

    protected abstract Class<FiniteHintFinder<H>> getAudioHintFinderClass();

    private FiniteHintFinder<H> resolveVisualHintFinder() {
        return DI.get(getVisualHintFinderClass());
    }

    private FiniteHintFinder<H> resolveAudioHintFinder() {
        return DI.get(getAudioHintFinderClass());
    }

    public H find(P puzzle) {
        return switch (puzzle.exercise.type) {
        case VISUAL -> findVisualHint(puzzle);
        case AUDIO -> findAudioHint(puzzle);
        default -> null;
        };
    }

    private H findVisualHint(P puzzle) {
        return visualHintFinder.find(puzzle.solution);
    }

    private H findAudioHint(P puzzle) {
        return audioHintFinder.find(puzzle.solution);
    }
}
