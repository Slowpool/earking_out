package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.Error;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.ValidationResult;

public final class PuzzleConfigValidatorTest {

    private static final boolean ANY_STATS_RECORDING = false;

    private PuzzleConfigValidator<SomePuzzleConfigAggregate> getValidator() {
        return new PuzzleConfigValidator<SomePuzzleConfigAggregate>() {
        };
    }

    private SomePuzzleConfigAggregate createSomePuzzleConfig(final int targetNumberOfPuzzles, final boolean statsRecording) {
        return new SomePuzzleConfigAggregate(new SomeExercise(), targetNumberOfPuzzles, statsRecording);
    }

    // TODO put assert methods in special validator test helper
    private void assertNoErrors(final ValidationResult validationResult) {
        assertTrue(validationResult.isValid());
        assertEquals(0, validationResult.errors().size());
    }

    /**
     * `expectedPropertiesToFail` - is list of properties which are expected to
     * fail. if one property is expected to have two errors, put this property in
     * this list twice.
     **/
    private void assertContainsTheseErrors(final ValidationResult validationResult, final List<String> expectedPropertiesToFail) {
        assertFalse(validationResult.isValid());

        var errors = validationResult.errors();
        assertEquals(expectedPropertiesToFail.size(), errors.size());
        assertErrorsCorrespondToProperties(errors, expectedPropertiesToFail);
    }

    private void assertErrorsCorrespondToProperties(final List<Error> errors, final List<String> expectedPropertiesToFail) {
        var mutableExpectedProps = new ArrayList<>(expectedPropertiesToFail);
        for (var error : errors) {
            var message = error.message();
            var property = tryFindAnyProperty(message, mutableExpectedProps);
            if (property == null) {
                throw new AssertionError("property not found in errors. message: %s".formatted(message));
            } else {
                mutableExpectedProps.remove(property);
            }
        }
    }

    private String tryFindAnyProperty(final String message, final List<String> expectedPropertiesToFail) {
        var optionalProperty = expectedPropertiesToFail.stream().filter(message::contains).findFirst();
        return optionalProperty.orElse(null);
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