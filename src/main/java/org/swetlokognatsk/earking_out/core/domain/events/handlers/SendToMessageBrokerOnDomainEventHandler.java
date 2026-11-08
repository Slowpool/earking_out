package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.base.DomainEventHandler;
import org.swetlokognatsk.earking_out.core.ports.events.messagebroker.MessageBroker;
import org.swetlokognatsk.earking_out.core.ports.events.messagebroker.MessageDestinations;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SendToMessageBrokerOnDomainEventHandler implements DomainEventHandler<DomainEvent> {
    private final MessageBroker messageBroker;

    public void handle(final DomainEvent domainEvent) {
        messageBroker.send(MessageDestinations.DOMAIN_EVENTS, domainEvent);
    }
}
