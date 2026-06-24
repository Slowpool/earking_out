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
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.FakePuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchPuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeVisualPerfectPitchPuzzleGenerator;

// TODO review the domain layer to make sure it does not contain a concepts the domain expert wouldn't understand
public final class PuzzleTestHelper {
    protected final PuzzleConfigRepository puzzleConfigRepository;

    public PuzzleTestHelper(final PuzzleConfigRepository puzzleConfigRepository) {
        this.puzzleConfigRepository = puzzleConfigRepository;
    }

    public <E extends Exercise, P extends Puzzle<E, ?, ?, ?>> P createPuzzle(final E exercise) {
        var puzzleConfigDto = PuzzleConfigDTOAssembler.getPuzzleConfigDTO(exercise);

        var puzzleGenerator = getFakePuzzleGenerator(exercise);
        return (P) PuzzlesFactory.create(puzzleConfigDto, puzzleGenerator);
    }

    public <E extends Exercise, P extends Puzzle<E, ?, ?, ?>> P createPuzzle(final E exercise, final String fakeSolution) {
        FakePuzzleGenerator.fakeSolution = fakeSolution;
        return createPuzzle(exercise);
    }

    public <P extends Puzzle<?, ?, ?, ?>> P createPuzzle(final ExerciseNames exerciseName, final ExerciseTypes exerciseType, final String fakeSolution) {
        var exercise = ExercisesFactory.create(exerciseName, exerciseType);
        return (P) createPuzzle(exercise, fakeSolution);
    }

    private static <PG extends PuzzleGenerator> PG getFakePuzzleGenerator(final Exercise exercise) {
        var puzzleGenerator = switch (exercise.name) {
        case PERFECT_PITCH -> switch (exercise.type) {
        case VISUAL -> new FakeVisualPerfectPitchPuzzleGenerator();
        case AUDIO -> new FakeAudioPerfectPitchPuzzleGenerator();
        default -> throw new RuntimeException("unknown exercise");
        };
        default -> throw new RuntimeException("unknown exercise");
        };
        return (PG) puzzleGenerator;
    }
}
