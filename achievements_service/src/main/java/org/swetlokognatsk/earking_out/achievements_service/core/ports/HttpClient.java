package org.swetlokognatsk.earking_out.achievements_service.core.ports;

import java.util.Set;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements;

public interface HttpClient {
    /**
     * Modifies input {@code achievements}, removing an unlocked achievements of
     * {@code userId}
     */
    void retainLockedAchievements(UserId userId, Set<Achievements> achievements);
}
