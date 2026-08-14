package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

public final class InMemoryPuzzleConfigRepository implements PuzzleConfigRepository {
    private final Map<Exercise, PuzzleConfigAggregate<?>> aggregates = new HashMap<>();

    private final AbstractPuzzleConfigAggregatesFactory abstractPuzzleConfigAggregatesFactory;
    private final PuzzleConfigDTOAssembler dtoAssembler;

    public InMemoryPuzzleConfigRepository(final AbstractPuzzleConfigAggregatesFactory abstractPuzzleConfigAggregatesFactory, final PuzzleConfigDTOAssembler dtoAssembler) {
        this.abstractPuzzleConfigAggregatesFactory = abstractPuzzleConfigAggregatesFactory;
        this.dtoAssembler = dtoAssembler;

        seedConfigs();
    }

    private void seedConfigs() {
        var exercises = ExercisesFactory.getAll();
        PuzzleConfigAggregate<?> puzzleConfigAggregate;
        for (var exercise : exercises) {
            var factory = createFactory(exercise);
            puzzleConfigAggregate = factory.createDefault();
            aggregates.put(exercise, puzzleConfigAggregate);
        }
    }

    private <E extends Exercise, PCAF extends PuzzleConfigAggregatesFactory<? extends PuzzleConfigAggregate<E>>> PCAF createFactory(final E exercise) {
        return abstractPuzzleConfigAggregatesFactory.createFactory(exercise);
    }

    public <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA genericGet(final E exercise) {
        var puzzleConfigAggregate = aggregates.get(exercise);
        if (puzzleConfigAggregate == null) {
            throw new IllegalArgumentException("unknown exercise: " + exercise);
        }

        puzzleConfigAggregate = createDeepCopy(puzzleConfigAggregate);
        // TODO refresh pianoKeyboardAggregates before returning (pull new ones from pianoKeyboardRepository)
        return (PCA) puzzleConfigAggregate;
    }

    private <PCA extends PuzzleConfigAggregate<?>> PCA createDeepCopy(final PCA puzzleConfigAggregate) {
        var puzzleConfigAggregateFactory = (PuzzleConfigAggregatesFactory<PCA>) createFactory(puzzleConfigAggregate.getId());
        PCA puzzleConfigAggregateCopy = puzzleConfigAggregateFactory.createDeepCopy(puzzleConfigAggregate);
        return (PCA) puzzleConfigAggregateCopy;
    }

    public void genericSave(PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        puzzleConfigAggregate = createDeepCopy(puzzleConfigAggregate);
        aggregates.put(puzzleConfigAggregate.getId(), puzzleConfigAggregate);
    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> PCDTO getPuzzleConfigDTO(E exercise) {
        var puzzleConfig = genericGet(exercise);
        var dto = dtoAssembler.assemble(puzzleConfig);
        return (PCDTO) dto;
    }
}
