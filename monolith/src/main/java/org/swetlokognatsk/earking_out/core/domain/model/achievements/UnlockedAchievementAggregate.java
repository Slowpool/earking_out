package org.swetlokognatsk.earking_out.core.domain.model.achievements;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;

public class UnlockedAchievementAggregate extends AggregateRoot<UnlockedAchievementId> {

    public final UserId userId;
    public final AchievementId achievementId;
    public final LocalDateTime unlockedOn;
    public boolean notificationIsSent;

    public UnlockedAchievementAggregate(final UnlockedAchievementId id, final UserId userId, final AchievementId achievementId, final LocalDateTime unlockedOn) {
        super(id);
        this.userId = userId;
        this.achievementId = achievementId;
        this.unlockedOn = unlockedOn;
    }
}
