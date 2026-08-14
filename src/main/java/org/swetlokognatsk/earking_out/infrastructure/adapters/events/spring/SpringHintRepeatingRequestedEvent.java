package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;

public class SpringHintRepeatingRequestedEvent extends DomainEventWrapper<HintRepeatingRequestedEvent> {

    public SpringHintRepeatingRequestedEvent(final Object source, final HintRepeatingRequestedEvent domainEvent) {
        super(source, domainEvent);
    }
}