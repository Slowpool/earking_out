package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.springframework.context.ApplicationEventPublisher;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;

public final class SpringEventPublisher implements EventPublisher {
    private ApplicationEventPublisher eventPublisher;

    // TODO what will you do with compiler warning
    public SpringEventPublisher(final ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(final DomainEvent event) {
        eventPublisher.publishEvent(event);
    }
}
