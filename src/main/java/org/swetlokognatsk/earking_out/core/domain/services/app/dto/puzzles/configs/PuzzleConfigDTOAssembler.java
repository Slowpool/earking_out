package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.EndPuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

// TODO register this service as singleton in DI? or just use static methods with static fields?
public final class PuzzleConfigDTOAssembler {
    protected static final Map<Exercise, EndPuzzleConfigDTOAssembler<?, ?, ?>> endDtoAssemblers = new HashMap<>();

    static {
        EndPuzzleConfigDTOAssembler<?, ?, ?> dtoAssembler;
        for (var exercise : ExercisesFactory.getAll()) {
            dtoAssembler = EndDTOAssemblersFactory.create(exercise);
            endDtoAssemblers.put(exercise, dtoAssembler);
        }
    }

    private PuzzleConfigDTOAssembler() {
    }

    public static <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> PCDTO getPuzzleConfigDTO(E exercise) {
        var puzzleConfigRepository = DI.get(PuzzleConfigRepository.class);
        var puzzleConfig = puzzleConfigRepository.get(exercise);
        // TODO srp violation - this method must be just `assemble()`. getting config from repo is not responsibility of DTOAssembler. everything above this line in this method is violation, everything below is fine
        var dto = assemble(puzzleConfig);
        return (PCDTO) dto;
    }

    public static <E extends Exercise, PCA extends PuzzleConfigAggregate<E>, PCDTO extends PuzzleConfigDTO<E>> PCDTO assemble(final PCA aggregate) {
        var endDtoAssembler = (EndPuzzleConfigDTOAssembler<E, PCA, PCDTO>) endDtoAssemblers.get(aggregate.exercise);
        var dto = endDtoAssembler.assemble(aggregate);
        return dto;
    }
}
