package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.perfectpitch;

import java.util.List;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate.*;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.FinalizedPuzzleConfigValidator;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.Error;

abstract class FinalizedPerfectPitchConfigValidator<PPCA extends PerfectPitchConfigAggregate<?>> extends FinalizedPuzzleConfigValidator<PPCA> {

    public List<Error> gatherAllErrors(final PPCA aggregate) {
        var errors = super.gatherAllErrors(aggregate);

        if (aggregate.getNormalizedNotesForPuzzle().length == 0) {
            errors.add(new Error("%s must have at least one note".formatted(NORMALIZED_NOTES_FOR_PUZZLE_PROP)));
        }

        return errors;
    }
}
