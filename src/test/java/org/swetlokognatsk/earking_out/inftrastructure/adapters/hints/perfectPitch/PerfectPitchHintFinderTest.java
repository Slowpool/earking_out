package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzleTestHelper;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeAudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeVisualPerfectPitchHints;

public class PerfectPitchHintFinderTest {
    protected PuzzleTestHelper puzzleHelper;

    @Before
    public void setup() {
        DI.clear();
        puzzleHelper = new PuzzleTestHelper(DI.get(PuzzleConfigRepository.class));
    }

    @Test
    public void exerciseTypeIsCorrect() {
        var hintFinder = DI.get(HintFinder.class);

        String fakeSolution;
        for (var exerciseType : ExerciseTypes.values()) {
            // TODO looks wrong
            var expectedSubstring = exerciseType == ExerciseTypes.AUDIO ? FakeAudioPerfectPitchHints.TYPE : FakeVisualPerfectPitchHints.TYPE;
            for (Integer i = 4; i < 90; i++) {
                // TODO is it a good idea to depend on other test suites' static methods?
                fakeSolution = i.toString();
                var puzzle = puzzleHelper.createPuzzle(ExerciseNames.PERFECT_PITCH, exerciseType, fakeSolution);
                var hint = hintFinder.find(puzzle).getValue();
                assertTrue(hint.contains(expectedSubstring));
            }
        }
    }

    @Test
    public void exerciseSolutionIsCorrect() {
        var hintFinder = DI.get(HintFinder.class);

        String fakeSolution;
        String expectedSubstring;
        for (byte i = FIRST_NOTE_NUMBER.value; i < PIANO_KEYS_NUMBER; i++) {
            for (var exercise : ExercisesFactory.getAll(ExerciseNames.PERFECT_PITCH)) {
                expectedSubstring = String.valueOf(i);
                fakeSolution = String.valueOf(i);
                var puzzle = puzzleHelper.createPuzzle(exercise, fakeSolution);
                var hint = hintFinder.find(puzzle).getValue();
                // TODO seems awkward
                assertTrue(hint.endsWith(expectedSubstring));
            }
        }
    }
}
