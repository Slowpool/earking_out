package org.swetlokognatsk.earking_out.infrastructure.adapters.achievements;

import static org.swetlokognatsk.earking_out.SpringProfiles.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.NewUnlockedAchievement;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.achievements.UnlockedAchievementsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Repository
@Profile(JPA)
@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SpringJpaUnlockedAchievementsRepository implements UnlockedAchievementsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final UnlockedAchievementAggregatesFactory factory;

    public boolean exists(final UserId userId, final AchievementId achievementId) {
        var jpql = """
                SELECT 1
                FROM UnlockedAchievementEntity unlockedAchievement
                WHERE unlockedAchievement.userId = :userId
                    AND unlockedAchievement.achievementId = :achievementId
                """;
        var exists = queryByUserAndAchievement(jpql, Integer.class, userId, achievementId)
                .getSingleResultOrNull();
        return exists == 1;
    }

    private <T> TypedQuery<T> queryByUserAndAchievement(final String jpql, final Class<T> resultType, final UserId userId, final AchievementId achievementId) {
        return getEntityManager()
                .createQuery(jpql, resultType)
                .setParameter("userId", userId.id())
                .setParameter("achievementId", achievementId.id);
    }

    public UnlockedAchievementAggregate get(final UnlockedAchievementId id) {
        var entity = entityManager.find(UnlockedAchievementEntity.class, id);
        if (entity == null) {
            throw new AggregateNotFoundException();
        }

        return mapToAggregate(entity);
    }

    private UnlockedAchievementAggregate mapToAggregate(final UnlockedAchievementEntity entity) {
        var id = new UnlockedAchievementId(entity.getId());
        var userId = new UserId(entity.getUserId());
        var achievementId = new AchievementId(entity.getAchievementId());
        var unlockedOn = entity.getUnlockedOn();
        return factory.create(id, userId, achievementId, unlockedOn);
    }

    public UnlockedAchievementAggregate get(final UserId userId, final AchievementId achievementId) {
        var jpql = """
                SELECT unlockedAchievement
                FROM UnlockedAchievementEntity unlockedAchievement
                WHERE unlockedAchievement.userId = :userId
                    AND unlockedAchievement.achievementId = :achievementId
                """; // TODO does `LIMIT` make sense here?
        var entity = queryByUserAndAchievement(jpql, UnlockedAchievementEntity.class, userId, achievementId)
                .getSingleResultOrNull();
        if (entity == null) {
            throw new AggregateNotFoundException();
        }

        return mapToAggregate(entity);
    }

    @Transactional
    public void save(final UnlockedAchievementAggregate newUnlockedAchievement) {
        var entity = mapToEntity(newUnlockedAchievement);

        entityManager.persist(entity);
    }

    private UnlockedAchievementEntity mapToEntity(final UnlockedAchievementAggregate aggregate) {
        return new UnlockedAchievementEntity();
    }
}
