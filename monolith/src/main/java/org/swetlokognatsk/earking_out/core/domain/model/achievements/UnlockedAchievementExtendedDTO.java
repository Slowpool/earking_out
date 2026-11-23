package org.swetlokognatsk.earking_out.core.domain.model.achievements;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;

public final class UnlockedAchievementExtendedDTO extends UnlockedAchievementDTO {

    public final AchievementTechnicalName technicalName;

    public UnlockedAchievementExtendedDTO(final UnlockedAchievementId id, final UserId userId, final AchievementId achievementId, final LocalDateTime unlockedOn, final boolean notificationIsSen, final AchievementTechnicalName technicalName) {
        super(id, userId, achievementId, unlockedOn, notificationIsSen);
        this.technicalName = technicalName;
    }

    public UnlockedAchievementExtendedDTO(final UnlockedAchievementDTO dto, final AchievementTechnicalName technicalName) {
        super(dto.id, dto.userId, dto.achievementId, dto.unlockedOn, dto.notificationIsSen);
        this.technicalName = technicalName;
    }

}
