package org.swetlokognatsk.earking_out.infrastructure.adapters.achievements;

import static org.swetlokognatsk.earking_out.SpringProfiles.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.Achievement;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementTechnicalName;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementDTO;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementExtendedDTO;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.achievements.UnlockedAchievementDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.asdf;
import org.swetlokognatsk.earking_out.core.ports.achievements.UnlockedAchievementsRepository;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker.NewUnlockedAchievementDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import static org.swetlokognatsk.earking_out.infrastructure.adapters.achievements.UnlockedAchievementEntity.*;

// TODO DRY refactoring
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

    public boolean exists(final UserId userId, final AchievementTechnicalName technicalName) {
        var jpql = """
                SELECT 1
                FROM UnlockedAchievementEntity unlockedAchievement
                WHERE unlockedAchievement.userId = :userId
                    AND unlockedAchievement.achievement.technicalName = :technicalName
                """;
        var exists = getEntityManager()
                .createQuery(jpql, Integer.class)
                .setParameter("userId", userId.id())
                .setParameter("technicalName", technicalName.name())
                .getSingleResultOrNull();
        return exists != null;
    }

    public AchievementId getAchievementId(final AchievementTechnicalName technicalName) {
        var jpql = """
                SELECT achievement.id
                FROM AchievementEntity achievement
                WHERE achievement.technicalName = :technicalName
                """;
        var achievementId = getEntityManager()
                .createQuery(jpql, UUID.class)
                .setParameter("technicalName", technicalName.name())
                .getSingleResult();
        return new AchievementId(achievementId);
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

        return mapToAggregate(entity, false);
    }

    public Set<UnlockedAchievementAggregate> get(final UserId userId) {
        var entities = queryByUserId(userId)
                .getResultList();
        return mapToAggregates(entities, false);
    }

    private TypedQuery<UnlockedAchievementEntity> queryByUserId(final UserId userId) {
        var jpql = """
                SELECT unlockedAchievement
                FROM UnlockedAchievementEntity unlockedAchievement
                WHERE unlockedAchievement.userId = :userId
                """;
        return getEntityManager()
                .createQuery(jpql, UnlockedAchievementEntity.class)
                .setParameter("userId", userId.id());
    }

    private Set<UnlockedAchievementAggregate> mapToAggregates(final List<UnlockedAchievementEntity> entities, final boolean loadAchievement) {
        return entities.stream()
                .map(entity -> mapToAggregate(entity, loadAchievement))
                .collect(Collectors.toSet());
    }

    private UnlockedAchievementAggregate mapToAggregate(final UnlockedAchievementEntity entity, final boolean loadAchievement) {
        var id = new UnlockedAchievementId(entity.getId());
        var userId = new UserId(entity.getUserId());
        var achievementId = new AchievementId(entity.getAchievementId());
        var unlockedOn = entity.getUnlockedOn();
        var achievement = loadAchievement
                ? mapAchievement(entity.getAchievement())
                : null;
        return factory.create(id, userId, achievementId, unlockedOn, achievement);
    }

    private Achievement mapAchievement(final AchievementEntity entity) {
        var id = new AchievementId(entity.getId());
        var technicalName = new AchievementTechnicalName(entity.getTechnicalName());
        return new Achievement(id, technicalName);
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

        return mapToAggregate(entity, false);
    }

    public Set<UnlockedAchievementDTO> getDto(final UserId userId) {
        var aggregates = get(userId)
                .toArray(UnlockedAchievementAggregate[]::new);
        var dtos = UnlockedAchievementDTOAssembler.assemble(aggregates);
        return new HashSet<>(Arrays.asList(dtos));
    }

    public Set<UnlockedAchievementExtendedDTO> getDtoWithAchievements(final UserId userId) {
        var entityGraph = entityManager.getEntityGraph(ENTITY_GRAPH_WITH_ACHIEVEMENTS);
        var entities = queryByUserId(userId)
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
                .getResultList();
        var aggregates = mapToAggregates(entities, true)
                .toArray(UnlockedAchievementAggregate[]::new);
        var dtos = UnlockedAchievementDTOAssembler.assembleExtended(aggregates);
        return new HashSet<UnlockedAchievementExtendedDTO>(Arrays.asList(dtos));
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
