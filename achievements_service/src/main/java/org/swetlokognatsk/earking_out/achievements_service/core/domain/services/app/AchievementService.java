package org.swetlokognatsk.earking_out.achievements_service.core.domain.services.app;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.HttpClient;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.AchievementId;
import static org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements.getRelatedAchievements;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.UnlockedAchievement;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvent;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvents;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.services.domain.AchievementsUnlockerService;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.events.DomainEventDeserializer;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.messagebroker.MessageBroker;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AchievementService {

    private final MessageBroker messageBroker;
    private final DomainEventDeserializer domainEventDeserializer;
    private final HttpClient httpClient;
    private final AchievementsUnlockerService achievementsUnlocker;

    public void tryMakeNewAchievements(final String serializedDomainEvent) {
        var domainEvent = deserializeDomainEvent(serializedDomainEvent);
        tryMakeNewAchievements(domainEvent);
    }

    public void tryMakeNewAchievements(final DomainEvent newDomainEvent) {
        var lockedAchievements = gatherLockedAchievements(newDomainEvent);
        if (lockedAchievements.isEmpty()) {
            return;
        }

        var unlockedAchievements = tryUnlockAfter(lockedAchievements, newDomainEvent);
        if (!unlockedAchievements.isEmpty()) {
            sendUnlockedAchievements(unlockedAchievements);
        }
    }

    private DomainEvent deserializeDomainEvent(final String serializedDomainEvent) {
        return domainEventDeserializer.deserializeDomainEvent(serializedDomainEvent);
    }

    private Set<Achievements> gatherLockedAchievements(final DomainEvent domainEvent) {
        var relatedAchievements = getRelatedAchievements(domainEvent.type());
        httpClient.retainLockedAchievements(domainEvent.userId(), relatedAchievements);
        return relatedAchievements;
    }

    private Set<UnlockedAchievement> tryUnlockAfter(final Set<Achievements> lockedAchievements, final DomainEvent domainEvent) {
        // TODO optimization - check whether is it possible to throw some achievement at all after this event on business logic level. e.g.: user failed to successfully guess the puzzle, so the only `guess x puzzles` in lockedAchievement cannot be unlocked anyway. it allows to skip the further code

        var typesOfDomainEventsToRequest = new HashSet<String>();
        // TODO use streams
        for (var lockedAchievement : lockedAchievements) {
            Collections.addAll(typesOfDomainEventsToRequest, lockedAchievement.triggerEventTypes);
        }
        var domainEvents = requestDomainEvents(typesOfDomainEventsToRequest, domainEvent.userId());
        var unlockedAchievements = achievementsUnlocker.tryUnlock(lockedAchievements, domainEvents, domainEvent);
        return unlockedAchievements;
    }

    private DomainEvents requestDomainEvents(final Set<String> domainEventTypes, final UserId userId) {
        var types = domainEventTypes.toArray(String[]::new);
        return httpClient.getDomainEvents(userId, types);
    }

    private void sendUnlockedAchievements(final Set<UnlockedAchievement> achievements) {
        for (var achievement : achievements) {
            messageBroker.sendNewAchievement(achievement);
        }
    }
}
