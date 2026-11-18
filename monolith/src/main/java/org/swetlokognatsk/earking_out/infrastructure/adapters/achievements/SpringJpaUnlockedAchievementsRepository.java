package org.swetlokognatsk.earking_out.infrastructure.adapters.achievements;

import org.springframework.stereotype.Repository;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.NewUnlockedAchievement;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.achievements.UnlockedAchievementsRepository;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@WebRepository
@AllArgsConstructor
public class SpringJpaUnlockedAchievementsRepository implements UnlockedAchievementsRepository {
    private final EntityManager entityManager;

    public boolean exists(final UserId userId, final AchievementId achievementId) {
        var jpql = """
                SELECT 1
                FROM UnlockedAchievementEntity unlockedAchievement
                WHERE unlockedAchievement.userId = :userId
                    AND unlockedAchievement.achievementId = :achievementId
                """;
        var exists = entityManager.createQuery(jpql, Integer.class)
                .setParameter("userId", userId.id())
                .setParameter("achievementId", achievementId.id)
                .getSingleResultOrNull();
        return exists == 1;
    }

    public void save(final NewUnlockedAchievement newUnlockedAchievement) {

    }

    public UnlockedAchievementAggregate get(final UnlockedAchievementId id) {

    }

    public void save(final UnlockedAchievementAggregate newUnlockedAchievement) {

    }
}
