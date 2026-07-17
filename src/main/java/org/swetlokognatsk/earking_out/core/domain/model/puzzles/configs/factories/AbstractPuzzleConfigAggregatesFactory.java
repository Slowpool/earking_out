package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.VisualPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardStorageAdapter;

public final class AbstractPuzzleConfigAggregatesFactory {

    private AbstractPuzzleConfigAggregatesFactory() {
    }

    public static <E extends Exercise, PCAF extends PuzzleConfigAggregatesFactory<? extends PuzzleConfigAggregate<E>, ?>> PCAF createFactory(final E exercise) {
        PuzzleConfigAggregatesFactory<?, ?> factory = switch (exercise) {
            case AudioPerfectPitchExercise e -> new AudioPerfectPitchConfigAggregatesFactory(DI.get(PianoKeyboardStorageAdapter.class));
            case VisualPerfectPitchExercise e -> new VisualPerfectPitchConfigAggregatesFactory(DI.get(PianoKeyboardStorageAdapter.class));
            default -> throw new IllegalArgumentException("unknown exercise for PuzzleConfigAggregatesFactory: " + exercise);
        };
        return (PCAF) factory;
    }
}
