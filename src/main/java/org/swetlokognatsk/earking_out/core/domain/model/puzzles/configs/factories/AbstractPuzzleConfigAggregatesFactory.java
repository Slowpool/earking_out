package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.VisualPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;

public final class AbstractPuzzleConfigAggregatesFactory {
    private final ObjectCloner objectCloner;

    public AbstractPuzzleConfigAggregatesFactory(final ObjectCloner objectCloner) {
        this.objectCloner = objectCloner;
    }

    public <E extends Exercise, PCAF extends PuzzleConfigAggregatesFactory<? extends PuzzleConfigAggregate<E>, ?>> PCAF createFactory(final E exercise) {
        PuzzleConfigAggregatesFactory<?, ?> factory = switch (exercise) {
            case AudioPerfectPitchExercise e -> new AudioPerfectPitchConfigAggregatesFactory(objectCloner);
            case VisualPerfectPitchExercise e -> new VisualPerfectPitchConfigAggregatesFactory(objectCloner);
            default -> throw new IllegalArgumentException("unknown exercise for PuzzleConfigAggregatesFactory: " + exercise);
        };
        return (PCAF) factory;
    }
}
