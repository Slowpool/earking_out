package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;

public class SpringPianoKeyPressedEvent extends DomainEventWrapper<PianoKeyPressedEvent> {

    public SpringPianoKeyPressedEvent(final Object source, final PianoKeyPressedEvent domainEvent) {
        super(source, domainEvent);
    }
}
