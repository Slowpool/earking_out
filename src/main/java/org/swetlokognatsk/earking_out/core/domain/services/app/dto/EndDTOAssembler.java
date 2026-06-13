package org.swetlokognatsk.earking_out.core.domain.services.app.dto;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public abstract class EndDTOAssembler<E extends Exercise, PCA extends PuzzleConfigAggregate<E>, PCDTO extends PuzzleConfigDTO<E>> {
    public abstract PCDTO assemble(final PCA puzzleConfigAggregate);
}
