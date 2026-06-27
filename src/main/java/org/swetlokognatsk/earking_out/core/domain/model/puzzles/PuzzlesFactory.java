package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.AudioPerfectPitchHint;
import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.PerfectPitchHint;
import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.VisualPerfectPitchHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.VisualPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators.SolutionGeneratorsFactory;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.finders.HintFinder;

public final class PuzzlesFactory {
    protected final HintFinder hintFinder;
    protected final SolutionGeneratorsFactory solutionGeneratorsFactory;

    public PuzzlesFactory() {
        hintFinder = DI.get(HintFinder.class);
        solutionGeneratorsFactory = DI.get(SolutionGeneratorsFactory.class);
    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>, r, P extends Puzzle<E, ?>> P create(final Exercise exercise) {
        var puzzleConfigDto = PuzzleConfigDTOAssembler.getPuzzleConfigDTO(exercise);
        // TODO cache?
        var solutionGenerator = solutionGeneratorsFactory.create(puzzleConfigDto);
        var solution = solutionGenerator.generate();
        var hint = hintFinder.find(exercise, solution);

        var puzzle = switch (exercise) {
        case VisualPerfectPitchExercise e -> new VisualPerfectPitchPuzzle((VisualPerfectPitchExercise) exercise, solution, (VisualPerfectPitchHint) hint);
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchPuzzle((AudioPerfectPitchExercise) exercise, solution, (AudioPerfectPitchHint) hint);
        default -> throw new RuntimeException("unknown exercise for puzzle: " + exercise);
        };
        return (P) puzzle;
    }
}
