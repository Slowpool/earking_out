package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public abstract class EndSessionDTOAssembler<E extends Exercise, S extends Solution, P extends Puzzle<E, S>, PCDTO extends PuzzleConfigDTO<E>, SA extends SessionAggregate<E, S, P, PCDTO>, SADTO extends SessionDTO<E, P, PCDTO, SA>> {

    public abstract SADTO assemble(final SA sessionAggregate);

}
