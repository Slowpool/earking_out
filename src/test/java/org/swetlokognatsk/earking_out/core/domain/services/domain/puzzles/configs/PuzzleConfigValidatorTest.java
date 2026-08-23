package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import static org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.TestPuzzleConfigValidatorHelper.*;

public final class PuzzleConfigValidatorTest {

    private static final boolean ANY_STATS_RECORDING = false;

    private PuzzleConfigValidator<SomePuzzleConfigAggregate> getValidator() {
        return new PuzzleConfigValidator<SomePuzzleConfigAggregate>() {
        };
    }

    private SomePuzzleConfigAggregate createSomePuzzleConfig(final int targetNumberOfPuzzles, final boolean statsRecording) {
        return new SomePuzzleConfigAggregate(new SomeExercise(), targetNumberOfPuzzles, statsRecording);
    }

    public void thisTargetNumberOfPuzzlesFails(final int targetNumberOfPuzzles) {
        var validator = getValidator();
        var puzzleConfig = createSomePuzzleConfig(targetNumberOfPuzzles, ANY_STATS_RECORDING);

        var validationResult = validator.validate(puzzleConfig);

        assertContainsTheseErrors(validationResult, List.of(PuzzleConfigAggregate.TARGET_NUMBER_OF_PUZZLES_PROP));
    }

    // TODO how about parameterized test like [TestCase] in c#
    @Test
    public void zeroTargetNumberOfPuzzlesFails() {
        thisTargetNumberOfPuzzlesFails(0);
    }

    @Test
    public void negativeTargetNumberOfPuzzlesFails() {
        thisTargetNumberOfPuzzlesFails(-1);
    }

    @Test
    public void positiveTargetNumberOfPuzzlesIsOk() {
        var validator = getValidator();
        var puzzleConfig = createSomePuzzleConfig(1, ANY_STATS_RECORDING);

        var validationResult = validator.validate(puzzleConfig);

        assertNoErrors(validationResult);
    }
}

class SomePuzzleConfigAggregate extends PuzzleConfigAggregate<SomeExercise> {

    public SomePuzzleConfigAggregate(final SomeExercise exercise, final int targetNumberOfPuzzles, final boolean statsRecording) {
        super(exercise, targetNumberOfPuzzles, statsRecording);
    }

    public void updateConfigSpecificProperty(String a, Object o) {
    }
}

class SomeExercise extends Exercise {

    public String tName() {
        return "latch";
    }

    public String tType() {
        return "latch";
    }

    public SomeExercise() {
        // does not matter
        super(ExerciseNames.PERFECT_PITCH, ExerciseTypes.AUDIO);
    }
}