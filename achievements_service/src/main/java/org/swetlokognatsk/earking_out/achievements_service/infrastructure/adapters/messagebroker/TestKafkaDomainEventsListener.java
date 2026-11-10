package org.swetlokognatsk.earking_out.achievements_service.infrastructure.adapters.messagebroker;

import org.springframework.kafka.annotation.KafkaListener;

public class TestKafkaDomainEventsListener {

    @KafkaListener(topics = "domain-events", groupId = "achievements-service")
    // TODO how to read json dynamically
    public void accept(Object domainEvent) {
        System.out.println("reading domain event");
        System.out.println("");
        System.out.println("reading domain event");
    }
}
