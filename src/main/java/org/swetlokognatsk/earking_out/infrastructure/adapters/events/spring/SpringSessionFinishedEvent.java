package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;

public class SpringSessionFinishedEvent extends DomainEventWrapper<SessionFinishedEvent> {

    public SpringSessionFinishedEvent(final Object source, final SessionFinishedEvent domainEvent) {
        super(source, domainEvent);
    }
}