package org.swetlokognatsk.earking_out.core.domain.model.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;

// TODO does it make sense to add private constructor to each factory?
public final class PuzzleGeneratorsFactory {
    public static PuzzleGenerator create(PuzzleConfig<?> puzzleConfig) {
        var exercise = puzzleConfig.exercise;
        return switch(exercise.name) {
            case PERFECT_PITCH -> switch(exercise.type) {
                // case VISUAL -> 
                case AUDIO -> new AudioPerfectPitchPuzzleGenerator((AudioPerfectPitchConfig)puzzleConfig);
                default -> throw new RuntimeException("unknown exercise type for puzzle generator: " + exercise.type);
            };
            default -> throw new RuntimeException("unknown exercise for puzzle generator: " + exercise.name);
        };
    }
}
