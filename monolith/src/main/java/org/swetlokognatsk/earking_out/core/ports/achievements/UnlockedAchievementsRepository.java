package org.swetlokognatsk.earking_out.core.ports.achievements;

import java.util.Set;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementId;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementDTO;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementExtendedDTO;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.base.TypedAggregateRepository;

public interface UnlockedAchievementsRepository extends TypedAggregateRepository<UnlockedAchievementId, UnlockedAchievementAggregate> {
    boolean exists(UserId userId, AchievementId achievementId);

    UnlockedAchievementAggregate get(UserId userId, AchievementId achievementId);

    Set<UnlockedAchievementAggregate> get(UserId userId);

    Set<UnlockedAchievementDTO> getDto(UserId userId);

    Set<UnlockedAchievementExtendedDTO> getDtoWithAchievements(UserId userId);

    UnlockedAchievementId saveAndGetId(UnlockedAchievementAggregate aggregate);
}
