package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.PuzzleTest;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.IPerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeAudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeVisualPerfectPitchHints;

public class PerfectPitchHintFinderTest {
    @Test
    public void exerciseTypeIsCorrect() {
        var hintFinder = DI.get(IPerfectPitchHintFinder.class);

        String fakeSolution;
        for (var exerciseType : ExerciseTypes.values()) {
            var expectedSubstring = exerciseType == ExerciseTypes.AUDIO ? FakeAudioPerfectPitchHints.TYPE : FakeVisualPerfectPitchHints.TYPE;
            for (Integer i = 4; i < 90; i++) {
                // TODO is it a good idea to depent on other test suites' static methods?
                fakeSolution = i.toString();
                var puzzle = PuzzleTest.createPuzzle(ExerciseNames.PERFECT_PITCH, exerciseType, fakeSolution);
                var hint = hintFinder.find(puzzle).getValue();
                assertTrue(hint.contains(expectedSubstring));
            }
        }
    }

    @Test
    public void exerciseSolutionIsCorrect() {
        var hintFinder = DI.get(IPerfectPitchHintFinder.class);

        String fakeSolution;
        String expectedSubstring;
        for (Integer i = 4; i < 90; i++) {
            for (var exerciseType : ExerciseTypes.values()) {
                expectedSubstring = i.toString();
                // TODO is it a good idea to depent on other test suites' static methods?
                fakeSolution = i.toString();
                var puzzle = PuzzleTest.createPuzzle(ExerciseNames.PERFECT_PITCH, exerciseType, fakeSolution);
                var hint = hintFinder.find(puzzle).getValue();
                assertTrue(hint.contains(expectedSubstring));
            }
        }
    }
}
