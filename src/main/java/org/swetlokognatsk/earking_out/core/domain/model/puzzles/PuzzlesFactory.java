package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import java.util.Map;
import java.util.Map.Entry;
import org.swetlokognatsk.earking_out.DebugUtils;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.VisualPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.puzzles.SolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.factories.puzzles.generators.SolutionGeneratorsFactory;

// TODO encapsulate factory inside di, so that instead his could be used: `DI.createPuzzle(new AudioPerfectPitchExercise())` or kinda
public final class PuzzlesFactory {
    private final SolutionGeneratorsFactory solutionGeneratorsFactory;
    private final PuzzleConfigRepository puzzleConfigRepository;

    // latch entry to avoid `cachedSolutionGenerator == null` checking
    // TODO think about it. now it's a mess
    private static final PuzzleConfigDTO<?> latchConfig = new AudioPerfectPitchConfigDTO(null, 0, false, null, null, null, false);
    private static final SolutionGenerator<?> latchSolutionGenerator = new AudioPerfectPitchSolutionGenerator() {
        public AudioPerfectPitchSolution generate() {
            return null;
        }
    };
    private Entry<PuzzleConfigDTO<?>, SolutionGenerator<?>> cachedSolutionGenerator = Map.entry(latchConfig, latchSolutionGenerator);

    public PuzzlesFactory(final SolutionGeneratorsFactory solutionGeneratorsFactory, final PuzzleConfigRepository puzzleConfigRepository) {
        this.solutionGeneratorsFactory = solutionGeneratorsFactory;
        this.puzzleConfigRepository = puzzleConfigRepository;
    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>, r, P extends Puzzle<E, ?>> P create(final E exercise) {
        // TODO cache only the last solution generator
        DebugUtils.startStopwatch();
        var solutionGenerator = getSolutionGenerator(exercise);
        DebugUtils.stopStopwatch();

        var solution = solutionGenerator.generate();

        var puzzle = switch (exercise) {
        case VisualPerfectPitchExercise e -> new VisualPerfectPitchPuzzle(e, solution);
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchPuzzle(e, (AudioPerfectPitchSolution) solution);
        default -> throw new RuntimeException("unknown exercise for puzzle: " + exercise);
        };
        return (P) puzzle;
    }

    private SolutionGenerator<?> getSolutionGenerator(final Exercise exercise) {
        var puzzleConfigDto = puzzleConfigRepository.getPuzzleConfigDTO(exercise);
        if (!isCached(puzzleConfigDto)) {
            createAndCacheSolutionGenerator(puzzleConfigDto);
        }
        return cachedSolutionGenerator.getValue();
    }

    private void createAndCacheSolutionGenerator(final PuzzleConfigDTO<?> puzzleConfigDto) {
        var solutionGenerator = solutionGeneratorsFactory.create(puzzleConfigDto);
        // the previous entry is removed (by GC) to avoid memory leak
        cachedSolutionGenerator = Map.entry(puzzleConfigDto, solutionGenerator);
    }

    private boolean isCached(final PuzzleConfigDTO<?> puzzleConfigDto) {
        return puzzleConfigDto.equals(cachedSolutionGenerator.getKey());
    }
}
