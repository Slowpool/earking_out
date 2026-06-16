package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;

public final class PuzzleGeneratorsFactory {

    private PuzzleGeneratorsFactory() {
    }

    public static <PG extends PuzzleGenerator> PG create(final PuzzleConfigDTO<?> puzzleConfig) {
        var exercise = puzzleConfig.exercise;
        var puzzleGenerator = switch (exercise) {
        // case VISUAL -> 
        case AudioPerfectPitchExercise e -> DI.get(AudioPerfectPitchPuzzleGenerator.class, puzzleConfig);
        default -> throw new RuntimeException("unknown exercise for puzzle generator: " + exercise);
        };
        return (PG) puzzleGenerator;
    }
}
