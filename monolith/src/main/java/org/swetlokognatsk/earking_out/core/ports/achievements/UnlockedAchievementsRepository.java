package org.swetlokognatsk.earking_out.core.ports.achievements;

import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.NewUnlockedAchievement;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.base.TypedAggregateRepository;

public interface UnlockedAchievementsRepository extends TypedAggregateRepository<UnlockedAchievementId, UnlockedAchievementAggregate> {
    boolean exists(UserId userId, AchievementId achievementId);

    void save(NewUnlockedAchievement newUnlockedAchievement);
}
