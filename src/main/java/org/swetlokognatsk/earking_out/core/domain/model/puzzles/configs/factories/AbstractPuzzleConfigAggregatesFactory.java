package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class AbstractPuzzleConfigAggregatesFactory {

    private AbstractPuzzleConfigAggregatesFactory() {
    }

    public <PCAF extends PuzzleConfigAggregatesFactory> PCAF createFactory(final Exercise exercise) {
        var factory = switch (exercise) {
            case AudioPerfectPitchExercise e -> new AudioPerfectPitchConfigAggregatesFactory();
            case VisualPerfectPitchExercise e -> new VisualPerfectPitchConfigAggregatesFactory();
            default -> throw new IllegalArgumentException("unknown exercise for PuzzleConfigAggregatesFactory: " + exercise);
        };
        return (PCAF) factory;
    }

    public static <PCA extends PuzzleConfigAggregate<?>, PC extends PuzzleConfigAggregate<?>> PCA create(PC puzzleConfig) {
        var exercise = puzzleConfig.exercise;
        var pianoKeyboardRepository = DI.get(PianoKeyboardRepository.class);
        var aggregate = switch (exercise) {
        case VisualPerfectPitchExercise e -> new VisualPerfectPitchConfigAggregate((VisualPerfectPitchConfigAggregate) puzzleConfig, pianoKeyboardRepository);
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchConfigAggregate((AudioPerfectPitchConfigAggregate) puzzleConfig, pianoKeyboardRepository);
        default -> throw new RuntimeException("unknown exercise of puzzleConfig: " + exercise);
        };
        return (PCA) aggregate;
    }

}
