package org.swetlokognatsk.earking_out.inftrastructure.adapters.events;

import java.util.function.Consumer;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;
import org.swetlokognatsk.earking_out.core.ports.events.EventType;

public final class SpringEventBus implements EventBus {
    public <DE extends DomainEvent> void subscribe(final EventType<DE> eventType, Consumer<DE> action) {
        // TODO SpringEventBus

    }

}
