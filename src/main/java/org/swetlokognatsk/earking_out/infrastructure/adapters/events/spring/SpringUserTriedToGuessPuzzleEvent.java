package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;

public class SpringUserTriedToGuessPuzzleEvent extends DomainEventWrapper<UserTriedToGuessPuzzleEvent> {

    public SpringUserTriedToGuessPuzzleEvent(final Object source, final UserTriedToGuessPuzzleEvent domainEvent) {
        super(source, domainEvent);
    }
}