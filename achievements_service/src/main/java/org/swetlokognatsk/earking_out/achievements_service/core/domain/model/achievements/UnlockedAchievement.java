package org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements;

import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;

public record UnlockedAchievement(AchievementId id, UserId userId) {
}
