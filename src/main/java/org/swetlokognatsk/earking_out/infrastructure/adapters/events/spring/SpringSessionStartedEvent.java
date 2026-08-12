package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;

public class SpringSessionStartedEvent extends DomainEventWrapper<SessionStartedEvent> {

    public SpringSessionStartedEvent(final Object source, final SessionStartedEvent domainEvent) {
        super(source, domainEvent);
    }
}