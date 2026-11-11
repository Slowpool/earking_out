package org.swetlokognatsk.earking_out.achievements_service.core.domain.services.app;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.DomainEvent;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievement;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.AchievementId;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.UnlockedAchievement;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.messagebroker.MessageBroker;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AchievementService {

    private final MessageBroker messageBroker;

    public void tryMakeNewAchievements(final String serializedDomainEvent) {
        var domainEvent = deserializeDomainEvent(serializedDomainEvent);
        var lockedAchievements = getLockedAchievements(domainEvent);
        if (lockedAchievements.isEmpty()) {
            return;
        }

        var unlockedAchievements = tryUnlockAfter(lockedAchievements, domainEvent);
        sendUnlockedAchievements(unlockedAchievements);
    }

    private DomainEvent deserializeDomainEvent(final String serializedDomainEvent) {
        // TODO deserializeDomainEvent
        return new DomainEvent("SomeDomainEvent", new UserId(1), LocalDateTime.now(), new HashMap<>());
    }

    private Set<Achievement> getLockedAchievements(final DomainEvent domainEvent) {
        // TODO getUnlockedAchievements
        return Set.of(
                new Achievement(new AchievementId(1)),
                new Achievement(new AchievementId(2)));
    }

    private Set<UnlockedAchievement> tryUnlockAfter(final Set<Achievement> lockedAchievements, final DomainEvent domainEvent) {
        var userId = domainEvent.get("userId", UserId.class);
        // TODO tryUnlockAfter
        return Set.of(new UnlockedAchievement(new AchievementId(1), new UserId(1)));
    }

    private void sendUnlockedAchievements(final Set<UnlockedAchievement> achievements) {
        for (var achievement : achievements) {
            messageBroker.sendNewAchievement(achievement);
        }
    }
}
