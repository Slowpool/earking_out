package org.swetlokognatsk.earking_out.app.web.services.achievements;

import java.util.Set;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementTypes;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.UnlockedAchievement;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;

@Component
public class AchievementsService {

    public Set<?> get(final AchievementTypes type, final UserId userId) {
        return switch(type) {
            case UNLOCKED -> getUnlockedAchievements(userId);
            default -> throw new IllegalArgumentException("unknown achievement type: " + type.toString());
        };
    }

    private Set<UnlockedAchievement> getUnlockedAchievements(final UserId userId) {

    }
}
