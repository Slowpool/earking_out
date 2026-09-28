package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.VisualPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public final class PuzzleConfigAggregatesFactoryResolver {

    public PuzzleConfigAggregatesFactoryResolver() {
    }

    public <E extends Exercise, PCAF extends PuzzleConfigAggregatesFactory<? extends PuzzleConfigAggregate<E>>> PCAF resolveFactory(final E exercise) {
        PuzzleConfigAggregatesFactory<?> factory = switch (exercise) {
            // TODO `the value of the local variable isn't used` - is it possible to do something with it besides the warning suppression?
            case AudioPerfectPitchExercise e -> DI.get(AudioPerfectPitchConfigAggregatesFactory.class);
            case VisualPerfectPitchExercise e -> DI.get(VisualPerfectPitchConfigAggregatesFactory.class);
            default -> throw new IllegalArgumentException("unknown exercise for PuzzleConfigAggregatesFactory: " + exercise);
        };
        return (PCAF) factory;
    }
}
