package org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements;

import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnlockedAchievement {
    public final AchievementId id;
    public final UserId userId;
}
