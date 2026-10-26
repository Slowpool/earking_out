package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.Error;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.ValidationResult;

public final class TestPuzzleConfigValidatorHelper {

    private TestPuzzleConfigValidatorHelper() {
    }

    public static void assertNoValidationErrors(final ValidationResult validationResult) {
        assertTrue(validationResult.isValid());
        assertEquals(0, validationResult.errors().size());
    }

    /**
     * `expectedPropertiesToFail` - is list of properties which are expected to
     * fail. if one property is expected to have two errors, put this property in
     * this list twice.
     **/
    public static void assertThesePropertiesLedToErrors(final ValidationResult validationResult, final List<String> expectedPropertiesToFail) {
        assertFalse(validationResult.isValid());

        var errors = validationResult.errors();
        assertEquals(expectedPropertiesToFail.size(), errors.size());
        assertErrorsCorrespondToProperties(errors, expectedPropertiesToFail);
    }

    public static void assertErrorsCorrespondToProperties(final List<Error> errors, final List<String> expectedPropertiesToFail) {
        var mutableExpectedProps = new ArrayList<>(expectedPropertiesToFail);
        for (var error : errors) {
            if (mutableExpectedProps.contains(error.property())) {
                mutableExpectedProps.remove(error.property());
            } else {
                throw new AssertionError("unexpected error: %s".formatted(error.toString()));
            }
        }
    }
}
