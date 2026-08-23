package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
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
            var message = error.message();
            var property = tryFindAnyProperty(message, mutableExpectedProps);
            if (property == null) {
                throw new AssertionError("property not found in errors. message: %s".formatted(message));
            } else {
                mutableExpectedProps.remove(property);
            }
        }
    }

    public static String tryFindAnyProperty(final String message, final List<String> expectedPropertiesToFail) {
        var optionalProperty = expectedPropertiesToFail.stream().filter(message::contains).findFirst();
        return optionalProperty.orElse(null);
    }

}
