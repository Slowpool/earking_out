package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import static org.swetlokognatsk.earking_out.SpringProfiles.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactoryResolver;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.CachingPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.AccessLevel;

// TODO integration tests. definitely
@Repository
@Primary
@Profile({ DESKTOP, WEB })
@Getter(AccessLevel.PRIVATE)
public class SpringJpaPuzzleConfigRepository extends BasePuzzleConfigRepository {

    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;
    private final UserResolver userResolver;

    // TODO use @Transactional
    public SpringJpaPuzzleConfigRepository(final PuzzleConfigAggregatesFactoryResolver puzzleConfigAggregatesFactoryResolver, final PuzzleConfigJsonSerializer puzzleConfigJsonSerializer, final PuzzleConfigDTOAssembler dtoAssembler, final CachingPuzzleConfigRepository cacheRepository, final EntityManager entityManager, final PlatformTransactionManager transactionManager, final UserResolver userResolver) {
        this.entityManager = entityManager;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.userResolver = userResolver;
        super(puzzleConfigAggregatesFactoryResolver, puzzleConfigJsonSerializer, dtoAssembler, cacheRepository);
    }

    public void saveImpl(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var serializedPuzzleConfig = getPuzzleConfigJsonSerializer()
                .serializePuzzleConfig(puzzleConfigAggregate);
        var stringedExercise = puzzleConfigAggregate.getId()
                .toString();

        getTransactionTemplate()
                .execute(status -> {
                    var puzzleConfigEntity = findByExercise(puzzleConfigAggregate.getId());
                    if (puzzleConfigEntity == null) {
                        puzzleConfigEntity = new PuzzleConfigEntity(getCurrentUserId(), stringedExercise, serializedPuzzleConfig);
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
        var id = new PuzzleConfigId(exerciseId, getCurrentUserId());
        var puzzleConfig = getEntityManager()
                .find(PuzzleConfigEntity.class, id);
        return puzzleConfig;
    }

    private int getCurrentUserId() {
        return userResolver.getCurrentUserId()
                .id();
    }

    public <E extends Exercise, PCDTO extends PuzzleConfigDTO<E>> PCDTO getPuzzleConfigDTO(final E exercise, final UserId userId) {
        // yes, naively relying on cache repo
        return getCacheRepository()
                .getPuzzleConfigDTO(exercise, userId);
    }
}
