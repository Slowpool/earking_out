package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.FiniteHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;

public abstract class HintFinderByType implements HintFinder {
    final FiniteHintFinder visualHintFinder = resolveVisualHintFinder();
    final FiniteHintFinder audioHintFinder = resolveAudioHintFinder();

    private FiniteHintFinder resolveVisualHintFinder() {
        return DI.get(getVisualHintFinderClass());
    }

    protected abstract Class<? extends FiniteHintFinder> getVisualHintFinderClass();

    private FiniteHintFinder resolveAudioHintFinder() {
        return DI.get(getAudioHintFinderClass());
    }

    protected abstract Class<? extends FiniteHintFinder> getAudioHintFinderClass();

    public Hint find(Puzzle<?, ?, ?> puzzle) {
        return switch (puzzle.exercise.type) {
        case VISUAL -> findVisualHint(puzzle);
        case AUDIO -> findAudioHint(puzzle);
        default -> null;
        };
    }

    private Hint findVisualHint(Puzzle<?, ?, ?> puzzle) {
        return visualHintFinder.find(puzzle.solution);
    }

    private Hint findAudioHint(Puzzle<?, ?, ?> puzzle) {
        return audioHintFinder.find(puzzle.solution);
    }
}
