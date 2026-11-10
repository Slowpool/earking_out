package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

@Component
public final class SessionAggregateDTOAssembler {
    private final Map<Exercise, EndSessionAggregateDTOAssembler<?, ?, ?, ?, ?, ?>> endDtoAssemblers = new HashMap<>();

    public SessionAggregateDTOAssembler(final EndSessionAggregateDTOAssemblersFactory assemblersFactory) {
        EndSessionAggregateDTOAssembler<?, ?, ?, ?, ?, ?> endSessionAggregateDTOAssembler;
        for (var exercise : ExercisesFactory.getAll()) {
            endSessionAggregateDTOAssembler = assemblersFactory.create(exercise);
            endDtoAssemblers.put(exercise, endSessionAggregateDTOAssembler);
        }
    }

    public <E extends Exercise, S extends Solution, P extends Puzzle<E, S>, PCDTO extends PuzzleConfigDTO<E>, SA extends SessionAggregate<E, S, P, PCDTO>, SADTO extends SessionAggregateDTO<E, P, PCDTO, SA>> SADTO assemble(final SA sessionAggregate) {
        var endDtoAssembler = (EndSessionAggregateDTOAssembler<E, S, P, PCDTO, SA, SADTO>) endDtoAssemblers.get(sessionAggregate.getPuzzleConfig().exercise);
        var sessionAggregateDto = endDtoAssembler.assemble(sessionAggregate);
        return (SADTO) sessionAggregateDto;
    }
}
