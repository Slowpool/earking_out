package org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker;

import org.springframework.kafka.annotation.KafkaListener;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;

@WebComponent
public class TestDomainEventsListener {

    @KafkaListener(topics = "domain-events", groupId = "earking-out-app")
    public void accept(DomainEvent domainEvent) {
        System.out.println("");
        System.out.println("reading the domain event from kafka: " + domainEvent.toString() + " " + domainEvent.timestamp.toString());
        System.out.println("");
    }
}
