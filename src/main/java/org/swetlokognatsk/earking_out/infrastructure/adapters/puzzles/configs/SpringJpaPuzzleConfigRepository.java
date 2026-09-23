package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactoryResolver;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import jakarta.persistence.EntityManager;

public class SpringJpaPuzzleConfigRepository extends PersistentPuzzleConfigRepository {

    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;

    public SpringJpaPuzzleConfigRepository(final PuzzleConfigAggregatesFactoryResolver puzzleConfigAggregatesFactoryResolver, final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer, final PuzzleConfigDTOAssembler dtoAssembler, final InMemoryPuzzleConfigRepository cacheRepository, final EntityManager entityManager, final PlatformTransactionManager transactionManager) {
        this.entityManager = entityManager;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        super(puzzleConfigAggregatesFactoryResolver, puzzleConfigJsonSerializer, dtoAssembler, cacheRepository);
    }

    public void genericSave(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var serializedPuzzleConfig = puzzleConfigJsonSerializer.serializePuzzleConfig(puzzleConfigAggregate);
        var stringedExercise = puzzleConfigAggregate.getId()
                .toString();

        transactionTemplate.execute(status -> {
            var puzzleConfigEntity = findByExercise(puzzleConfigAggregate.getId());
            if (puzzleConfigEntity == null) {
                puzzleConfigEntity = new PuzzleConfigEntity(stringedExercise, serializedPuzzleConfig);
            } else {
                puzzleConfigEntity.setSerializedPuzzleConfig(serializedPuzzleConfig);
            }

            entityManager.persist(puzzleConfigEntity);
            return null;
        });
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
