package org.swetlokognatsk.earking_out.core.domain.services.app.exceptions;

import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

// TODO draft version
public class InvalidPuzzleConfigException extends RuntimeException {
    public final PuzzleConfigDTO<?> puzzleConfigDto;

    public InvalidPuzzleConfigException(final PuzzleConfigDTO<?> puzzleConfigDto) {
        this.puzzleConfigDto = puzzleConfigDto;
    }
}
