package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.exceptions;

import java.util.List;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.Error;

public class InvalidPuzzleConfigException extends Exception {
    public final PuzzleConfigDTO<?> puzzleConfigDto;
    public final List<Error> errors;

    public InvalidPuzzleConfigException(final PuzzleConfigDTO<?> puzzleConfigDto, final List<Error> errors) {
        this.puzzleConfigDto = puzzleConfigDto;
        this.errors = errors;
    }
}
