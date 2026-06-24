package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.puzzles.SolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;

public final class SolutionGeneratorsFactory {

    private SolutionGeneratorsFactory() {
    }

    public static <PG extends SolutionGenerator> PG create(final PuzzleConfigDTO<?> puzzleConfig) {
        var exercise = puzzleConfig.exercise;
        var solutionGenerator = switch (exercise) {
        // case VISUAL -> 
        case AudioPerfectPitchExercise e -> DI.get(AudioPerfectPitchSolutionGenerator.class, puzzleConfig);
        default -> throw new RuntimeException("unknown exercise for puzzle generator: " + exercise);
        };
        return (PG) solutionGenerator;
    }
}
