package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;

public class SpringSessionFinishedEvent extends SpringDomainEvent<SessionFinishedEvent> {

    public SpringSessionFinishedEvent(final Object source, final SessionFinishedEvent domainEvent) {
        super(source, domainEvent);
    }
}