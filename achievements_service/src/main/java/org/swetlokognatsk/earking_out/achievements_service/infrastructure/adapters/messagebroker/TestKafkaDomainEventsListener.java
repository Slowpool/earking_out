package org.swetlokognatsk.earking_out.achievements_service.infrastructure.adapters.messagebroker;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.services.app.AchievementService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TestKafkaDomainEventsListener {

    private final AchievementService achievementService;

    @KafkaListener(topics = "domain-events", groupId = "achievements-service")
    public void accept(String domainEvent) {
        achievementService.tryMakeNewAchievements(domainEvent);
    }
}
