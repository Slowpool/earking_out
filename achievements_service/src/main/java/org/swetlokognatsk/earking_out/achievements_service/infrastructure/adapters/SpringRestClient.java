package org.swetlokognatsk.earking_out.achievements_service.infrastructure.adapters;

import java.net.URI;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.Achievements;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.achievements.UnlockedAchievement;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvent;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvents;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.HttpClient;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.ResponseDeserializer;
import lombok.AllArgsConstructor;
import static org.swetlokognatsk.earking_out.achievements_service.infrastructure.World.*;

@Component
@AllArgsConstructor
public class SpringRestClient implements HttpClient {

    private final ResponseDeserializer responseDeserializer;

    public void retainLockedAchievements(final UserId userId, final Set<Achievements> allAchievements) {
        var unlockedAchievementIds = getUnlockedAchievements(userId, allAchievements)
                .stream()
                .map(unlockedAchievement -> unlockedAchievement.id())
                .toList();
        allAchievements.removeIf(achievement -> unlockedAchievementIds.contains(achievement.id));
    }

    private Set<UnlockedAchievement> getUnlockedAchievements(final UserId userId, final Set<Achievements> achievements) {
        var uri = UriComponentsBuilder.fromUriString(MONOLITH_ACHIEVEMENTS_URI)
        // TODO add api key
                .queryParam("status", "unlocked")
                .queryParam("userId", userId.id())
                .build()
                .toUri();
        // TODO does this really need to be created each time?
        return RestClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .body(Set.class);
    }

    public DomainEvents getDomainEvents(final UserId userId, final String[] eventTypes) {
        var uri = UriComponentsBuilder.fromUriString(MONOLITH_DOMAIN_EVENTS_URI)
        // TODO add api key
                .queryParam("userId", userId.id())
                .queryParam("eventTypes", String.join(",", eventTypes))
                .queryParam("orderBy", "timestamp")
                .queryParam("orderByDirection", "ASC")
                .build()
                .toUri();
        var responseBody = RestClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .body(String.class);
        return responseDeserializer.deserializeDomainEventsResponse(responseBody);
    }
}
