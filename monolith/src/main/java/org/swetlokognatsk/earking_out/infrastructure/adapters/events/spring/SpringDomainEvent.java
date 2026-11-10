package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

public class SpringDomainEvent<DE extends DomainEvent> extends DomainEventWrapper<DE> {

    public SpringDomainEvent(final Object source, final DE domainEvent) {
        super(source, domainEvent);
    }
}
