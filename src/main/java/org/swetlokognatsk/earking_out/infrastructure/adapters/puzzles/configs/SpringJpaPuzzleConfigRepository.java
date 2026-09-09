package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Primary
public final class SpringJpaPuzzleConfigRepository extends PersistentPuzzleConfigRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public SpringJpaPuzzleConfigRepository(final AbstractPuzzleConfigAggregatesFactory abstractPuzzleConfigAggregatesFactory, final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer, final PuzzleConfigDTOAssembler dtoAssembler, final InMemoryPuzzleConfigRepository cacheRepository) {
        super(abstractPuzzleConfigAggregatesFactory, puzzleConfigJsonSerializer, dtoAssembler, cacheRepository);
    }

    @Transactional
    public void genericSave(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var serializedPuzzleConfig = puzzleConfigJsonSerializer.serializePuzzleConfig(puzzleConfigAggregate);
        var stringedExercise = puzzleConfigAggregate.getId()
                .toString();
        var puzzleConfigEntity = new PuzzleConfigEntity(stringedExercise, serializedPuzzleConfig);
        entityManager.persist(puzzleConfigEntity);
    }

    protected String getOrCreatePuzzleConfigJson(final Exercise exercise) {
        var puzzleConfigEntity = findByExercise(exercise);
        if (puzzleConfigEntity == null) {
            createAndSaveDefaultConfig(exercise);
            puzzleConfigEntity = findByExercise(exercise);
        }
        return puzzleConfigEntity.getSerializedPuzzleConfig();
    }

    private final PuzzleConfigEntity findByExercise(final Exercise exercise) {
        var exerciseId = exercise.toString();
        var puzzleConfig = entityManager.find(PuzzleConfigEntity.class, exerciseId);
        return puzzleConfig;
    }

}
