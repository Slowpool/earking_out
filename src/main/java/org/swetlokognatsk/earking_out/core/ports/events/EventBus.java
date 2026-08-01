package org.swetlokognatsk.earking_out.core.ports.events;

import java.util.function.Consumer;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

public interface EventBus {
    <DE extends DomainEvent> void subscribe(Class<DE> clazz, Consumer<DE> action);
}
