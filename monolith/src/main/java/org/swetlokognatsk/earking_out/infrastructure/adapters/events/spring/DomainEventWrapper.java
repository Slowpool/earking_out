package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.springframework.context.ApplicationEvent;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

abstract class DomainEventWrapper<DE extends DomainEvent> extends ApplicationEvent {
    public final DE domainEvent;

    DomainEventWrapper(final Object source, final DE domainEvent) {
        super(source);

        this.domainEvent = domainEvent;
    }
}
