package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;

public final class SpringEventPublisher implements EventPublisher {
    private ApplicationEventPublisher eventPublisher;

    // TODO what will you do with compiler warning
    public SpringEventPublisher(final ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(final DomainEvent event) {
        var wrappedEvent = wrapEvent(event);
        eventPublisher.publishEvent(wrappedEvent);
    }

    private ApplicationEvent wrapEvent(final DomainEvent domainEvent) {
        return switch (domainEvent) {
        case NewPuzzleCreatedEvent de -> new SpringNewPuzzleCreatedEvent(null, de);
        default -> throw new RuntimeException("unknown domainEvent: " + domainEvent.getClass().getName());
        };
    }
}
