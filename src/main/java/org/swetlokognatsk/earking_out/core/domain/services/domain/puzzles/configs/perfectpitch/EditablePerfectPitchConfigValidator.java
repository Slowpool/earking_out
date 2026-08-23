package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.perfectpitch;

import java.util.List;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.PuzzleConfigValidator;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.Error;

abstract class EditablePerfectPitchConfigValidator<PPCA extends PerfectPitchConfigAggregate<?>> extends PuzzleConfigValidator<PPCA> {

    public List<Error> gatherAllErrors(final PPCA puzzleConfigAggregate) {
        var errors = super.gatherAllErrors(puzzleConfigAggregate);
        // TODO EditablePerfectPitchConfigValidator
        
        return errors;
    }
}
