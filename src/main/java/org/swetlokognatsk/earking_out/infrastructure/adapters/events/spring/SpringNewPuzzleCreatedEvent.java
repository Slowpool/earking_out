package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.springframework.context.ApplicationEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;

public final class SpringNewPuzzleCreatedEvent extends DomainEventWrapper<NewPuzzleCreatedEvent> {

    public SpringNewPuzzleCreatedEvent(final Object source, final NewPuzzleCreatedEvent domainEvent) {
        super(source, domainEvent);
    }
}
