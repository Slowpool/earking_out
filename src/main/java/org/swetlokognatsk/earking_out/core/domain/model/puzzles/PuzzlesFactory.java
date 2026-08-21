package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.VisualPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.infrastructure.factories.puzzles.generators.SolutionGeneratorsFactory;

// TODO encapsulate factory inside di, so that instead his could be used: `DI.createPuzzle(new AudioPerfectPitchExercise())` or kinda
public final class PuzzlesFactory {
    private final SolutionGeneratorsFactory solutionGeneratorsFactory;
    private final PuzzleConfigRepository puzzleConfigRepository;

    public PuzzlesFactory(final SolutionGeneratorsFactory solutionGeneratorsFactory, final PuzzleConfigRepository puzzleConfigRepository) {
        this.solutionGeneratorsFactory = solutionGeneratorsFactory;
        this.puzzleConfigRepository = puzzleConfigRepository;
    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>, r, P extends Puzzle<E, ?>> P create(final Exercise exercise) {
        var puzzleConfigDto = puzzleConfigRepository.getPuzzleConfigDTO(exercise);
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
