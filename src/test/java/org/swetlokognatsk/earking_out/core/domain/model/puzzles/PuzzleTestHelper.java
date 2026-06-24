package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import static org.junit.Assert.assertNotNull;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.puzzles.SolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.FakeSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeVisualPerfectPitchSolutionGenerator;

// TODO review the domain layer to make sure it does not contain a concepts the domain expert wouldn't understand
public final class PuzzleTestHelper {
    protected final PuzzleConfigRepository puzzleConfigRepository;

    public PuzzleTestHelper(final PuzzleConfigRepository puzzleConfigRepository) {
        this.puzzleConfigRepository = puzzleConfigRepository;
    }

    public <E extends Exercise, P extends Puzzle<E, ?, ?, ?>> P createPuzzle(final E exercise) {
        var puzzleConfigDto = PuzzleConfigDTOAssembler.getPuzzleConfigDTO(exercise);

        var solutionGenerator = getFakeSolutionGenerator(exercise);
        return (P) PuzzlesFactory.create(puzzleConfigDto, solutionGenerator);
    }

    public <E extends Exercise, P extends Puzzle<E, ?, ?, ?>> P createPuzzle(final E exercise, final String fakeSolution) {
        FakeSolutionGenerator.fakeSolution = fakeSolution;
        return createPuzzle(exercise);
    }

    public <P extends Puzzle<?, ?, ?, ?>> P createPuzzle(final ExerciseNames exerciseName, final ExerciseTypes exerciseType, final String fakeSolution) {
        var exercise = ExercisesFactory.create(exerciseName, exerciseType);
        return (P) createPuzzle(exercise, fakeSolution);
    }

    private static <PG extends SolutionGenerator> PG getFakeSolutionGenerator(final Exercise exercise) {
        var solutionGenerator = switch (exercise.name) {
        case PERFECT_PITCH -> switch (exercise.type) {
        case VISUAL -> new FakeVisualPerfectPitchSolutionGenerator();
        case AUDIO -> new FakeAudioPerfectPitchSolutionGenerator();
        default -> throw new RuntimeException("unknown exercise");
        };
        default -> throw new RuntimeException("unknown exercise");
        };
        return (PG) solutionGenerator;
    }
}
