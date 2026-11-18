package org.swetlokognatsk.earking_out.core.domain.model.achievements;

import org.swetlokognatsk.earking_out.core.domain.model.base.Entity;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;

public class UnlockedAchievement extends Entity<UnlockedAchievementId> {

    public final UserId userId;
    public final AchievementId achievementId;
    
    public UnlockedAchievement(final UnlockedAchievementId id, final UserId userId, final AchievementId achievementId) {
        super(id);
        this.userId = userId;
        this.achievementId = achievementId;
    }
}
