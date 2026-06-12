package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.VisualPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class AbstractPuzzleConfigAggregatesFactory {

    private AbstractPuzzleConfigAggregatesFactory() {
    }

    public static <E extends Exercise, PCAF extends PuzzleConfigAggregatesFactory<? extends PuzzleConfigAggregate<E>>> PCAF createFactory(final E exercise) {
        var factory = switch (exercise) {
            case AudioPerfectPitchExercise e -> new AudioPerfectPitchConfigAggregatesFactory();
            case VisualPerfectPitchExercise e -> new VisualPerfectPitchConfigAggregatesFactory();
            default -> throw new IllegalArgumentException("unknown exercise for PuzzleConfigAggregatesFactory: " + exercise);
        };
        return (PCAF) factory;
    }

    // // TODO it does not belong here
    // public static <PCA extends PuzzleConfigAggregate<?>, PC extends PuzzleConfigAggregate<?>> PCA create(final Exercise exercise) {
    //     var pianoKeyboardRepository = DI.get(PianoKeyboardRepository.class);
    //     var aggregate = switch (exercise) {
    //     case VisualPerfectPitchExercise e -> new VisualPerfectPitchConfigAggregate((VisualPerfectPitchConfigAggregate) puzzleConfig, pianoKeyboardRepository);
    //     case AudioPerfectPitchExercise e -> new AudioPerfectPitchConfigAggregate((AudioPerfectPitchConfigAggregate) puzzleConfig, pianoKeyboardRepository);
    //     default -> throw new RuntimeException("unknown exercise of puzzleConfig: " + exercise);
    //     };
    //     return (PCA) aggregate;
    // }

}
