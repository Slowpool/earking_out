package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.IFiniteHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.IHintFinder;

public abstract class HintFinderByType implements IHintFinder {
    final IFiniteHintFinder visualHintFinder = resolveVisualHintFinder();
    final IFiniteHintFinder audioHintFinder = resolveAudioHintFinder();

    private IFiniteHintFinder resolveVisualHintFinder() {
        return DI.get(getVisualHintFinderClass());
    }

    protected abstract Class<? extends IFiniteHintFinder> getVisualHintFinderClass();

    private IFiniteHintFinder resolveAudioHintFinder() {
        return DI.get(getAudioHintFinderClass());
    }

    protected abstract Class<? extends IFiniteHintFinder> getAudioHintFinderClass();

    public <T extends Hint> T find(Puzzle<?, ?, ?> puzzle) {
        return switch (puzzle.exercise.type) {
        case VISUAL -> findVisualHint(puzzle);
        case AUDIO -> findAudioHint(puzzle);
        default -> null;
        };
    }

    private <T extends Hint> T findVisualHint(Puzzle<?, ?, ?> puzzle) {
        return visualHintFinder.find(puzzle.solution);
    }

    private <T extends Hint> T findAudioHint(Puzzle<?, ?, ?> puzzle) {
        return audioHintFinder.find(puzzle.solution);
    }
}
