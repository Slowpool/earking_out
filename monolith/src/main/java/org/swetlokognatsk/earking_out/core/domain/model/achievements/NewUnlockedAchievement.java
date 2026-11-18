package org.swetlokognatsk.earking_out.core.domain.model.achievements;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;

public record NewUnlockedAchievement(AchievementId id, UserId userId, LocalDateTime unlockedOn) implements Serializable {
    private static final long serialVersionUID = 1L;

}
