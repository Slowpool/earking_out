package org.swetlokognatsk.earking_out.core.domain.model.achievements;

import org.swetlokognatsk.earking_out.core.domain.model.base.Entity;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;

// TODO think about it. yet it's a latch, just a proof-of-the-concept for kafka
public final class NewUnlockedAchievement extends Entity<AchievementId> {
    private static final long serialVersionUID = 1L;

    public final UserId userId;

    public NewUnlockedAchievement(final AchievementId id, final UserId userId) {
        super(id);
        this.userId = userId;
    }
}
