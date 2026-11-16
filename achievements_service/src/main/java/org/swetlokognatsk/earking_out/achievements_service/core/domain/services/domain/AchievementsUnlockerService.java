package org.swetlokognatsk.earking_out.achievements_service.core.domain.services.domain;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.AchievementId;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.UnlockedAchievement;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvent;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvents;
import jakarta.annotation.Nullable;

@Service
public class AchievementsUnlockerService {

    public Set<UnlockedAchievement> tryUnlock(final Set<Achievements> lockedAchievements, final DomainEvents source, final DomainEvent trigger) {
        var unlockedAchievements = new HashSet<UnlockedAchievement>();
        UnlockedAchievement achievement;
        for (var lockedAchievement : lockedAchievements) {
            achievement = tryUnlock(lockedAchievement, source, trigger);
            if (achievement != null) {
                unlockedAchievements.add(achievement);
            }
        }
        return unlockedAchievements;
    }

    public @Nullable UnlockedAchievement tryUnlock(final Achievements lockedAchievement, final DomainEvents source, final DomainEvent trigger) {
        return null;
    }
}
