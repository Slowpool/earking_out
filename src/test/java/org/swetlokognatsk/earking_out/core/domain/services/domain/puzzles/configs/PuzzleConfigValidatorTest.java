package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs;

import org.junit.Before;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.Error;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import static org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.TestPuzzleConfigValidatorHelper.*;

import java.util.List;

public abstract class PuzzleConfigValidatorTest<E extends Exercise, PCA extends PuzzleConfigAggregate<E>, PCAF extends PuzzleConfigAggregatesFactory<PCA>, VC extends PuzzleConfigValidator<PCA>> {

    protected final PCAF aggregatesFactory;
    protected VC validator;

    protected abstract E getExercise();

    protected abstract Class<VC> getValidatorClass();

    public PuzzleConfigValidatorTest() {
        aggregatesFactory = (PCAF) DI.get(AbstractPuzzleConfigAggregatesFactory.class).createFactory(getExercise());
    }

    @Before
    public void setup() {
        validator = (VC) DI.get(getValidatorClass());
    }

    protected PCA createPuzzleConfig() {
        return aggregatesFactory.createDefault();
    }

    protected void assertNoValidationErrors(final PCA puzzleConfig) {
        var validationResult = validator.validate(puzzleConfig);
        TestPuzzleConfigValidatorHelper.assertNoValidationErrors(validationResult);
    }

    protected void assertThesePropertiesLedToErrors(final PCA puzzleConfig, final List<String> expectedPropertiesToFail) {
        var validationResult = validator.validate(puzzleConfig);
        TestPuzzleConfigValidatorHelper.assertThesePropertiesLedToErrors(validationResult, expectedPropertiesToFail);
    }

}
