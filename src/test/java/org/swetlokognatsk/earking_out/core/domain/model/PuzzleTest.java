package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.services.puzzles.generators.PuzzleGeneratorsFactory;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.ReadPuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.FakePuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchPuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeVisualPerfectPitchPuzzleGenerator;

public class PuzzleTest {
    public static <P extends Puzzle<?, ?, ?, ?>> P createPuzzle(ExerciseNames exerciseName, ExerciseTypes exerciseType, String fakeSolution) {
        FakePuzzleGenerator.fakeSolution = fakeSolution;
        return createPuzzle(exerciseName, exerciseType);
    }

    public static <P extends  Puzzle<?, ?, ?, ?>> P createPuzzle(ExerciseNames exerciseName, ExerciseTypes exerciseType) {
        var exercise = ExercisesFactory.create(exerciseName, exerciseType);
        assertNotNull(exercise);

        var configReadService = DI.get(ReadPuzzleConfigService.class);
        var puzzleConfig = configReadService.fetch(exercise);

        var puzzleGenerator = getFakePuzzleGenerator(exercise);
        return (P)PuzzlesFactory.create(exercise, puzzleConfig, puzzleGenerator);
    }

    private static <PG extends PuzzleGenerator> PG getFakePuzzleGenerator(Exercise exercise) {
        var puzzleGenerator = switch (exercise.name) {
            case PERFECT_PITCH -> switch (exercise.type) {
                case VISUAL -> new FakeVisualPerfectPitchPuzzleGenerator();
                case AUDIO -> new FakeAudioPerfectPitchPuzzleGenerator();
                default -> throw new RuntimeException("unknown exercise");
            };
            default -> throw new RuntimeException("unknown exercise");
        };
        return (PG)puzzleGenerator;
    }

    @Test
    // TODO it checks whether puzzle.create indeed creates the object of `Puzzle` type, that's it. is it ok to test such a thing or it is redundant?
    public void createPerfectPitchPuzzle() {
        var puzzle = createPuzzle(ExerciseNames.PERFECT_PITCH, ExerciseTypes.AUDIO);
        assertTrue(puzzle instanceof Puzzle);
    }

    @Test
    public void perfectPitchCorrectPuzzleGuess() {
        var solution = "4";
        var puzzle = createPuzzle(ExerciseNames.PERFECT_PITCH, ExerciseTypes.AUDIO, solution);
        boolean correctAnswer = puzzle.guess(new Guess(solution));
        assertTrue(correctAnswer);
    }

    @Test
    public void perfectPitchWrongPuzzleGuess() {
        var puzzle = createPuzzle(ExerciseNames.PERFECT_PITCH, ExerciseTypes.AUDIO, "4");
        boolean correctAnswer = puzzle.guess(new Guess("5"));
        assertFalse(correctAnswer);
    }

    // TODO is it normal to test X and then write the test Y which also does X?
    @Test
    /**
     * Well, actually the only thing this test does is checking that Puzzle()
     * constructor successfully assigned the object variable `IHintFinder` as a
     * dependency
     */
    public void hintCorrespondsToSolution() {
        var fakeSolution = "4";
        var puzzle = createPuzzle(ExerciseNames.PERFECT_PITCH, ExerciseTypes.AUDIO, fakeSolution);

        var hint = puzzle.hint;
        var hintFinder = DI.get(HintFinder.class);
        var correctHint = hintFinder.find(puzzle).getValue();

        assertEquals(correctHint, hint.getValue());
    }
}
