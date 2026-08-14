package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

// TODO make non-static
public final class SessionDTOAssembler {
    private final static Map<Exercise, EndSessionDTOAssembler<?, ?, ?, ?, ?, ?>> endDtoAssemblers = new HashMap<>();

    static {
        var endSessionDtoAssemblersFactory = DI.get(EndSessionDTOAssemblersFactory.class);

        EndSessionDTOAssembler<?, ?, ?, ?, ?, ?> endSessionDtoAssembler;
        for (var exercise : ExercisesFactory.getAll()) {
            endSessionDtoAssembler = endSessionDtoAssemblersFactory.create(exercise);
            endDtoAssemblers.put(exercise, endSessionDtoAssembler);
        }
    }

    public static <E extends Exercise, S extends Solution, P extends Puzzle<E, S>, PCDTO extends PuzzleConfigDTO<E>, SA extends SessionAggregate<E, S, P, PCDTO>, SADTO extends SessionDTO<E, P, PCDTO, SA>> SADTO assemble(final SA sessionAggregate) {
        var endDtoAssembler = (EndSessionDTOAssembler<E, S, P, PCDTO, SA, SADTO>) endDtoAssemblers.get(sessionAggregate.getPuzzleConfig().exercise);
        var SessionDto = endDtoAssembler.assemble(sessionAggregate);
        return (SADTO) SessionDto;
    }

    public static SessionDTO<?, ?, ?, ?> getSessionDTO(final SessionId sessionId) {
        var sessionRepositoryDelegator = DI.get(SessionRepositoryDelegator.class);
        var sessionAggregate = sessionRepositoryDelegator.get(sessionId);

        var SessionDto = assemble(sessionAggregate);
        return SessionDto;
    }
}
