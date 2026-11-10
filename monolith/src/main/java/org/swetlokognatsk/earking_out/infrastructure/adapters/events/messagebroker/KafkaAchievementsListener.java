package org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker;

import org.springframework.kafka.annotation.KafkaListener;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;

@WebComponent
public class KafkaAchievementsListener {

    @KafkaListener(topics = "achivements")
    public void accept(final NewUnlockedAchievement newUnlockedAchievement) {
        System.out.println("");
        System.out.println("reading the domain event from kafka: %s".formatted(domainEvent));
        System.out.println("");
    }
}
