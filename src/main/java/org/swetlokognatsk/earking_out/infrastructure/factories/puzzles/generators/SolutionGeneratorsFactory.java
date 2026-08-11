package org.swetlokognatsk.earking_out.infrastructure.factories.puzzles.generators;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.puzzles.SolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchSolutionGenerator;

public final class SolutionGeneratorsFactory {

    public SolutionGeneratorsFactory() {
    }

    @SuppressWarnings("unchecked")
    public <PG extends SolutionGenerator<?>> PG create(final PuzzleConfigDTO<?> puzzleConfig) {
        var exercise = puzzleConfig.exercise;
        var solutionGenerator = switch (exercise) {
        case AudioPerfectPitchExercise e -> DI.get(AudioPerfectPitchSolutionGenerator.class, puzzleConfig);
        case VisualPerfectPitchExercise e -> DI.get(VisualPerfectPitchSolutionGenerator.class, puzzleConfig);
        default -> throw new RuntimeException("unknown exercise for solution generator: " + exercise);
        };
        return (PG) solutionGenerator;
    }

}
