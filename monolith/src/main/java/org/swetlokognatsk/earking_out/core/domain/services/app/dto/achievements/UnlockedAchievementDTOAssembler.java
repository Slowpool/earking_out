package org.swetlokognatsk.earking_out.core.domain.services.app.dto.achievements;

import java.util.Arrays;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievementDTO;

public final class UnlockedAchievementDTOAssembler {

    public static final UnlockedAchievementDTO assemble(final UnlockedAchievementAggregate aggregate) {
        return new UnlockedAchievementDTO(aggregate.getId(), aggregate.userId, aggregate.achievementId, aggregate.unlockedOn, aggregate.notificationIsSent);
    }

    public static final UnlockedAchievementDTO[] assemble(final UnlockedAchievementAggregate[] aggregates) {
        return Arrays.stream(aggregates)
                .map(UnlockedAchievementDTOAssembler::assemble)
                .toArray(UnlockedAchievementDTO[]::new);
    }
}
