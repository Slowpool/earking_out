package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.identity.User;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactoryResolver;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.AccessLevel;

@Repository
@Primary
@Getter(AccessLevel.PRIVATE)
public class SpringJpaPuzzleConfigRepository extends PersistentPuzzleConfigRepository {

    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;
    private final UserId userId;

    public SpringJpaPuzzleConfigRepository(final PuzzleConfigAggregatesFactoryResolver puzzleConfigAggregatesFactoryResolver, final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer, final PuzzleConfigDTOAssembler dtoAssembler, final InMemoryPuzzleConfigRepository cacheRepository, final EntityManager entityManager, final PlatformTransactionManager transactionManager, final User user) {
        this.entityManager = entityManager;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.userId = user.id;
        super(puzzleConfigAggregatesFactoryResolver, puzzleConfigJsonSerializer, dtoAssembler, cacheRepository);
    }

    public void genericSave(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var serializedPuzzleConfig = getPuzzleConfigJsonSerializer()
                .serializePuzzleConfig(puzzleConfigAggregate);
        var stringedExercise = puzzleConfigAggregate.getId()
                .toString();

        getTransactionTemplate()
                .execute(status -> {
                    var puzzleConfigEntity = findByExercise(puzzleConfigAggregate.getId());
                    if (puzzleConfigEntity == null) {
                        puzzleConfigEntity = new PuzzleConfigEntity(userId.id(), stringedExercise, serializedPuzzleConfig);
                    } else {
                        puzzleConfigEntity.setSerializedPuzzleConfig(serializedPuzzleConfig);
                    }

                    getEntityManager()
                            .persist(puzzleConfigEntity);
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
        var id = new PuzzleConfigId(exerciseId, userId.id());
        var puzzleConfig = getEntityManager()
                .find(PuzzleConfigEntity.class, id);
        return puzzleConfig;
    }

}
