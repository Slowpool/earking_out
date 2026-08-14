package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

// TODO make a manager-wrapper to separate the puzzleConfigRepository logic and assembling logic
public final class PuzzleConfigDTOAssembler {
    private static final Map<Exercise, EndPuzzleConfigDTOAssembler<?, ?, ?>> endDtoAssemblers = new HashMap<>();

    static {
        EndPuzzleConfigDTOAssembler<?, ?, ?> dtoAssembler;
        for (var exercise : ExercisesFactory.getAll()) {
            dtoAssembler = EndPuzzleConfigDTOAssemblersFactory.create(exercise);
            endDtoAssemblers.put(exercise, dtoAssembler);
        }
    }

    public PuzzleConfigDTOAssembler() {
    }

    public <E extends Exercise, PCA extends PuzzleConfigAggregate<E>, PCDTO extends PuzzleConfigDTO<E>> PCDTO assemble(final PCA aggregate) {
        var endDtoAssembler = (EndPuzzleConfigDTOAssembler<E, PCA, PCDTO>) endDtoAssemblers.get(aggregate.getId());
        var dto = endDtoAssembler.assemble(aggregate);
        return dto;
    }
}
