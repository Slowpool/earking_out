package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.VisualPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators.SolutionGeneratorsFactory;
import org.swetlokognatsk.earking_out.core.ports.DI;

public final class PuzzlesFactory {
    protected final SolutionGeneratorsFactory solutionGeneratorsFactory;

    public PuzzlesFactory() {
        solutionGeneratorsFactory = DI.get(SolutionGeneratorsFactory.class);
    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>, r, P extends Puzzle<E, ?>> P create(final Exercise exercise) {
        var puzzleConfigDto = PuzzleConfigDTOAssembler.getPuzzleConfigDTO(exercise);
        // TODO cache?
        var solutionGenerator = solutionGeneratorsFactory.create(puzzleConfigDto);
        var solution = solutionGenerator.generate();

        var puzzle = switch (exercise) {
        case VisualPerfectPitchExercise e -> new VisualPerfectPitchPuzzle((VisualPerfectPitchExercise) exercise, solution);
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchPuzzle((AudioPerfectPitchExercise) exercise, (AudioPerfectPitchSolution) solution);
        default -> throw new RuntimeException("unknown exercise for puzzle: " + exercise);
        };
        return (P) puzzle;
    }
}
