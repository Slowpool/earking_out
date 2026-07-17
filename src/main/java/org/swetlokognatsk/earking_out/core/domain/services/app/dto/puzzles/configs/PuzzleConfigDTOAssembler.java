package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

public final class PuzzleConfigDTOAssembler {
    protected static final Map<Exercise, EndPuzzleConfigDTOAssembler<?, ?, ?>> endDtoAssemblers = new HashMap<>();
    protected static final PuzzleConfigRepository puzzleConfigRepository = DI.get(PuzzleConfigRepository.class);

    static {
        EndPuzzleConfigDTOAssembler<?, ?, ?> dtoAssembler;
        for (var exercise : ExercisesFactory.getAll()) {
            dtoAssembler = EndPuzzleConfigDTOAssemblersFactory.create(exercise);
            endDtoAssemblers.put(exercise, dtoAssembler);
        }
    }

    private PuzzleConfigDTOAssembler() {
    }

    public static <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> PCDTO getPuzzleConfigDTO(E exercise) {
        var puzzleConfig = puzzleConfigRepository.get(exercise);
        var dto = assemble(puzzleConfig);
        return (PCDTO) dto;
    }

    public static <E extends Exercise, PCA extends PuzzleConfigAggregate<E>, PCDTO extends PuzzleConfigDTO<E>> PCDTO assemble(final PCA aggregate) {
        var endDtoAssembler = (EndPuzzleConfigDTOAssembler<E, PCA, PCDTO>) endDtoAssemblers.get(aggregate.getId());
        var dto = endDtoAssembler.assemble(aggregate);
        return dto;
    }
}
