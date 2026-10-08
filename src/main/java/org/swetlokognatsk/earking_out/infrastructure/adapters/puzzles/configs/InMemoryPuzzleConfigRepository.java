package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactoryResolver;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleConfigNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;

@Repository
@Getter(AccessLevel.PRIVATE)
public class InMemoryPuzzleConfigRepository implements PuzzleConfigRepository {
    private final Map<PuzzleConfigId, PuzzleConfigAggregate<?>> aggregates = new HashMap<>();

    private final PuzzleConfigAggregatesFactoryResolver puzzleConfigAggregatesFactoryResolver;
    private final PuzzleConfigDTOAssembler dtoAssembler;
    private final UserResolver userResolver;

    public InMemoryPuzzleConfigRepository(final PuzzleConfigAggregatesFactoryResolver puzzleConfigAggregatesFactoryResolver, final PuzzleConfigDTOAssembler dtoAssembler, final UserResolver userResolver) {
        this.puzzleConfigAggregatesFactoryResolver = puzzleConfigAggregatesFactoryResolver;
        this.dtoAssembler = dtoAssembler;
        this.userResolver = userResolver;

        // TODO it should be used only in tests as i remember
        // seedConfigs();
    }

    private <E extends Exercise, PCAF extends PuzzleConfigAggregatesFactory<? extends PuzzleConfigAggregate<E>>> PCAF createFactory(final E exercise) {
        return getPuzzleConfigAggregatesFactoryResolver()
                .resolveFactory(exercise);
    }

    public <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA genericGet(final E exercise) {
        var puzzleConfigAggregate = innerGet(exercise);
        if (puzzleConfigAggregate == null) {
            throw new PuzzleConfigNotFoundException("puzzle config is not found for exercise: " + exercise);
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

    public void save(PuzzleConfigAggregate<Exercise> puzzleConfigAggregate) {
        puzzleConfigAggregate = createDeepCopy(puzzleConfigAggregate);
        saveImpl(puzzleConfigAggregate);
    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> PCDTO getPuzzleConfigDTO(E exercise) {
        var puzzleConfig = genericGet(exercise);
        var dto = getDtoAssembler()
                .assemble(puzzleConfig);
        return (PCDTO) dto;
    }

    private <E extends Exercise> PuzzleConfigAggregate<E> innerGet(final E exercise) {
        return (PuzzleConfigAggregate<E>) getAggregates()
                .get(buildKey(exercise));
    }

    private void saveImpl(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var key = buildKey(puzzleConfigAggregate.getId());
        getAggregates()
                .put(key, puzzleConfigAggregate);
    }

    private PuzzleConfigId buildKey(final Exercise exercise) {
        var userId = getUserResolver()
                .getCurrentUserId()
                .id();
        return new PuzzleConfigId(exercise.toString(), userId);
    }

    public void actualizeCache() {
        // no impl because it's caching repo itself        
    }
}
