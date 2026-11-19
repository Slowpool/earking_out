package org.swetlokognatsk.earking_out.core.domain.model.achievements;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;

@Service
public class UnlockedAchievementAggregatesFactory extends AggregatesFactory<UnlockedAchievementAggregate> {

    public UnlockedAchievementAggregatesFactory(final ObjectCloner objectCloner) {
        super(objectCloner);
    }

    public UnlockedAchievementAggregate create(final UnlockedAchievementId id, final UserId userId, final AchievementId achievementId, final LocalDateTime unlockedOn) {
        return new UnlockedAchievementAggregate(id, userId, achievementId, unlockedOn);
    }
}
