package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyReleasedEvent;

public class SpringPianoKeyReleasedEvent extends SpringDomainEvent<PianoKeyReleasedEvent> {

    public SpringPianoKeyReleasedEvent(final Object source, final PianoKeyReleasedEvent domainEvent) {
        super(source, domainEvent);
    }
}
