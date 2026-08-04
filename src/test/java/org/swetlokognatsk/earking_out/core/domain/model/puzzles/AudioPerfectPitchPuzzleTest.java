package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public class AudioPerfectPitchPuzzleTest {
    private PuzzleTestHelper puzzleHelper;

    @Before
    public void setup() {
        DI.refreshDependencies();
        puzzleHelper = new PuzzleTestHelper(DI.get(PuzzleConfigRepository.class));
    }

    @Test
    // TODO it checks whether puzzle.create indeed creates the object of `Puzzle` type, that's it. is it ok to test such a thing or it is redundant?
    public void puzzleClassCorresponds() {
        var puzzle = puzzleHelper.createPuzzle(new AudioPerfectPitchExercise());
        assertTrue(puzzle instanceof AudioPerfectPitchPuzzle);
    }

    @Test
    public void perfectPitchCorrectPuzzleGuess() {
        var solution = new AudioPerfectPitchSolution(FIRST_NOTE_NUMBER);
        var puzzle = puzzleHelper.createPuzzle(new AudioPerfectPitchExercise(), solution);
        boolean correctAnswer = puzzle.guess(solution);
        assertTrue(correctAnswer);
    }

    @Test
    public void perfectPitchWrongPuzzleGuess() {
        var solution = new AudioPerfectPitchSolution(FIRST_NOTE_NUMBER);
        var puzzle = puzzleHelper.createPuzzle(new AudioPerfectPitchExercise(), solution);

        var wrongSolution = new AudioPerfectPitchSolution(FIRST_NOTE_NUMBER.increment());
        boolean correctAnswer = puzzle.guess(wrongSolution);
        assertFalse(correctAnswer);
    }

    // TODO delete
    // // TODO is it normal to test X and then write the test Y which also does X?
    // @Test
    // /**
    //  * Well, actually the only thing this test does is checking that Puzzle()
    //  * constructor successfully assigned the object variable `IHintFinder` as a
    //  * dependency
    //  */
    // public void hintCorrespondsToSolution() {
    //     var fakeSolution = "4";
    //     var exercise = new AudioPerfectPitchExercise();

    //     var puzzle = puzzleHelper.createPuzzle(exercise, fakeSolution);
    //     var hint = puzzle.hint;
    //     var hintFinder = DI.get(HintFinder.class);
    //     var correctHint = hintFinder.find(exercise, new Solution(fakeSolution));
    //     var correctHintValue = correctHint.getValue();

    //     assertEquals(correctHintValue, hint.getValue());
    // }
}
