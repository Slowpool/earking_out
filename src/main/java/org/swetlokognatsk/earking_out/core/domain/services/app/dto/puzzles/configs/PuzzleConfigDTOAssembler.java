package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.EndDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

// TODO register this service as singleton in DI? or just use static methods with static fields?
public final class PuzzleConfigDTOAssembler {
    protected static final Map<Exercise, EndDTOAssembler<?, ?, ?>> dtoAssemblers = new HashMap<>();

    static {
        EndDTOAssembler<?, ?, ?> dtoAssembler;
        for (var exercise : ExercisesFactory.getAll()) {
            dtoAssembler = EndDTOAssemblersFactory.create(exercise);
            dtoAssemblers.put(exercise, dtoAssembler);
        }
    }

    public static <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> void assemble(Class<E> exerciseClass, E exercise) {
        var puzzleConfigRepository = DI.get(PuzzleConfigRepository.class);
        var puzzleConfig = puzzleConfigRepository.get(exercise);

        var endDtoAssembler = dtoAssemblers.get(exercise);
        var dto = endDtoAssembler.assemble(puzzleConfig);
        return (PCDTO) dto;

    }
}
