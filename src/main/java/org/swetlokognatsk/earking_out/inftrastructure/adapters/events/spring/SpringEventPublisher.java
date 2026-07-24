package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.events.EventPublisher;

public final class SpringEventPublisher implements EventPublisher {
    // TODO use annotation
    // @Autowired
    private ApplicationEventPublisher eventPublisher;

    public SpringEventPublisher(final ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(final DomainEvent event) {
        eventPublisher.publishEvent(event);
    }

    public void publish(final List<DomainEvent> events) {
        for(var event : events) {
            publish(event);
        }
    }

}
