package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs;

import java.util.List;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate.*;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.Error;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.ValidatorService;

public abstract class PuzzleConfigValidator<PCA extends PuzzleConfigAggregate<?>> extends ValidatorService<PCA> {

    public List<Error> gatherAllErrors(final PCA puzzleConfigAggregate) {
        var errors = createErrorsList();
        if (puzzleConfigAggregate.getTargetNumberOfPuzzles() <= 0) {
            errors.add(new Error(TARGET_NUMBER_OF_PUZZLES_PROP, "puzzles.configs.number_of_puzzles", "puzzles.configs.number_of_puzzles.invalid_value.greater_than_0"));
        }
        return errors;
    }

}
