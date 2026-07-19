package org.swetlokognatsk.earking_out.core.ports.events;

import java.util.function.Consumer;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

public interface EventBus {
    public <DE extends DomainEvent> void subscribe(final EventType<DE> eventType, Consumer<DE> action);
}
