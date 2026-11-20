package org.swetlokognatsk.earking_out.infrastructure.adapters.achievements;

import static org.swetlokognatsk.earking_out.SpringProfiles.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.asdf;
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
        return exists != null;
    }

    private <T> TypedQuery<T> queryByUserAndAchievement(final String jpql, final Class<T> resultType, final UserId userId, final AchievementId achievementId) {
        return getEntityManager()
                .createQuery(jpql, resultType)
                .setParameter("userId", userId.id())
                .setParameter("achievementId", achievementId.id);
    }

    public UnlockedAchievementAggregate get(final UnlockedAchievementId id) {
        var entity = entityManager.find(UnlockedAchievementEntity.class, id.id());
        if (entity == null) {
            throw new AggregateNotFoundException();
        }

        return mapToAggregate(entity);
    }

    public Set<UnlockedAchievementAggregate> get(final UserId userId) {
        var jpql = """
                SELECT unlockedAchievement
                FROM UnlockedAchievementEntity unlockedAchievement
                WHERE unlockedAchievement.userId = :userId
                """;
        var entities = getEntityManager()
                .createQuery(jpql, UnlockedAchievementEntity.class)
                .setParameter("userId", userId.id())
                .getResultList();
        return mapToAggregates(entities);
    }

    private Set<UnlockedAchievementAggregate> mapToAggregates(final List<UnlockedAchievementEntity> entities) {
        return entities.stream()
                .map(this::mapToAggregate)
                .collect(Collectors.toSet());
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
    public UnlockedAchievementId saveAndGetId(final UnlockedAchievementAggregate aggregate) {
        var entity = innerSave(aggregate);

        return new UnlockedAchievementId(entity.getId());
    }

    @Transactional
    public void save(final UnlockedAchievementAggregate aggregate) {
        innerSave(aggregate);
    }

    private UnlockedAchievementEntity innerSave(final UnlockedAchievementAggregate aggregate) {
        var entity = mapToEntity(aggregate);

        entityManager.persist(entity);
        return entity;
    }

    private UnlockedAchievementEntity mapToEntity(final UnlockedAchievementAggregate aggregate) {
        // TODO where to put null checks?
        var id = aggregate.getId()
                .id();
        var achievementId = aggregate.achievementId.id;
        var userId = aggregate.userId.id();
        var unlockedOn = aggregate.unlockedOn;
        var notificationIsSent = aggregate.notificationIsSent;
        if (id == null) {
            return new UnlockedAchievementEntity(userId, achievementId, unlockedOn, notificationIsSent);
        } else {
            var entity = new UnlockedAchievementEntity(id, userId, achievementId, unlockedOn, notificationIsSent);
            return entityManager.merge(entity);
        }
    }
}
