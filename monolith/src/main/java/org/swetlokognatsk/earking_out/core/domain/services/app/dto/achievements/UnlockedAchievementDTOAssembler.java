package org.swetlokognatsk.earking_out.core.domain.services.app.dto.achievements;

import java.util.Arrays;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.Achievement;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementDTO;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementExtendedDTO;

public final class UnlockedAchievementDTOAssembler {

    public static final UnlockedAchievementDTO assemble(final UnlockedAchievementAggregate aggregate) {
        return new UnlockedAchievementDTO(aggregate.getId(), aggregate.userId, aggregate.achievementId, aggregate.unlockedOn, aggregate.notificationIsSent);
    }

    public static final UnlockedAchievementExtendedDTO assembleExtended(final UnlockedAchievementAggregate aggregate) {
        return new UnlockedAchievementExtendedDTO(assemble(aggregate), aggregate.achievement.technicalName);
    }

    public static final UnlockedAchievementDTO[] assemble(final UnlockedAchievementAggregate[] aggregates) {
        return Arrays.stream(aggregates)
                .map(UnlockedAchievementDTOAssembler::assemble)
                .toArray(UnlockedAchievementDTO[]::new);
    }

    public static final UnlockedAchievementExtendedDTO assembleExtended(final UnlockedAchievementAggregate[] aggregates) {
        return Arrays.stream(aggregates)
                .map(UnlockedAchievementDTOAssembler::assembleExtended)
                .toArray(UnlockedAchievementExtendedDTO[]::new);
    }
}
