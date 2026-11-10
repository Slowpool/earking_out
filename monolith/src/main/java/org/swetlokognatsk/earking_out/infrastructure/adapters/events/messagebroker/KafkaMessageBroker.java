package org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker;

import org.springframework.kafka.core.KafkaTemplate;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.events.messagebroker.MessageBroker;
import org.swetlokognatsk.earking_out.core.ports.events.messagebroker.MessageDestinations;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;
import lombok.AllArgsConstructor;

@WebComponent
@AllArgsConstructor
public class KafkaMessageBroker implements MessageBroker {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(final MessageDestinations destination, final Object message) {
        // TODO is it open/closed-compatible?
        switch (destination) {
        case DOMAIN_EVENTS:
            var domainEvent = (DomainEvent) message;
            var key = domainEvent.userId.toString();
            kafkaTemplate.send(destination.value, key, domainEvent)
            // TODO .handle(exception -> logger.log(exception))
            ;
            break;
        default:
            throw new IllegalArgumentException("unknown destination: " + destination);
        }
    }

}
