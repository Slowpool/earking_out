package org.swetlokognatsk.earking_out.core.domain.services.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;

public final class PuzzleGeneratorsFactory {

    private PuzzleGeneratorsFactory() {
    }

    public static <PG extends PuzzleGenerator> PG create(PuzzleConfig<?> puzzleConfig) {
        var exercise = puzzleConfig.exercise;
        var puzzleGenerator = switch (exercise.name) {
        case PERFECT_PITCH -> switch (exercise.type) {
        // case VISUAL -> 
        case AUDIO -> DI.get(AudioPerfectPitchPuzzleGenerator.class, puzzleConfig);
        default -> throw new RuntimeException("unknown exercise type for puzzle generator: " + exercise.type);
        };
        default -> throw new RuntimeException("unknown exercise for puzzle generator: " + exercise.name);
        };
        return (PG) puzzleGenerator;
    }
}
