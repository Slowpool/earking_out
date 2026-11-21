package org.swetlokognatsk.earking_out.core.domain.model.achievements;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import lombok.Data;

@Data
public sealed class UnlockedAchievementDTO permits UnlockedAchievementExtendedDTO  {

    public final UnlockedAchievementId id;
    public final UserId userId;
    public final AchievementId achievementId;
    public final LocalDateTime unlockedOn;
    public final boolean notificationIsSen;
}
