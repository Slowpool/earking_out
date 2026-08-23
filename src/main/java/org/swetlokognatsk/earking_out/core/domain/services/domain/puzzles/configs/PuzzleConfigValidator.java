package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs;

import java.util.List;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.Error;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.ValidatorService;

public abstract class PuzzleConfigValidator<PCA extends PuzzleConfigAggregate<?>> extends ValidatorService<PCA> {

    public List<Error> gatherAllErrors(final PCA puzzleConfigAggregate) {
        var errors = createErrorsList();
        if (puzzleConfigAggregate.getTargetNumberOfPuzzles() <= 0) {
            errors.add(new Error("%s must be greater than 0".formatted(PuzzleConfigAggregate.TARGET_NUMBER_OF_PUZZLES_PROP)));
        }
        return errors;
    }

}
